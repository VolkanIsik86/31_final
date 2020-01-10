package domain.squares;

import controllers.GUILogic;
import domain.Board;
import domain.Player;
import services.TxtReader;

//This square moves player to jail
public class GoToJailSquare extends Square {
    
    private Square jail;
    private String message;
    
    public GoToJailSquare(String name, int index, TxtReader landedOnTxt,  Square jail) {
        super(name, index, landedOnTxt);
        this.jail = jail;
        
    }

    // Moves player to jail.
    public String landedOn(Player player) {
        message = "GoToJail square";
        player.setLocation(jail);
        player.setJail(true);
        return message;
    }
}
