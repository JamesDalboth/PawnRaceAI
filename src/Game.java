public class Game {

  private Move[] moves;
  private int moveInd;
  private Board brd;
  private Color player;

  public Game(Board board) {
    moves = new move[200];
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

    public void unapplyMove(){
      moveInd -= 1;
      brd.unapplyMove(moves[moveInd]);
      if (player == Color.WHITE) {
        player = Color.BLACK;
      } else {
        player = Color.WHITE;
      }
    }

    public boolean isFinished() {
      for (int i = 0; i < 8) {
        if (brd.getSquare[0][i].occupiedBy() != Color.NONE) {
          return True;
        }
        if (brd.getSquare[7][i].occupiedBy() != Color.NONE) {
          return True;
        }
      }
      return False;
    }

    public Color getGameResult() {
      for (int i = 0; i < 8) {
        if (brd.getSquare[0][i].occupiedBy() != Color.NONE) {
          return Color.BLACK;
        }
        if (brd.getSquare[7][i].occupiedBy() != Color.NONE) {
          return Color.WHITE;
        }
      }
    }

    public Move parseMove(String san) {
      boolean isCapture = False;
      String fromPos = "";
      String toPos = san.charAt(0) + "";
      if (san.length() == 4) {
        isCapture = True;
        fromPos = san.charAt(2) + san.charAt(3);
      } else {
        fromPos = san.charAt(0) + san.charAt(1);
      }
      int toX = ((int) toPos.charAt(0)) - 97
      int fromX = ((int) fromPos.charAt(0)) - 97
      Square startSquare;
      Square endSquare;
    }
  }


}
