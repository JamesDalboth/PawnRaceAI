
public class PawnRace {
  private static boolean player1Human;
  private static boolean player2Human;
  private static int depth = 6;
  public static void main(String[] args) {
    if (args[0].charAt(0) == 'P') {
      player1Human = true;
    } else {
      player1Human = false;
    }
    if (args[1].charAt(0) == 'P') {
      player2Human = true;
    } else {
      player2Human = false;
    }

    Board board = new Board(args[2].charAt(0),args[3].charAt(0));
    Game game = new Game(board);
    Player player1 = new Player(game,board,Color.WHITE,!player1Human);
    Player player2 = new Player(game,board,Color.BLACK,!player2Human);
    boolean player1Move = true;
    while (!game.isFinished()) {
      board.display();
      if (player1Move == true) {
        player1.makeMove2(depth,true);
      } else {
        player2.makeMove2(depth,false);
      }
      player1Move = !player1Move;
    }
    board.display();
    System.out.println("The winner is -> " + game.getGameResult());
  }
}
