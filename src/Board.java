public class Board{

  private Square[][] gameboard = new Square[8][8];

  public Board(char whiteGap, char blackGap) {
    int wx = (int) whiteGap;
    int bx = (int) blackGap;

    for (int i = 0; i < 8; i ++) {
      for (int j = 0; j < 8; j ++) {
        gameboard[i][j] = new Square(i,j);
      }
    }
    for (int i = 0; i < 8; i++) {
      if (i != wx) {
        gameboard[1][i].setOccupier(Color.WHITE);
      }
      if (i != bx) {
        gameboard[6][i].setOccupier(Color.BLACK);
      }
    }
  }

  public Square getSquare(int x, int y){
    return gameboard[x][y];
  }

  public void applyMove(Move move) {
    int toX = move.getTo().getX();
    int toY = move.getTo().getY();
    int frX = move.getFrom().getX();
    int frY = move.getFrom().getY();
    gameboard[toY][toX].setOccupier(gameboard[frY][frX].occupiedBy());
    gameboard[frY][frX].setOccupier(Color.NONE);
    if (move.isEnPassantCaputre()) {
      if (gameboard[frX][frY].occupiedBy() == Color.WHITE){
        gameboard[frY - 1][frX].setOccupier(Color.NONE);
      } else {
        gameboard[frY + 1][frX].setOccupier(Color.NONE);
      }
    }
  }

  public void unapplyMove(Move move) {
    int frX = move.getTo().getX();
    int frY = move.getTo().getY();
    int toX = move.getFrom().getX();
    int toY = move.getFrom().getY();
    gameboard[toY][toX].setOccupier(gameboard[frY][frX].occupiedBy());
    gameboard[frY][frX].setOccupier(Color.NONE);
    if (move.isCapture()){
      if (gameboard[frX][frY].occupiedBy() == Color.WHITE){
        if (move.isEnPassantCaputre()) {
          gameboard[frY - 1][frX].setOccupier(Color.BLACK);
        } else {
          gameboard[frY][frX].setOccupier(Color.BLACK);
        }
      } else {
        if (move.isEnPassantCaputre()) {
          gameboard[frY + 1][frX].setOccupier(Color.WHITE);
        } else {
          gameboard[frY][frX].setOccupier(Color.WHITE);
        }
      }
    }
  }

  public void display() {
    System.out.println("  ABCDEFGH  ");
    System.out.println("            ");
    for (int i = 8; i > 0; i--){
      System.out.print(i + " ");
      for (int j = 0; j < 8; j++){
        if (gameboard[i-1][j].occupiedBy() == Color.WHITE){
          System.out.print("W");
        } else if (gameboard[i-1][j].occupiedBy() == Color.BLACK) {
          System.out.print("B");
        } else{
          System.out.print(".");
        }
        System.out.println(" " + i);
      }
    }
    System.out.println("            ");
    System.out.println("  ABCDEFGH  ");
  }

}
