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
    return copySquare(gameboard[x][y]);
  }

  public Square copySquare(Square sq1) {
    Square sq2 = new Square(sq1.getX(),sq1.getY());
    sq2.setOccupier(sq1.occupiedBy(),sq1.getPawn());
    return sq2;
  }

  public void applyMove(Move move) {
    int toX = move.getTo().getX();
    int toY = move.getTo().getY();
    int frX = move.getFrom().getX();
    int frY = move.getFrom().getY();
    gameboard[toX][toY].setOccupier(gameboard[frX][frY].occupiedBy(),gameboard[frX][frY].getPawn());
    gameboard[frX][frY].setOccupier(Color.NONE,null);
    if (move.isEnPassantCaputre()) {
      gameboard[toX][frY].setOccupier(Color.NONE,null);
      gameboard[toX][frY].setOccupier(Color.NONE,null);
    }
    if (frY - 2 == toY || frY + 2 == toY) {
      gameboard[toX][toY].Move(true);
    } else {
      gameboard[toX][toY].Move(false);
    }
  }

  public void undoMove(Move move) {
    int toX = move.getTo().getX();
    int toY = move.getTo().getY();
    int frX = move.getFrom().getX();
    int frY = move.getFrom().getY();
    gameboard[toX][toY] = copySquare(move.getTo());
    gameboard[frX][frY] = copySquare(move.getFrom());
    if (move.isEnPassantCaputre()) {
      if (move.getFrom().occupiedBy() == Color.WHITE) {
        gameboard[toX][frY].setOccupier(Color.BLACK,new Pawn(Color.BLACK));
        gameboard[toX][frY].Move(true);
      } else {
        gameboard[toX][frY].setOccupier(Color.WHITE,new Pawn(Color.WHITE));
        gameboard[toX][frY].Move(true);
      }
    }
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
    System.out.println();
    System.out.println("  A B C D E F G H  ");
  }

  public int eval() {
    int score = 0;
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (gameboard[i][j].occupiedBy() == Color.WHITE) {
          if (j == 7) {
            return 9999;
          }
          if (isPassedPawn(j,Color.WHITE)) {
            score += java.lang.Math.pow(4,j);
          } else if (lonelyPawn(i,j,Color.WHITE)){
            score += java.lang.Math.pow(6,j);
          } else {
            score += java.lang.Math.pow(2,j);
          }
          if (isBlockerBoi(i,j,Color.WHITE)) {
            score += 30;
          }
          if (isProtectorBoi(i,j,Color.WHITE)) {
            score += 30;
          }
        }
        if (gameboard[i][j].occupiedBy() == Color.BLACK) {
          if (j == 0) {
            return -9999;
          }
          if (isPassedPawn(j,Color.BLACK)) {
            score -= java.lang.Math.pow(4,j);
          } else if (lonelyPawn(i,j,Color.BLACK)){
            score -= java.lang.Math.pow(6,j);
          } else {
            score -= java.lang.Math.pow(2,j);
          }
          if (isBlockerBoi(i,j,Color.BLACK)) {
            score -= 30;
          }
          if (isProtectorBoi(i,j,Color.BLACK)) {
            score -= 30;
          }
        }
      }
    }
    return score;
  }
  public boolean lonelyPawn(int x,int y, Color col) {
    if (col == Color.WHITE) {
      for (int i = y+1; i < 8; i ++) {
        if (gameboard[x][i].occupiedBy() == Color.BLACK) {
          return false;
        }
        if (x+1<8) {
          if (gameboard[x+1][i].occupiedBy() == Color.BLACK) {
            return false;
          }
        }
        if (x-1>-1) {
          if (gameboard[x-1][i].occupiedBy() == Color.BLACK) {
            return false;
          }
        }
      }
    } else {
      for (int i = y-1; i >= 0; i --) {
        if (gameboard[x][i].occupiedBy() == Color.WHITE) {
          return false;
        }
        if (x+1<8) {
          if (gameboard[x+1][i].occupiedBy() == Color.WHITE) {
            return false;
          }
        }
        if (x-1>-1) {
          if (gameboard[x-1][i].occupiedBy() == Color.WHITE) {
            return false;
          }
        }
      }
    }
    return true;
  }
  public boolean isPassedPawn(int y,Color col) {
    Color oppCol = Color.WHITE;
    if (col == oppCol){
      oppCol = Color.BLACK;
    }

    if (col == Color.WHITE) {
      for (int i = y;i<8;i++){
        for (int j = 0; j < 8;j++){
          if (gameboard[i][j].occupiedBy() == oppCol) {
            return false;
          }
        }
      }
    }

    if (col == Color.BLACK) {
      for (int i = y;i>=0;i--){
        for (int j = 0; j < 8;j++){
          if (gameboard[i][j].occupiedBy() == oppCol) {
            return false;
          }
        }
      }
    }

    return true;
  }

  public boolean isBlockerBoi(int i, int j, Color col) {
    Color oppCol = Color.WHITE;
    if (col == oppCol){
      oppCol = Color.BLACK;
    }
    if (col == Color.WHITE) {
      if (gameboard[i][j+1].occupiedBy() == oppCol) {
        return true;
      } else {
        return false;
      }
    } else {
      if (gameboard[i][j-1].occupiedBy() == oppCol) {
        return true;
      } else {
        return false;
      }
    }
  }

  public boolean isProtectorBoi(int i, int j, Color col) {
    if (col == Color.WHITE) {
      if (i-1 >= 0) {
        if (gameboard[i-1][j+1].occupiedBy() == col) {
          return true;
        }
      }
      if (i+1 <8) {
        if (gameboard[i+1][j+1].occupiedBy() == col) {
          return true;
        }
      }
    } else {
      if (i-1 >= 0) {
        if (gameboard[i-1][j-1].occupiedBy() == col) {
          return true;
        }
      }
      if (i+1 <8) {
        if (gameboard[i+1][j-1].occupiedBy() == col) {
          return true;
        }
      }
    }
    return false;
  }

  public boolean isVulnBoi(int i, int j, Color col) {
    Color oppCol = Color.WHITE;
    if (col == oppCol){
      oppCol = Color.BLACK;
    }
    if (col == Color.WHITE) {

      if (j < 7 && i-1 >=0) {
        if (gameboard[i-1][j+1].occupiedBy() == oppCol) {
          return true;
        }
      }
      if (j < 7 && i <7) {
        if (gameboard[i+1][j+1].occupiedBy() == oppCol) {
          return true;
        }
      }
    } else {
      if (j >0 && i-1 >=0) {
        if (gameboard[i-1][j-1].occupiedBy() == oppCol) {
          return true;
        }
      }
      if (j >0 && i <7) {
        if (gameboard[i+1][j-1].occupiedBy() == oppCol) {
          return true;
        }
      }
    }
    return false;
  }

  public void Update(int i , int j) {
    gameboard[i][j].Update();
  }

}
