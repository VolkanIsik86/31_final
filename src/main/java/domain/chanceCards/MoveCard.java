package domain.chanceCards;

import controllers.GUILogic;
import domain.Board;
import domain.ChanceDeck;
import domain.Player;
import domain.squares.Square;

public class MoveCard extends ChanceCard {

    protected final int moves;
    protected final Board board;

    public MoveCard(String type, String description, ChanceDeck chanceDeck, int moves, Board board) {
        super(type, description, chanceDeck);
        this.moves = moves;
        this.board = board;
    }

    public int applyEffect(Player player) {
        return moves;
    }

}
