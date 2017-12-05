import java.lang.Math;
public class Board{

  private Square[][] gameboard = new Square[8][8];

  public Board(char whiteGap, char blackGap) {
    int wx = ((int) whiteGap) - 65;
    int bx = ((int) blackGap) - 65;
    for (int i = 0; i < 8; i ++) {
      for (int j = 0; j < 8; j ++) {
        gameboard[i][j] = new Square(i,j);
      }
    }
    for (int i = 0; i < 8; i++) {
      if (i != wx) {
        gameboard[i][1].setOccupier(Color.WHITE,new Pawn(Color.WHITE));
      }
      if (i != bx) {
        gameboard[i][6].setOccupier(Color.BLACK,new Pawn(Color.WHITE));
      }
    }
  }

  public Square getSquare(int x, int y){
    if (y < 0 || y > 7 || x < 0 || x > 7) {
      return null;
    }
    return gameboard[x][y];
  }

  public void applyMove(Move move) {
    int toX = move.getTo().getX();
    int toY = move.getTo().getY();
    int frX = move.getFrom().getX();
    int frY = move.getFrom().getY();
    gameboard[toX][toY].setOccupier(gameboard[frX][frY].occupiedBy(),gameboard[frX][frY].getPawn());
    gameboard[frX][frY].setOccupier(Color.NONE,null);
    if (move.isEnPassantCaputre()) {
      if (gameboard[frX][frY].occupiedBy() == Color.WHITE){
        gameboard[frX - 1][frY].setOccupier(Color.NONE,null);
        gameboard[toX][toY].getPawn().Move(true);
      } else {
        gameboard[frX + 1][frY].setOccupier(Color.NONE,null);
        gameboard[toX][toY].getPawn().Move(true);
      }
    }
    gameboard[toX][toY].getPawn().Move(false);
  }

  public void undoMove(Move move) {
    int toX = move.getTo().getX();
    int toY = move.getTo().getY();
    int frX = move.getFrom().getX();
    int frY = move.getFrom().getY();
    gameboard[toX][toY] = move.getTo();
    gameboard[frX][frY] = move.getFrom();
  }

  public void display() {
    System.out.println("  A B C D E F G H  ");
    System.out.println();
    for (int i = 8; i > 0; i--){
      System.out.print(i + " ");
      for (int j = 0; j < 8; j++){
        if (gameboard[j][i-1].occupiedBy() == Color.WHITE){
          System.out.print("W ");
        } else if (gameboard[j][i-1].occupiedBy() == Color.BLACK) {
          System.out.print("B ");
        } else{
          System.out.print(". ");
        }
      }
      System.out.println(" " + i);
    }
    System.out.println("            ");
    System.out.println("  ABCDEFGH  ");
  }

  public int eval() {
    int score = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (gameboard[i][j].occupiedBy() == Color.WHITE) {
          if (i == 7) {
            return 9999;
          }
          score += java.lang.Math.pow(2,i);
        }
        if (gameboard[i][j].occupiedBy() == Color.BLACK) {
          if (i == 7) {
            return -9999;
          }
          score -= java.lang.Math.pow(2,7-i);
        }
      }
    }
    return score;
  }

}
