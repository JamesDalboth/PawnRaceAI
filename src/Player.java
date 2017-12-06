import java.util.Random;
import java.util.Scanner;
public class Player {
  private Game game;
  private Color color;
  private Board board;
  private boolean isComputerPlayer;
  private Player opp;

  public Player(Game gm, Board brd, Color clr, boolean isCP){
    game = gm;
    color = clr;
    board = brd;
    isComputerPlayer = isCP;
  }

  public void setOpponent(Player opponent){
    opp = opponent;
  }

  public Color getColor(){
    return color;
  }

  public boolean isComputerPlayer() {
    return isComputerPlayer;
  }

  public Square[] getAllPawns(Color col){
    int count = 0;
    for (int i = 0; i < 8; i++){
      for (int j = 0; j < 8; j++){
        if (board.getSquare(i,j).occupiedBy() == col){
          count += 1;
        }
      }
    }
    Square[] pawns = new Square[count];
    count = 0;
    for (int i = 0; i < 8; i++){
      for (int j = 0; j < 8; j++){
        if (board.getSquare(i,j).occupiedBy() == col){
          pawns[count] = board.getSquare(i,j);
          count += 1;
        }
      }
    }
    return pawns;
  }

  public Move[] getAllValidMoves(Color col){
    Square[] pawns = getAllPawns(col);
    Move[] moveList = new Move[40];
    int moveCount = 0;
    //Go through each pawns
    for (Square pawn: pawns) {
      // get current Square
      int startX = pawn.getX();
      int startY = pawn.getY();
      Square startSquare = board.getSquare(startX,startY);

      // Calculate direction

      int dir = 1;
      if (col == Color.BLACK) {
        dir = -1;
      }

      //Can move forward One
      if (board.getSquare(startX,startY + dir) == null) {
        System.out.println(startY);
        board.display();
      }
      if (board.getSquare(startX,startY + dir).occupiedBy() == Color.NONE) {
        Square toSquare = board.getSquare(startX,startY+dir);
        moveList[moveCount] = new Move(startSquare,toSquare,false,false);
        moveCount += 1;
      }

      //Can move forward two
      if (board.getSquare(startX,startY).getPawn() != null) {
        if (!board.getSquare(startX,startY).getPawn().hasMoved()){
          if (board.getSquare(startX,startY + dir).occupiedBy() == Color.NONE &&
          board.getSquare(startX,startY + 2*dir).occupiedBy() == Color.NONE){
            Square toSquare = board.getSquare(startX,startY+2*dir);
            moveList[moveCount] = new Move(startSquare,toSquare,false,false);
            moveCount += 1;
          }
        }
      }

      //Can take normal

      Color oppCol = Color.WHITE;
      if (col == oppCol){
        oppCol = Color.BLACK;
      }
      if (board.getSquare(startX+1,startY + dir) != null) {
        if (board.getSquare(startX+1,startY + dir).occupiedBy() == oppCol) {
          Square toSquare = board.getSquare(startX+1,startY + dir);
          moveList[moveCount] = new Move(startSquare,toSquare,true,false);
          moveCount += 1;
        }
      }
      if (board.getSquare(startX-1,startY + dir) != null) {
        if (board.getSquare(startX-1,startY + dir).occupiedBy() == oppCol) {
          Square toSquare = board.getSquare(startX-1,startY + dir);
          moveList[moveCount] = new Move(startSquare,toSquare,true,false);
          moveCount +=1 ;
        }
      }

      // EnPass?
      if (board.getSquare(startX+1,startY+dir) != null) {
        if (board.getSquare(startX+1,startY+dir).occupiedBy() == Color.NONE) {
          if (board.getSquare(startX+1,startY).getPawn() != null) {
            if (board.getSquare(startX+1,startY).getPawn().getEP()
              && board.getSquare(startX+1,startY).occupiedBy() == oppCol) {
              Square toSquare = board.getSquare(startX+1,startY + dir);
              moveList[moveCount] = new Move(startSquare,toSquare,true,true);
              moveCount += 1;
            }
          }
        }
      }
      if (board.getSquare(startX-1,startY+dir) != null) {
        if (board.getSquare(startX-1,startY+dir).occupiedBy() == Color.NONE) {
          if (board.getSquare(startX-1,startY).getPawn() != null) {
            if (board.getSquare(startX-1,startY).getPawn().getEP()
            && board.getSquare(startX-1,startY).occupiedBy() == oppCol) {
              Square toSquare = board.getSquare(startX-1,startY + dir);
              moveList[moveCount] = new Move(startSquare,toSquare,true,true);
              moveCount += 1;
            }
          }
        }
      }
    }
    Move[] result = new Move[moveCount];
    for (int i = 0; i < moveCount;i++){
      result[i] = moveList[i];
    }
    return result;
  }

  public void makeMove2(int depth,boolean isMax) {
    if (isComputerPlayer) {
      Move[] allValid = getAllValidMoves(color);
      if (allValid.length == 0) {
        game.End();
      } else {
        Move bestMove = null;
        int bestScore = -100000000;
        for (int i = 0; i < allValid.length; i++) {
          Move choice = allValid[i];
          game.applyMove(choice);
          int score = 0;
          int curState = board.eval();
          if (curState == 9999 || curState == -9999) {
            score = curState*100;
          } else {
            score = minimax2(depth-1,-100000000,100000000,!isMax);
          }
          if (!isMax) {
            score *= -1;
          }
          game.unApplyMove(choice);
          if (score > bestScore) {
            bestScore = score;
            bestMove = choice;
          }
        }
        game.applyMove(bestMove);
        System.out.println("___________");
        System.out.println(bestMove.getSAN());
        System.out.println("___________");
      }
    } else {

      Scanner in = new Scanner(System.in);
      String inputMove;
      Move choice = null;
      while (choice == null) {
        System.out.print("Move -> ");
        inputMove = in.nextLine();
        choice = game.parseMove(inputMove);
      }
      game.applyMove(choice);
    }
  }

  public int minimax2(int depth,int alpha, int beta,boolean isMax) {
    int curState = board.eval();
    if (curState == 9999 || curState == -9999) {
      return curState * depth;
    }
    if (depth == 0) {
      return board.eval();
    }
    if (isMax){
      Move[] allValid = getAllValidMoves(Color.WHITE);
      int bestMove = -9999;
      for (int i = 0; i < allValid.length; i++) {
        Move choice = allValid[i];
        game.applyMove(choice);
        bestMove = Math.max(bestMove,minimax2(depth-1,alpha,beta,!isMax));
        game.unApplyMove(choice);
        alpha = Math.max(alpha,bestMove);
        if (beta <= alpha) {
          return bestMove;
        }
      }
      return bestMove;
    } else {
      Move[] allValid = getAllValidMoves(Color.BLACK);
      int bestMove = 9999;
      for (int i = 0; i < allValid.length; i++) {
        Move choice = allValid[i];
        game.applyMove(choice);
        bestMove = Math.min(bestMove,minimax2(depth-1,alpha,beta,!isMax));
        game.unApplyMove(choice);
        beta = Math.min(beta,bestMove);
        if (beta <= alpha) {
          return bestMove;
        }
      }
      return bestMove;
    }
  }
}
