package domain.chanceCards;

import domain.ChanceDeck;
import domain.Player;
import services.TxtReader;

public class PayCard extends ChanceCard {

    private final int amount;
    private final TxtReader cardsTxt;

    public PayCard(String type, String description, ChanceDeck chanceDeck, int amount, TxtReader cardsTxt) {
        super(type, description, chanceDeck);
        this.amount = amount;
        this.cardsTxt = cardsTxt;
    }

    public int applyEffect(Player player) {
        if (player.attemptToPay(amount)) {
            player.withdraw(amount);
            return 0;
        }
        return amount;
    }

}
