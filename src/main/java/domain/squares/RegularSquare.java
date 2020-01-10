package domain.squares;

import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

//Start, fængsel, parkering squares

public class RegularSquare extends Square {
    private String message;

    public RegularSquare(String name, int index, TxtReader landedOnTxt) {
        super(name, index, landedOnTxt);
    }

    @Override
    public String landedOn(Player p) {
        message = "Regular square";
        return message;

    }

}
