package domain.squares;

import domain.Player;
import services.TxtReader;

//This square moves player to jail
public class GoToJailSquare extends Square {

    public GoToJailSquare(String name, int index, TxtReader landedOnTxt) {
        super(name, index, landedOnTxt);
        
    }
    // Moves player to jail.
    public String landedOn(Player player) {
        return "GoToJail square" + " J";
    }
    
}
