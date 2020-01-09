package controllers;

import domain.*;
import domain.squares.*;
import services.TxtReader;

public class TurnLogic {
    
    protected Board board;
    protected GUILogic guiLogic;
    protected TxtReader turnLogicTxt;
    protected final Die die = new Die();
    protected final Die die2 = new Die();

    public void init(Board board, GUILogic guiLogic, TxtReader turnLogicTxt){
        this.board = board;
        this.guiLogic = guiLogic;
        this.turnLogicTxt = turnLogicTxt;
    }

    public void takeTurn(Player player ) {
        
        guiLogic.showMessage(turnLogicTxt.getLine("It is") + turnLogicTxt.getLine("s"));
    
        boolean endTurn = false;
        boolean hasThrown = false;
        String choice;
        
        //Start of user menu loop
        while(endTurn == false){
            
            //Displays the correct menu, depending on whether or not the player has thrown the dice
            if(hasThrown == false){
                choice = guiLogic.getUserButtonPressed(turnLogicTxt.getLine("Choose option"),  turnLogicTxt.getLine("Throw"),turnLogicTxt.getLine("Properties"));
            } else{
                choice = guiLogic.getUserButtonPressed(turnLogicTxt.getLine("Choose option"),   turnLogicTxt.getLine("Properties"), turnLogicTxt.getLine("End"));
            }
           
            //Depending on menu choice
            if (choice.equals(turnLogicTxt.getLine("Throw"))){
        
                hasThrown = true;
                
                //Roll the dice
                die.roll();
                int roll1 = die.getFaceValue();
                die2.roll();
                int roll2 = die2.getFaceValue();
                int sumRoll = roll1+roll2;
                player.setLastRoll((sumRoll));
        
                guiLogic.displayDie(roll1, roll2);
        
                //Calculate and move to next location
                Square nextLocation = board.nextLocation(player, sumRoll);
                player.setLocation(nextLocation);
                guiLogic.movePiece(player, player.getLastRoll());
        
                //Apply the square's effect to the player
                String message = nextLocation.landedOn(player);
        
                guiLogic.setPlayerBalance(player);
                guiLogic.setSquareOwner(player);
                guiLogic.showMessage(turnLogicTxt.getLine(message));
        
            } else if (choice.equals(turnLogicTxt.getLine("Properties"))){
                String selection = guiLogic.getUserSelection(turnLogicTxt.getLine("Choose property"),"opt1","opt2");
        
                guiLogic.getUserButtonPressed(turnLogicTxt.getLine("Choose option"),turnLogicTxt.getLine("House"),turnLogicTxt.getLine("Pledge"),turnLogicTxt.getLine("Trade"),turnLogicTxt.getLine("Back"));
        
            } else if (choice.equals(turnLogicTxt.getLine("End"))) {
                endTurn = true;
            }
        }
        
        guiLogic.showMessage(turnLogicTxt.getLine("End turn"));
    }
    
    void playRound(PlayerList playerList, String looser) {
        for (int i = 0; i < playerList.NumberOfPlayers(); i++) {

            Player currentPlayer = playerList.getPlayer(i);

            //If player is in jail
            if (currentPlayer.getJail()) {

                guiLogic.showMessage(turnLogicTxt.getLine("In jail pay now"));

                if (currentPlayer.attemptToPay(1)) {
                    currentPlayer.withdraw(1);
                    guiLogic.setPlayerBalance(currentPlayer);
                    currentPlayer.setJail(false);
                } else {
                    currentPlayer.setLost(true);
                    currentPlayer.setBalance(0);
                    guiLogic.showMessage(turnLogicTxt.getLine("Does not have fonds to pay"));
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
