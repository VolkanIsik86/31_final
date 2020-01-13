package domain.chanceCards;

import controllers.GUILogic;
import domain.Board;
import domain.ChanceDeck;
import domain.Player;
import domain.squares.PropertySquare;
import services.TxtReader;

public class PayHouseCard extends ChanceCard{
    private final int amount;
    private final TxtReader cardsTxt;
    protected Board board;

    public PayHouseCard(String type, String description, GUILogic guiLogic, ChanceDeck chanceDeck, int amount, TxtReader cardsTxt) {
        super(type, description, guiLogic, chanceDeck);
        this.amount = amount;
        this.cardsTxt = cardsTxt;
    }

    public int getHouses(PropertySquare property, Player p){
        return property.getHouses();
    }

    public void applyEffect(Player player){
        int tempHouses = 0;
        int tempHotels = 0;
        for(int i = 0; i < board.getOwnables().length;i++){
            if(board.getOwnables()[i].getOwner()==player){
                if(getHouses((PropertySquare)board.getOwnables()[i],player) < 5){
                    tempHouses += getHouses((PropertySquare)board.getOwnables()[i],player);
                } else {
                    tempHotels += 1;
                }
            }
        }
        player.attemptToPay(tempHotels*amount*4+tempHouses*amount);
    }

}
