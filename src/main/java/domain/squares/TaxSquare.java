package domain.squares;

import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

public class TaxSquare extends Square {
    private String message;
    private int tax = 1000;
    private int index;

    public TaxSquare(String name, int index, TxtReader landedOnTxt) {
        super(name, index, landedOnTxt);
        this.index = index;
    }

    public void setTax(int tax) {this.tax = tax;}

    public void payTax(Player p){p.withdraw(this.tax);}

    @Override
    public String landedOn(Player p) {
        message = "Tax square";
        return message;
    }
}

