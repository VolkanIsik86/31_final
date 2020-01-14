package domain.chanceCards;

import controllers.GUILogic;
import domain.Board;
import domain.ChanceDeck;
import domain.Player;
import domain.squares.OwnableSquare;
import services.TxtReader;

import java.util.Arrays;

public class PayHouseCard extends ChanceCard {
    private final int amount;
    private final TxtReader cardsTxt;
    protected Board board;

    public PayHouseCard(String type, String description, GUILogic guiLogic, ChanceDeck chanceDeck, int amount, TxtReader cardsTxt) {
        super(type, description, guiLogic, chanceDeck);
        this.amount = amount;
        this.cardsTxt = cardsTxt;
    }

    private OwnableSquare[] getRealEstateSquares(Player player) {
        OwnableSquare[] temp = new OwnableSquare[board.getOwnables().length];
        int j = 0;
        for (int i = 0; i < board.getOwnables().length; i++) {
            final OwnableSquare ownableSquare = board.getOwnables()[i];
            if (ownableSquare.getOwner() == player && ownableSquare.isRealEstate()) {
                temp[j++] = ownableSquare;
            }
        }
        return Arrays.copyOf(temp, j);
    }

    public void applyEffect(Player player) {
        int tempHouses = 0;
        int tempHotels = 0;
        int tempAmount;
        //Dårlig workaround for specialpriser til hoteller
        if(amount==500){
            tempAmount = 2000;
        }else{
            tempAmount = 2300;
        }
        OwnableSquare[] realEstateSquares = getRealEstateSquares(player);
        for (OwnableSquare realEstateSquare : realEstateSquares) {
            int houseCount = realEstateSquare.getHouseCount();
            if (houseCount < 5) {
                tempHouses += houseCount;
            } else {
                tempHotels++;
            }
        }
        player.attemptToPay(tempHouses*amount+tempHotels*tempAmount);
    }

}
