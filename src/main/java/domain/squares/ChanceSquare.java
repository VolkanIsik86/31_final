package domain.squares;

import domain.Player;
import services.TxtReader;

// Chance holds chance cards that affect player randomly
public class ChanceSquare extends Square {


    //Constructor
    public ChanceSquare(String name, int index, TxtReader landedOnTxt) {
        super(name, index, landedOnTxt);
    }

    // Pulls a random card from chancecards array and affects the player with it.
    public String landedOn(Player player) {

        String message = "Press OK";
        message = message + "S";
        return message;
    }
}
