public class Game {

  private Move[] moves;
  private int moveInd;
  private Board brd;
  private Color player;

  public Game(Board board) {
    moves = new Move[200];
    moveInd = -1;
    player = Color.WHITE;
    brd = board;
  }

  public Color getCurrentPlayer(){
    return player;
  }

  public Move getLastMove(){
    if (moveInd == -1) {
      return null;
    }
    return moves[moveInd-1];
  }

  public void applyMove(Move move) {
    moveInd += 1;
    moves[moveInd] = move;
    brd.applyMove(move);
    if (player == Color.WHITE) {
      player = Color.BLACK;
    } else {
      player = Color.WHITE;
    }

  }

  public void unapplyMove() {
    if (moveInd != -1) {
      moveInd -= 1;
      brd.unapplyMove(moves[moveInd]);
      if (player == Color.WHITE) {
        player = Color.BLACK;
      } else {
        player = Color.WHITE;
      }
    }
  }

    public boolean isFinished() {
      for (int i = 0; i < 8;i++) {
        if (brd.getSquare(0,i).occupiedBy() != Color.NONE) {
          return true;
        }
        if (brd.getSquare(7,i).occupiedBy() != Color.NONE) {
          return true;
        }
      }
      return false;
    }

    public Color getGameResult() {
      for (int i = 0; i < 8;i++) {
        if (brd.getSquare(0,i).occupiedBy() != Color.NONE) {
          return Color.BLACK;
        }
        if (brd.getSquare(7,i).occupiedBy() != Color.NONE) {
          return Color.WHITE;
        }
      }
    }

    public Move parseMove(String san) {
      boolean isCapture = false;
      String fromPos = "";
      String toPos = san.charAt(0) + "";
      if (san.length() == 4) {
        isCapture = true;
        fromPos = ("" + san.charAt(2)) + (san.charAt(3)+ "");
      } else {
        fromPos = san.charAt(0) + san.charAt(1);
      }
      int toX = ((int) toPos.charAt(0)) - 97;
      int fromY;
      int fromX = ((int) fromPos.charAt(0)) - 97;
      int toY = Integer.parseInt(toPos.charAt(1)) - 1;
      if (!isCapture) {
        if (player == Color.WHITE) {
          if (brd.getSquare(toY - 1,fromX).occupiedBy == Color.WHITE) {
            fromY = toY - 1;
          } else if (brd.getSquare(toY - 2,fromX).occupiedBy == Color.WHITE){
            fromY = toY - 2;
          } else {
            return null;
          }
        } else {
          if (brd.getSquare(toY + 1,fromX).occupiedBy == Color.BLACK) {
            fromY = toY + 1;
          } else if (brd.getSquare(toY + 2,fromX).occupiedBy == Color.BLACK){
            fromY = toY + 2;
          } else {
             return null;
           }
        }
      } else {
        if (player == Color.WHITE) {
          if (brd.getSquare[toY - 1][fromX].occupiedBy == Color.WHITE) {
            fromY = toY - 1;
          } else {
            return null;
          }
        } else {
          if (brd.getSquare[toY + 1][fromX].occupiedBy == Color.BLACK) {
            fromY = toY + 1;
          } else {
            return null;
          }
        }
        Color opColor = WHITE;
        boolean enPass;
        if (player == Color.WHITE) {
          opColor = Color.BLACK;
        }
        if (brd.getSquare[toY][toX] == opColor) {
          enPass = false;
        } else {
          enPass = true;
        }
      }
      return new Move(new Square(fromX,fromY),new Square(toX,toY),isCapture,enPass);
    }
  }
