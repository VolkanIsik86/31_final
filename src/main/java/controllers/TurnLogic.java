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

    public void takeTurn(Player player ) {

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
        String message = nextLocation.landedOn(player);




        guiLogic.setPlayerBalance(player);
        System.out.println("message");
        System.out.println(message.charAt(message.length()-1));

        if (message.charAt(message.length()-1) == 'T'){
            guiLogic.setSquareOwner(player);
        }

        guiLogic.showMessage(landedOnTxt.getLine(message));

        guiLogic.showMessage(landedOnTxt.getLine("End turn"));

    }
    void playRound(PlayerList playerList, String looser) {
        for (int i = 0; i < playerList.NumberOfPlayers(); i++) {

            Player currentPlayer = playerList.getPlayer(i);

            //If player is in jail
            if (currentPlayer.getJail()) {

                guiLogic.showMessage(landedOnTxt.getLine("In jail pay now"));

                if (currentPlayer.attemptToPay(1)) {
                    currentPlayer.withdraw(1);
                    guiLogic.setPlayerBalance(currentPlayer);
                    currentPlayer.setJail(false);
                } else {
                    currentPlayer.setLost(true);
                    currentPlayer.setBalance(0);
                    guiLogic.showMessage(landedOnTxt.getLine("Does not have fonds to pay"));
                    guiLogic.setPlayerBalance(currentPlayer);

                    looser = currentPlayer.getName();
                    break;
                }
            }

            takeTurn(currentPlayer);


            if (currentPlayer.getLost()) {
                looser = currentPlayer.getName();
                break;
            }
        }
    }

    private void updateGUI(Player player){

    }

}
