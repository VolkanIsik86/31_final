package domain.squares;

import domain.Player;
import services.TxtReader;

//This square moves player to jail
public class GoToJailSquare extends Square {
    
    private final Square jail;

    public GoToJailSquare(String name, int index, TxtReader landedOnTxt,  Square jail) {
        super(name, index, landedOnTxt);
        this.jail = jail;
        
    }
    // Moves player to jail.
    public String landedOn(Player player) {
        String message = "GoToJail square"+" J";
        player.setLocation(jail);
        player.setJail(true);
        return message;
    }
    
}
