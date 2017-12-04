public class Square {

  private int xCoord;
  private int yCoord;

  private Color occupant = Color.NONE;

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

  public void setOccupier(Color color) {
    occupant = color;
  }

}
