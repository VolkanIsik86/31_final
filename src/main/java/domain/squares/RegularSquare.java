package domain.squares;

import domain.Player;
import services.TxtReader;

//Start, fængsel, parkering squares
public class RegularSquare extends Square {

    public RegularSquare(String name, int index, TxtReader landedOnTxt) {
        super(name, index, landedOnTxt);
    }

    @Override
    public String landedOn(Player p) {
        return "Regular square" +" R";
    }
}
