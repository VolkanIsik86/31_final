package domain.squares;

import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

public class TaxSquare extends Square {
    private String message;
    private int tax;

    public TaxSquare(String name, int index, TxtReader landedOnTxt, int tax) {
        super(name, index, landedOnTxt);
        this.tax = tax;
    }

    @Override
    public String landedOn(Player p) {
        message = "Tax square";
        if(!p.attemptToPay(tax)){
            p.setLost(true);
        }
           p.withdraw(tax);
        return message;
    }
    
    @Override
    public String getInfo(){
        return "todo";
    }
    
}

