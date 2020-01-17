package domain.chanceCards;

import domain.Board;
import domain.Player;
import domain.squares.Square;

public class MoveCard extends ChanceCard {

    protected final int moves;
    protected final Board board;

    public MoveCard(String type, String description, int moves, Board board) {
        super(type, description);
        this.moves = moves;
        this.board = board;
    }

    //Moves the player x squares
    public int applyEffect(Player player) {
        Square nextLocation = board.nextLocation(player, moves);
        player.setLocation(nextLocation);


        nextLocation.landedOn(player);
        return moves;
    }

}
