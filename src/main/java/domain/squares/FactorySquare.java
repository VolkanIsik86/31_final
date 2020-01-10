package domain.squares;

import controllers.GUILogic;
import services.TxtReader;

public class FactorySquare extends OwnableSquare{
    
    private final int RENT_MODIFIER = 100;

    public FactorySquare(String name, int index, TxtReader landedOnTxt, int price, String type , String color) {
        super(name, index, landedOnTxt, price, type , color);
    }
    
    @Override
    public String getInfo(){
        return
                getLandedOnTxt().getLine("Status") + " " +
                "\n" + getLandedOnTxt().getLine("Pledge value") + " " + getPLEDGE_VALUE() +
                "\n" + getLandedOnTxt().getLine("Rent factories") +
                "\n\n" + getLandedOnTxt().getLine("Rent when owning all factories")
                ;
        
    }
    
    
}
