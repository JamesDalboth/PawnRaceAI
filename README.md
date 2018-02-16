# PawnRaceAI

# About

This was for a programmming competetion where the goal was to create a pawn race chess AI written in java, we then went in a round by 
round tournament. I got beaten in the quater-finals (Although the guy who beat me won so who knows how far I could of got..)

# The Rules

Players agree who will be black and who will be white. Both players set up their board normally except only the pawns are placed and the
black player gets to choose one pawn from each side to remove leaving a gap in each pawn rank.

The game then commences with the aim of the game being to get your pawn to the other players side. The first player to do so wins!

# My AI

My Ai works using a fairly standard method of minmax. If you don't know what minmax is then have a google. I also used alpha-beta pruning
to improve the efficency. The hardest part was trying to look at a board and give it a score. I originally just summed the height of each
pawn to get the score of each side. (where the height is how close you are to the other side). I hoped this would be better than it is
because the ai might opt to take another players piece which would obviously lower their score. However the AI still just ran to the end
of the board. I ended up making scale none linearly, and added in pattern matching to look for certain patterns such as a vulnerable pawn
or a supporting pawn. Performance wise the AI was really slow so I initially set the search depth to 1 (this was the main fault of my
program, I should of started it out at like 5, this meant my ai made dumb moves at the start which other ai's exploited) and slowly
increased the search depth as time went on.
