package domain.chanceCards;

//Chance card that has point and move modifier.

import controllers.GUILogic;
import domain.ChanceDeck;
import domain.Player;

public abstract class ChanceCard {
    
    private final String type;
    private final String description;

    ChanceCard(String type, String description, ChanceDeck chanceDeck) {
        this.type = type;
        this.description = description;
    }

    public String getDescription(){
        return description;
    }
    
    public String getType() {
        return type;
    }
    
    public abstract int applyEffect(Player p);
    
}
