package domain.chanceCards;

import domain.Player;

public class PayCard extends ChanceCard {

    private final int amount;

    public PayCard(String type, String description, int amount) {
        super(type, description);
        this.amount = amount;
    }

    //The player tries to pay the amount
    public int applyEffect(Player player) {
        if (player.attemptToPay(amount)) {
            player.withdraw(amount);
            return 0;
        }
        return amount;
    }

}
