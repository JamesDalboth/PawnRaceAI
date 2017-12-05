import java.util.Scanner;
public class PawnRace {
  private static boolean player1Human;
  private static boolean player2Human;
  public static void main(String[] args) {
    if (args[0] == "P") {
      player1Human = true;
    } else {
      player1Human = false;
    }
    if (args[1] == "P") {
      player2Human = true;
    } else {
      player2Human = false;
    }

    Board board = new Board(args[2].charAt(0),args[3].charAt(0));
    Game game = new Game(board);
    Player player1 = new Player(game,board,Color.WHITE,player1Human);
    Player player2 = new Player(game,board,Color.BLACK,player2Human);
    boolean player1Move = true;
    Scanner in = new Scanner(System.in);
    while (!game.isFinished()) {
      board.display();
      String bob = in.nextLine();
      if (player1Move == true) {
      player1.makeMove(4,true);
      } else {
        player2.AI1();
      }
      player1Move = !player1Move;
    }
    board.display();
  }
}
