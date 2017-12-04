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
        if (brd.getSquare(i,0).occupiedBy() != Color.NONE) {
        System.out.println(i + " , " + 0);
          return true;
        }
        if (brd.getSquare(i,7).occupiedBy() != Color.NONE) {
          System.out.println(i + " , " + 7);
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
      return null;
    }

    public Move parseMove(String san) {
      boolean isCapture = false;
      String fromPos = "";
      String toPos = san.charAt(0) + "";
      if (san.length() == 4) {
        isCapture = true;
        fromPos = ("" + san.charAt(2)) + (san.charAt(3)+ "");
      } else {
        fromPos = ("" + san.charAt(0)) + (san.charAt(1)+ "");
      }
      int toX = Integer.parseInt(toPos.charAt(0) + "");
      int fromY;
      int fromX = Integer.parseInt(fromPos.charAt(0) + "");
      int toY = Integer.parseInt(toPos.charAt(1) + "") - 1;
      if (!isCapture) {
        if (player == Color.WHITE) {
          if (brd.getSquare(toY - 1,fromX).occupiedBy() == Color.WHITE) {
            fromY = toY - 1;
          } else if (brd.getSquare(toY - 2,fromX).occupiedBy() == Color.WHITE){
            fromY = toY - 2;
            if (brd.getSquare(fromY,fromX).getPawn().hasMoved()) {
              return null;
            }
          } else {
            return null;
          }
        } else {
          if (brd.getSquare(toY + 1,fromX).occupiedBy() == Color.BLACK) {
            fromY = toY + 1;
          } else if (brd.getSquare(toY + 2,fromX).occupiedBy() == Color.BLACK){
            fromY = toY + 2;
            if (brd.getSquare(fromY,fromX).getPawn().hasMoved()) {
              return null;
            }
          } else {
             return null;
           }
        }
      } else {
        if (player == Color.WHITE) {
          if (brd.getSquare(toY - 1,fromX).occupiedBy() == Color.WHITE) {
            fromY = toY - 1;
          } else {
            return null;
          }
        } else {
          if (brd.getSquare(toY + 1,fromX).occupiedBy() == Color.BLACK) {
            fromY = toY + 1;
          } else {
            return null;
          }
        }
      }
      Color opColor = Color.WHITE;
      boolean enPass;
      if (player == Color.WHITE) {
        opColor = Color.BLACK;
      }
      if (brd.getSquare(toY,toX).occupiedBy() == opColor) {
        enPass = false;
      } else {
        enPass = true;
      }

      if (enPass) {
        if (player == Color.WHITE) {
          if (!(brd.getSquare(toY-1,toX).occupiedBy() == Color.BLACK && brd.getSquare(toY-1,toX).getPawn().getEP())) {
            return null;
          }
        } else {
          if (!(brd.getSquare(toY+1,toX).occupiedBy() == Color.WHITE && brd.getSquare(toY+1,toX).getPawn().getEP())) {
            return null;
          }
        }
      }
      return new Move(new Square(fromX,fromY),new Square(toX,toY),isCapture,enPass);
    }
  }
