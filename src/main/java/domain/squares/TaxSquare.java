package domain.squares;

import domain.Player;
import services.TxtReader;

public class TaxSquare extends Square {

    public TaxSquare(String name, int index, TxtReader landedOnTxt) {
        super(name, index, landedOnTxt);
    }

    @Override
    public String landedOn(Player p) {
        return "Tax square" + " T";
    }

}

