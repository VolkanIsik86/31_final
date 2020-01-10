package domain.squares;


import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

// Property square is the square that can be owned and other players, who land on it, pays to the owner.
public class PropertySquare extends OwnableSquare {
    
    private final int HOUSE_PRICE;

    public PropertySquare(String name, int index, TxtReader landedOnTxt, int price,int rent,String type, String color, int HOUSE_PRICE) {
        super(name, index, landedOnTxt, price, rent, type , color);
        this.HOUSE_PRICE = HOUSE_PRICE;
    }
    
    public int getHOUSE_PRICE(){
        return HOUSE_PRICE;
    }
    
}
