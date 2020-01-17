package domain.chanceCards;

import domain.Board;
import domain.Player;
import domain.squares.OwnableSquare;

import java.util.Arrays;

public class PayHouseCard extends ChanceCard {
    private final int amount;
    protected final Board board;

    public PayHouseCard(String type, String description, int amount, Board board) {
        super(type, description);
        this.amount = amount;
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
        //Checks which card is pulled and sets the hotel price (Bad method)
        if (amount == 500) {
            tempAmount = 2000;
        } else {
            tempAmount = 2300;
        }
        //Find the amount of realEstateSquares owned by player and count houses + hotels
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
        //Attempt to pay
        int toPay = tempHouses * amount + tempHotels * tempAmount;
        if (player.attemptToPay(toPay)) {
            player.withdraw(toPay);
            return 0;
        } else {
            return toPay;
        }
    }

}
