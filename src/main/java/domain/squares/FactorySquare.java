package domain.squares;

import controllers.GUILogic;
import services.TxtReader;

public class FactorySquare extends OwnableSquare{

    public FactorySquare(String name, int index, TxtReader landedOnTxt, int price, int rent, String type , String color) {
        super(name, index, landedOnTxt, price, rent, type , color);
    }
}
