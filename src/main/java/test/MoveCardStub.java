package test;

import controllers.GUILogic;
import domain.Board;
import domain.ChanceDeck;
import domain.Player;
import domain.chanceCards.MoveCard;
import domain.squares.Square;

public class MoveCardStub extends MoveCard {
    
    public MoveCardStub (String type, String description, ChanceDeck chanceDeck, int moves, Board board){
        super(type, description, chanceDeck, moves, board);
    }
    
    public int applyEffect(Player player){
        Square nextLocation = board.nextLocation(player, moves);
        player.setLocation(nextLocation);

    
        System.out.print("Står nu på: " + player.getLocation().getIndex());
        System.out.println(", " + player.getLocation().getClass());
        
        nextLocation.landedOn(player);
        return moves;
    }
    
    
    
}
