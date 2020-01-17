package domain.squares;

import domain.Board;
import services.TxtReader;

public class FactorySquare extends OwnableSquare {

    public FactorySquare(String name, int index, TxtReader landedOnTxt, int price, String type, String color, Board board) {
        super(name, index, landedOnTxt, price, type, color, board);
    }

    @Override
    public boolean isRealEstate() {
        return false;
    }

    @Override
    public int getHouseCount() {
        return 0;
    }

    //updates the rent from the eyes on the dice
    @Override
    public void updateRent(int lastRoll) {
        final int RENT_MODIFIER = 100;
    
        if(getOwner().getJail()){
            setRent(0);
        } else {
    
            switch (board.searchColors(this)) {
                case 0:
                    setRent(RENT_MODIFIER * 2 * lastRoll);
                    break;
                case 1:
                    setRent(RENT_MODIFIER * lastRoll);
                    break;
                default:
                    setRent(0);
            }
        }
    }

    @Override
    public String getInfo() {
        return
                getName() +
//                getLandedOnTxt().getLine("Status") + " " +
                "\n" + getLandedOnTxt().getLine("Pledge value") + " " + getPledge_Value() +
                "\n" + getLandedOnTxt().getLine("Rent factories") +
                "\n\n" + getLandedOnTxt().getLine("Rent when owning all factories")
                ;
        
    }
}
