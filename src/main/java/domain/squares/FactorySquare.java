package domain.squares;

import controllers.GUILogic;
import domain.Board;
import domain.Player;
import services.TxtReader;

public class FactorySquare extends OwnableSquare{
    
    private final int RENT_MODIFIER = 100;

    public FactorySquare(String name, int index, TxtReader landedOnTxt, int price, String type , String color, Board board) {
        super(name, index, landedOnTxt, price, type , color, board);
    }

    @Override
    public boolean isRealEstate(){
        return false;
    }

    @Override
    public int getHouseCount(){
        return 0;
    }

    @Override
    public void updateRent(int lastRoll) {
        
        switch(board.searchColors(this)) {
            case 0:
                setRent(RENT_MODIFIER * lastRoll);
                break;
            case 1:
                setRent(RENT_MODIFIER * 2 * lastRoll);
                break;
            default:
                setRent(0);
        }
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
