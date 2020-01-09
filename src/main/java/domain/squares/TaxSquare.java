package domain.squares;

import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

public class TaxSquare extends Square {
    private String message;
    private int tax;

    public TaxSquare(String name, int index, GUILogic guiLogic, TxtReader landedOnTxt, int tax) {
        super(name, index, guiLogic, landedOnTxt);
        this.tax = tax;
    }

    @Override
    public String landedOn(Player p) {
        message = "Tax square";
        p.attemptToPay(tax);
        return message;
    }
}

