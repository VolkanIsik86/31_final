package domain.chanceCards;

import domain.ChanceDeck;
import domain.Player;

public class WithDrawBalanceCard extends ChanceCard{

    final int amount;

    public WithDrawBalanceCard(String type, String description, ChanceDeck chanceDeck, int amount) {
        super(type, description, chanceDeck);
        this.amount = amount;
    }

    @Override
    public int applyEffect(Player p) {
        return 0;
    }
}
