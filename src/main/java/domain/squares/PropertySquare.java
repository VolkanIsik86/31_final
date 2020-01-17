package domain.squares;


import domain.Board;
import services.TxtReader;

// Property square is the square that can be owned and other players, who land on it, pays to the owner.
@SuppressWarnings("UnusedReturnValue")
public class PropertySquare extends OwnableSquare {

    private final int HOUSE_PRICE;
    private final int PRICE_IF_OWNING_ALL;
    private final int[] rentLadder;
    private int numberOfHouse;

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
    public boolean isRealEstate(){
        return true;
    }


    @Override
    public int getHouseCount(){
        return numberOfHouse;
    }

    @Override
    public String getInfo(){

        return
                getName() +
//                this.getLandedOnTxt().getLine("Status") + " " +
                "\n" + getLandedOnTxt().getLine("Price pr. house/hotel") + " " + getHOUSE_PRICE() +
//                "\n" + getLandedOnTxt().getLine("Pledge value") + " " + getPLEDGE_VALUE() +
                "" + getLandedOnTxt().getLine("Standard rent") + " " + getRentLadder()[0] +
                "\n" + getLandedOnTxt().getLine("Price at same color") + " " + getPRICE_OF_OWNING_ALL() +
                "\n" + getLandedOnTxt().getLine("1 house") + " " + getRentLadder()[1] +
                "\n" + getLandedOnTxt().getLine("2 houses") + " " + getRentLadder()[2] +
                "\n" + getLandedOnTxt().getLine("3 houses") + " " + getRentLadder()[3] +
                "\n" + getLandedOnTxt().getLine("4 houses") + " " + getRentLadder()[4] +
                "\n" + getLandedOnTxt().getLine("1 hotel") + " " + getRentLadder()[5]
                ;

    }

    public int addHouse(){
        if(numberOfHouse < 5){

            numberOfHouse++;
            return getHOUSE_PRICE();
        }
        return 0;
    }

    public int getValue(){
        int value = 0;
        value = getPrice() + value;
        value = value + (getHOUSE_PRICE() * this.numberOfHouse);
        return value;
    }

    @Override
    public void updateRent(int lastRoll) {

        if(getOwner().getJail()){
            setRent(0);
        } else {
    
            //If the square has an owner
            if(getOwner() != null){
        
                //If the square has a building
                if(numberOfHouse > 0){
            
                    switch(numberOfHouse){
                        case 1:
                            setRent(rentLadder[1]);
                            break;
                        case 2:
                            setRent(rentLadder[2]);
                            break;
                        case 3:
                            setRent(rentLadder[3]);
                            break;
                        case 4:
                            setRent(rentLadder[4]);
                            break;
                        case 5:
                            setRent(rentLadder[5]);
                            break;
                        default:
                            setRent(0);
                    }
            
                } else{
            
                    //If owner owns all properties
                    if (board.searchColors(this) == 0 ){
                        setRent(rentLadder[0]*2);
                    } else {
                        setRent(rentLadder[0]);
                    }
                }
            }
        }
    }
    
}
