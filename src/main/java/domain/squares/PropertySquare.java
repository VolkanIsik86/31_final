package domain.squares;


import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

// Property square is the square that can be owned and other players, who land on it, pays to the owner.
public class PropertySquare extends OwnableSquare {
    
    private final int HOUSE_PRICE;
    private final int PRICE_IF_OWNING_ALL;
    private int[] rentLadder;

    public PropertySquare(String name, int index, TxtReader landedOnTxt, int price,int rent,String type, String color, int HOUSE_PRICE, int[] rentLadder) {
        super(name, index, landedOnTxt, price, rent, type , color);
        this.HOUSE_PRICE = HOUSE_PRICE;
        PRICE_IF_OWNING_ALL = this.getPrice()*2;
        this.rentLadder = rentLadder;
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
                "\n" + this.getLandedOnTxt().getLine("#Houses/hotels") + " " +  "/" +
                "\n" + this.getLandedOnTxt().getLine("Price pr. house/hotel") + " " + this.getHOUSE_PRICE() +
                "\n" + this.getLandedOnTxt().getLine("Pledge value") + " " + this.getPLEDGE_VALUE() +
                "\n" + this.getLandedOnTxt().getLine("Standard rent") + " " + this.getRentLadder()[0] +
                "\n" + this.getLandedOnTxt().getLine("1 house") + " " + this.getRentLadder()[1] +
                "\n" + this.getLandedOnTxt().getLine("2 houses") + " " + this.getRentLadder()[2] +
                "\n" + this.getLandedOnTxt().getLine("3 houses") + " " + this.getRentLadder()[3] +
                "\n" + this.getLandedOnTxt().getLine("4 houses") + " " + this.getRentLadder()[4] +
                "\n" + this.getLandedOnTxt().getLine("1 hotel") + " " + this.getRentLadder()[5] +
                "\n" + this.getLandedOnTxt().getLine("Price at same color") + " " + this.getPRICE_OF_OWNING_ALL()
                ;
        
    }
    
}
