public class PawnRace {
  private boolean player1Human;
  private boolean player2Human;
  public static void (String[] args) {
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

    Board board = new Board(String[2].charAt(0),String[3].charAt(0))
    Game game = new Game(board);
    Player player1 = new Player(game,board,Color.WHITE,player1Human);
    Player player2 = new Player(game,board,Color.BLACK,player2Human);
    boolean player1Move = True;

  }
}
