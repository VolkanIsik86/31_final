package domain.chanceCards;

import controllers.GUILogic;
import domain.ChanceDeck;
import domain.Player;

public class EarnCard extends ChanceCard {

    private final int amount;

    public EarnCard(String type, String description, ChanceDeck chanceDeck, int amount) {
        super(type, description, chanceDeck);
        this.amount = amount;
    }

    public int applyEffect(Player player) {

        player.deposit(amount);
        return 0;
    }

}

