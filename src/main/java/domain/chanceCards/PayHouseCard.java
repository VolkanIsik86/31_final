package domain.chanceCards;

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

    public PayHouseCard(String type, String description, int amount, TxtReader cardsTxt, Board board) {
        super(type, description);
        this.amount = amount;
        this.cardsTxt = cardsTxt;
        this.board = board;
    }

    //Making an array of a players owned squares
    private OwnableSquare[] getRealEstateSquares(Player player) {
        if (board.getOwnables().length != 0) {
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
        return null;
    }
    //Calculates the amount a user has to pay, for each house and/or hotels on players ownableSquares
    public int applyEffect(Player player) {
        int tempHouses = 0;
        int tempHotels = 0;
        int tempAmount;
        //Dårlig workaround for specialpriser til hoteller
        if (amount == 500) {
            tempAmount = 2000;
        } else {
            tempAmount = 2300;
        }
        OwnableSquare[] realEstateSquares = getRealEstateSquares(player);
        assert realEstateSquares != null;
        for (OwnableSquare realEstateSquare : realEstateSquares) {
            int houseCount = realEstateSquare.getHouseCount();
            if (houseCount < 5) {
                tempHouses += houseCount;
            } else {
                tempHotels++;
            }
        }
        int toPay = tempHouses * amount + tempHotels * tempAmount;
        if (player.attemptToPay(toPay)) {
            player.withdraw(toPay);
            return 0;
        } else {
            return toPay;
        }
    }

}
