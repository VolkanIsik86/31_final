package domain.squares;

import domain.Player;
import services.TxtReader;

public class TaxSquare extends Square {
    private int tax;

    public TaxSquare(String name, int index, TxtReader landedOnTxt) {
        super(name, index, landedOnTxt);
    }

    public void setTax(int tax) {
        this.tax = tax;
    }

    public void payTax(Player p) {
        p.withdraw(this.tax);
    }

    @Override
    public String landedOn(Player p) {
        return "Tax square" + " T";
    }

}

