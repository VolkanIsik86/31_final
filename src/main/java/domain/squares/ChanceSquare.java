package domain.squares;

import controllers.GUILogic;
import domain.chanceCards.ChanceCard;
import domain.ChanceDeck;
import domain.Player;
import services.TxtReader;

// Chance holds chance cards that affect player randomly
public class ChanceSquare extends Square {
    
    protected final ChanceDeck chanceDeck;
    private String message;
    
    //Constructor
    public ChanceSquare(String name, int index, GUILogic guiLogic, TxtReader landedOnTxt, ChanceDeck chanceDeck) {
        super(name, index, guiLogic, landedOnTxt);
        this.chanceDeck = chanceDeck;
    }

    // Pulls a random card from chancecards array and affects the player with it.
    public String landedOn(Player player) {
    
        message = "Chance square";
        ChanceCard pulledCard = chanceDeck.pullRandomChanceCard();
        guiLogic.showChanceCard(pulledCard.getDescription());
        message = "Press OK";
        pulledCard.applyEffect(player);
        return message;
    }
}
