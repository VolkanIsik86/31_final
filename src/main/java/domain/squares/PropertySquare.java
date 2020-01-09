package domain.squares;


import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

// Property square is the square that can be owned and other players, who land on it, pays to the owner.
public class PropertySquare extends OwnableSquare {

    public PropertySquare(String name, int index, TxtReader landedOnTxt, int price,int rent,String type) {
        super(name, index, landedOnTxt, price, rent, type);


    }
}
