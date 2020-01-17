package domain.squares;

import domain.ChanceDeck;
import domain.Player;
import services.TxtReader;

// Chance holds chance cards that affect player randomly
public class ChanceSquare extends Square {

    protected ChanceDeck chanceDeck;

    //Constructor
    public ChanceSquare(String name, int index, TxtReader landedOnTxt, ChanceDeck chanceDeck) {
        super(name, index, landedOnTxt);
        this.chanceDeck = chanceDeck;
    }

    // Pulls a random card from chancecards array and affects the player with it.
    public String landedOn(Player player) {
        return "Press OK"+"S";
    }
}
