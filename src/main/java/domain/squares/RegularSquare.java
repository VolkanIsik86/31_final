package domain.squares;

import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

//Start, fængsel, parkering squares
public class RegularSquare extends Square {
    private String message;

    public RegularSquare(String name, int index, GUILogic guiLogic, TxtReader landedOnTxt) {
        super(name, index, guiLogic, landedOnTxt);
    }

    @Override
    public String landedOn(Player p) {
        message = "Regular square";
        return message;

    }


}
