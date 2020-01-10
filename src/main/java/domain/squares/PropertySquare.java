package domain.squares;


import controllers.GUILogic;
import domain.Board;
import domain.Player;
import services.TxtReader;

// Property square is the square that can be owned and other players, who land on it, pays to the owner.
public class PropertySquare extends OwnableSquare {
    
    private final int HOUSE_PRICE;
    private final int PRICE_IF_OWNING_ALL;
    private int[] rentLadder;
    private boolean hasBuilding = false;

    public PropertySquare(String name, int index, TxtReader landedOnTxt, int price, String type, String color, int HOUSE_PRICE, int[] rentLadder, Board board) {
        super(name, index, landedOnTxt, price, type , color, board);
        this.HOUSE_PRICE = HOUSE_PRICE;
        this.rentLadder = rentLadder;
        PRICE_IF_OWNING_ALL = rentLadder[0]*2;
    }
    
    public int getHOUSE_PRICE(){
        return HOUSE_PRICE;
    }
    
    public int getPRICE_OF_OWNING_ALL(){
        return PRICE_IF_OWNING_ALL;
    }
    
    public int[] getRentLadder(){
        return rentLadder;
    }
    
    @Override
    public String getInfo(){
        
        return
                this.getLandedOnTxt().getLine("Status") + " " +
                "\n" + getLandedOnTxt().getLine("Price pr. house/hotel") + " " + getHOUSE_PRICE() +
                "\n" + getLandedOnTxt().getLine("Pledge value") + " " + getPLEDGE_VALUE() +
                "\n" + getLandedOnTxt().getLine("Standard rent") + " " + getRentLadder()[0] +
                "\n" + getLandedOnTxt().getLine("1 house") + " " + getRentLadder()[1] +
                "\n" + getLandedOnTxt().getLine("2 houses") + " " + getRentLadder()[2] +
                "\n" + getLandedOnTxt().getLine("3 houses") + " " + getRentLadder()[3] +
                "\n" + getLandedOnTxt().getLine("4 houses") + " " + getRentLadder()[4] +
                "\n" + getLandedOnTxt().getLine("1 hotel") + " " + getRentLadder()[5] +
                "\n" + getLandedOnTxt().getLine("Price at same color") + " " + getPRICE_OF_OWNING_ALL()
                ;
        
    }
    
    @Override
    public int getRent() {
        return 1000;
    }
    
    
//    @Override
//    public int getRent() {
//        
//        //If the square has an owner
//        if(getOwner() != null){
//
//            //If the square has a building
//            if(hasBuilding){
//
//            } else{
//
//                //If owner owns all properties
//                if ()
//
//            }
//
//
//
//        } else {
//            return rentLadder[0];
//        }
//
//
//    }
    
}
