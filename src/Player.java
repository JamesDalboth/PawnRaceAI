public class Player {
  private Game game;
  private Color color;
  private Board board;
  private boolean isComputerPlayer;

  public Player(Game gm, Board brd, Color clr, boolean isCP){
    game = gm;
    color = clr;
    board = brd;
    isComputerPlayer = isCP;
  }


}
