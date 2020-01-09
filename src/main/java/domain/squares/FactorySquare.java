package domain.squares;

import controllers.GUILogic;
import services.TxtReader;

public class FactorySquare extends OwnableSquare{

    public FactorySquare(String name, int index, GUILogic guiLogic, TxtReader landedOnTxt, int price, int rent, String type) {
        super(name, index, guiLogic, landedOnTxt, price, rent, type);
    }
}
