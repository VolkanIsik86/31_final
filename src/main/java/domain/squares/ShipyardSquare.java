package domain.squares;


import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

// Property square is the square that can be owned and other players, who land on it, pays to the owner.
public class ShipyardSquare extends OwnableSquare {

    public ShipyardSquare(String name, int index, TxtReader landedOnTxt, int price, String type ,String color) {
        super(name, index, landedOnTxt, price, type , color);
        
    }
    
    @Override
    public String getInfo(){
        return
                getLandedOnTxt().getLine("Status") + " " +
                "\n" + getLandedOnTxt().getLine("Pledge value") + " " + getPLEDGE_VALUE() +
                "\n" + getLandedOnTxt().getLine("Rent shipyard") +
                "\n\n" + getLandedOnTxt().getLine("Rent when owning more shipyards")
        ;
    }
    
}
