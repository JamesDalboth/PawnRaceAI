import java.util.Random;
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

  public boolean isPassedPawn(Square square,Color col) {
    Color oppCol = Color.WHITE;
    if (col == oppCol){
      oppCol = Color.BLACK;
    }

    if (col == Color.WHITE) {
      for (int i = square.getY();i<8;i++){
        for (int j = 0; j < 8;j++){
          if (board.getSquare(i,j).occupiedBy() == oppCol) {
            return false;
          }
        }
      }
    }

    if (col == Color.BLACK) {
      for (int i = square.getY();i>=0;i--){
        for (int j = 0; j < 8;j++){
          if (board.getSquare(i,j).occupiedBy() == oppCol) {
            return false;
          }
        }
      }
    }

    return true;
  }

  public void makeMove(int depth,boolean isMax) {
    Move[] allValid = getAllValidMoves(color);
    Move bestMove = null;
    int bestScore = -10000;
    for (int i = 0; i < allValid.length; i++) {
      Move choice = allValid[i];
      game.applyMove(choice);
      int score = 0;
      int curState = board.eval();
      if (curState == 9999 || curState == -9999) {
        score = curState;
      } else {
        score = minimax(depth-1,!isMax);
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

  public int minimax(int depth,boolean isMax) {
    int curState = board.eval();
    if (curState == 9999 || curState == -9999) {
      return curState;
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
        bestMove = Math.max(bestMove,minimax(depth-1,!isMax));
        game.unApplyMove(choice);
      }
      return bestMove;
    } else {
      Move[] allValid = getAllValidMoves(Color.BLACK);
      int bestMove = 9999;
      for (int i = 0; i < allValid.length; i++) {
        Move choice = allValid[i];
        game.applyMove(choice);
        bestMove = Math.min(bestMove,minimax(depth-1,!isMax));
        game.unApplyMove(choice);
      }
      return bestMove;
    }
  }



  public void AI1() {
    Move[] allValid = getAllValidMoves(color);
    Move bestMove = null;
    int bestScore = -10000;
    for (int i = 0; i < allValid.length; i++) {
      Move choice = allValid[i];
      game.applyMove(choice);
      int score = board.eval();
      if (color == Color.BLACK) {
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
}
