public class Square {

  private int xCoord;
  private int yCoord;

  private Color occupant = Color.NONE;
  private Pawn piece;

  public Square(int x, int y) {
    xCoord = x;
    yCoord = y;
  }

  public int getX(){
    return xCoord;
  }

  public int getY(){
    return yCoord;
  }

  public Color occupiedBy() {
    return occupant;
  }
  public void Move(boolean doub) {
    piece.Move(doub);
  }

  public Pawn getPawn(){
    return  copyPawn(piece);
  }

  public Pawn copyPawn(Pawn pw1) {
    if (pw1 == null) {
      return null;
    }
    Pawn pw2 = new Pawn(occupant);
    if (pw1.hasMoved()) {
      if (pw1.getEP()) {
        pw2.Move(true);
      } else {
        pw2.Move(false);
      }
    }
    return pw2;
  }

  public void setOccupier(Color color,Pawn pwn) {
    occupant = color;
    if (color == Color.NONE) {
      piece = null;
    } else{
      piece = pwn;
    }
  }

  public void Update() {
    piece.Update();
  }

}
