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

  public Pawn getPawn(){
    return piece;
  }

  public void setOccupier(Color color,Pawn pwn) {
    occupant = color;
    if (color == Color.NONE) {
      piece = null;
    } else{
      piece = pwn;
    }
  }

}
