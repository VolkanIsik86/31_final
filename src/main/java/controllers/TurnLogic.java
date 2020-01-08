package controllers;

import domain.*;
import domain.squares.*;
import services.TxtReader;

public class TurnLogic {
    
    protected Board board;
    protected GUILogic guiLogic;
    protected TxtReader landedOnTxt;
    protected final Die die = new Die();

    public void init(Board board, GUILogic guiLogic, TxtReader landedOnTxt){
        this.board = board;
        this.guiLogic = guiLogic;
        this.landedOnTxt = landedOnTxt;
    }

    public void takeTurn(Player player ) {

        //Roll the die
        die.roll();
        int roll = die.getFaceValue();
        player.setLastRoll(roll);
        guiLogic.displayDie(roll, player.getName());

        //Calculate and move to next location
        Square nextLocation = board.nextLocation(player, die.getFaceValue());
        player.setLocation(nextLocation);
        guiLogic.movePiece(player, player.getLastRoll());

        //Apply the square's effect to the player
        String message = nextLocation.landedOn(player);




        guiLogic.setPlayerBalance(player);
        guiLogic.setSquareOwner(player);
        guiLogic.showMessage(landedOnTxt.getLine(message));

        guiLogic.showMessage(landedOnTxt.getLine("End turn"));

    }

    private void updateGUI(Player player){

    }

}
