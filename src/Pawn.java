
public class Pawn {
  private Color color;
  private boolean hasMoved;
  private boolean canEP = false;

  public Pawn(Color col) {
    hasMoved = false;
    color = col;
    canEP = false;
  }

  public void Move(boolean doubleMove) {
    hasMoved = true;
    canEP = doubleMove;
  }

  public boolean getEP(){
    return canEP;
  }

  public boolean hasMoved(){
    return hasMoved;
  }
}
