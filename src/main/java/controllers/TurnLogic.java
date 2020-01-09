package controllers;

import domain.*;
import domain.chanceCards.ChanceCard;
import domain.squares.*;
import services.TxtReader;

public class TurnLogic {
    
    protected Board board;
    protected GUILogic guiLogic;
    protected TxtReader turnLogicTxt;
    protected TxtReader cardsTxt;
    ChanceDeck chanceDeck;
    protected final Die die = new Die();
    int roll1, roll2, rollSum;

    public void init(Board board, GUILogic guiLogic, TxtReader turnLogicTxt, TxtReader cardsTxt){
        this.board = board;
        this.guiLogic = guiLogic;
        this.turnLogicTxt = turnLogicTxt;
        this.cardsTxt = cardsTxt;
        chanceDeck = new ChanceDeck(guiLogic, cardsTxt, board);
    }

    public void takeTurn(Player player ) {
        
        guiLogic.showMessage(turnLogicTxt.getLine("It is") + " " + player.getName() + turnLogicTxt.getLine("s"));
    
        boolean endTurn = false;
        boolean hasThrown = false;
        String choice;
        
        //Start of user menu loop
        while(endTurn == false){
            
            //Displays the correct menu, depending on whether or not the player has already thrown the dice
            if(hasThrown == false){
                choice = guiLogic.getUserButtonPressed(turnLogicTxt.getLine("Choose option"),  turnLogicTxt.getLine("Throw"),turnLogicTxt.getLine("Properties"));
            } else{
                choice = guiLogic.getUserButtonPressed(turnLogicTxt.getLine("Choose option"),   turnLogicTxt.getLine("Properties"), turnLogicTxt.getLine("End"));
            }
           
            //Depending on menu choice
            if (choice.equals(turnLogicTxt.getLine("Throw"))){
        
                hasThrown = true;
    
                //Roll the dice
                rollDice();
                player.setLastRoll((rollSum));
                guiLogic.displayDie(roll1, roll2);
                
                //Calculate and move to next location
                Square nextLocation = board.nextLocation(player, rollSum);
                player.setLocation(nextLocation);
                guiLogic.movePiece(player, player.getLastRoll());
    
                //Apply the square's effect to the player
                String message = nextLocation.landedOn(player);
    
                //checker om en spiller har købt en grund. Hvis vedkommende har, så opdaterer GUILogic til at vise den nye ejer af grunden.
                if (message.charAt(message.length()-1) == 'T'){
                    guiLogic.setSquareOwner(player);
                    message = message.substring(0,message.length()-1);
                }
                if (message.charAt(message.length()-1) == 'S'){
                    ChanceCard pulledCard = chanceDeck.pullRandomChanceCard();
                    guiLogic.showChanceCard(pulledCard.getDescription());
                    pulledCard.applyEffect(player);
                    message = message.substring(0,message.length()-1);
                }
    
                if (message.equals("GoToJail square"))
                    guiLogic.moveToJail(player);
    
                guiLogic.showMessage(turnLogicTxt.getLine(message));
    
                guiLogic.setPlayerBalance(player);
        
            } else if (choice.equals(turnLogicTxt.getLine("Properties"))){
                String selection = guiLogic.getUserSelection(turnLogicTxt.getLine("Choose property"),"opt1","opt2");
        
                guiLogic.getUserButtonPressed(turnLogicTxt.getLine("Choose option"),turnLogicTxt.getLine("House"),turnLogicTxt.getLine("Pledge"),turnLogicTxt.getLine("Trade"),turnLogicTxt.getLine("Back"));
        
            } else if (choice.equals(turnLogicTxt.getLine("End"))) {
                endTurn = true;
                guiLogic.showMessage(turnLogicTxt.getLine("End turn"));
            }
        }
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
    
    private void rollDice(){
        die.roll();
        roll1 = die.getFaceValue();
        die.roll();
        roll2 = die.getFaceValue();
        rollSum = roll1+roll2;
    }
    
    private void updateGUI(Player player){

    }

    
    
}
