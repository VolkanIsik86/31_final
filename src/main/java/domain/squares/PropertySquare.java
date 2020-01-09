package domain.squares;


import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

// Property square is the square that can be owned and other players, who land on it, pays to the owner.
public class PropertySquare extends OwnableSquare {

    public PropertySquare(String name, int index, GUILogic guiLogic, TxtReader landedOnTxt, int price,int rent,String type, String color) {
        super(name, index, guiLogic, landedOnTxt, price, rent, type , color);


    }
}
