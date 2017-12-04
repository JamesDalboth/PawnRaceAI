public class Player {
  private Game game;
  private Color color;
  private Board board;
  private boolean isComputerPlayer;
  private Player opp;

  public Player(Game gm, Board brd, Color clr, boolean isCP){
    game = gm;
    color = clr;
    board = brd;
    isComputerPlayer = isCP;
  }

  public void setOpponent(Player opponent){
    opp = opponent;
  }

  public Color getColor(){
    return color;
  }


}
