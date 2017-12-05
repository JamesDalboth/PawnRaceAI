public class Move {
  private Square lastSquare;
  private Square nextSquare;
  private boolean wasCapture;
  private boolean wasEnPassant;

  public Move(Square from, Square to, boolean isCapture, boolean isEnPassentCapture) {
    lastSquare = from;
    nextSquare = to;
    wasCapture = isCapture;
    wasEnPassant = isEnPassentCapture;
  }

  public Square getFrom() {
    return lastSquare;
  }

  public Square getTo() {
    return nextSquare;
  }

  public boolean isCapture() {
    return wasCapture;
  }

  public boolean isEnPassantCaputre() {
    return wasEnPassant;
  }

  public String getSAN() {
    String move = "";
    String startPos = ((char) (lastSquare.getX()+97)) + "";
    String endPos = ((char) (nextSquare.getX()+97)) + "" + (nextSquare.getY()+1);
    if (isCapture()) {
      move = startPos + "-";
    }
    move += endPos;
    return move;
  }
}
