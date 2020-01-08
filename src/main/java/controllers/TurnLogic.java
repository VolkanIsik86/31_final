package controllers;

import domain.*;
import domain.squares.*;
import services.TxtReader;

public class TurnLogic {
    
    protected Board board;
    protected GUILogic guiLogic;
    protected TxtReader landedOnTxt;
    protected final Die die = new Die();
    protected final Die die2 = new Die();
    
    public void init(Board board, GUILogic guiLogic, TxtReader landedOnTxt){
        this.board = board;
        this.guiLogic = guiLogic;
        this.landedOnTxt = landedOnTxt;
    }
    
    public void takeTurn(Player player) {
    
        //Roll the die
        die.roll();
        int roll1 = die.getFaceValue();
        die2.roll();
        int roll2 = die2.getFaceValue();
        int roll = roll1+roll2;
        player.setLastRoll((roll));
        guiLogic.displayDie(roll1, roll2, player.getName());
    
        //Calculate and move to next location
        Square nextLocation = board.nextLocation(player, roll);
        player.setLocation(nextLocation);
        guiLogic.movePiece(player, player.getLastRoll());
    
        //Apply the square's effect to the player
        nextLocation.landedOn(player);
    
        guiLogic.showMessage(landedOnTxt.getLine("End turn"));
        
    }
}
