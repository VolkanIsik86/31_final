package domain.squares;

import controllers.GUILogic;
import domain.Board;
import domain.Player;
import services.TxtReader;

//This square moves player to jail
public class GoToJailSquare extends Square {
    
    private final Board board;
    private String message;
    
    public GoToJailSquare(String name, int index, TxtReader landedOnTxt,  Board board) {
        super(name, index, landedOnTxt);
        this.board = board;
        
    }

    // Moves player to jail.
    public String landedOn(Player player) {
        message = "GoToJail square";
        player.setLocation(board.getJail());
        player.setJail(true);
        return message;
    }
    
}
