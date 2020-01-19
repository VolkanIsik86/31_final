package domain.chanceCards;

import domain.Player;

public class EarnCard extends ChanceCard {

    private final int amount;

    public EarnCard(String type, String description, int amount) {
        super(type, description);
        this.amount = amount;
    }

    //Adds x amount to the players account
    public int applyEffect(Player player) {

        player.deposit(amount);
        return 0;
    }

    public int getAmount() {
        return amount;
    }
}

