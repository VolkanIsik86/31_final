package domain.squares;

import controllers.GUILogic;
import services.TxtReader;

public class FactorySquare extends OwnableSquare{
    
    private final int PRICE_IF_OWNING_ALL;

    public FactorySquare(String name, int index, TxtReader landedOnTxt, int price, int rent, String type , String color) {
        super(name, index, landedOnTxt, price, rent, type , color);
        PRICE_IF_OWNING_ALL = this.getPrice()*2;
    }
    
    public int PRICE_OF_OWNING_ALL(){
        return PRICE_IF_OWNING_ALL;
    }
    
    
}
