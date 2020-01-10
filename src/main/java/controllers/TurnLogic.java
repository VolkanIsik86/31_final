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
    int roll1, roll2, rollSum = 0;
    private boolean hasThrown, ownsASquares;
    String[] menuItems;
    private String looser;

    public void init(Board board, GUILogic guiLogic, TxtReader turnLogicTxt, TxtReader cardsTxt){
        this.board = board;
        this.guiLogic = guiLogic;
        this.turnLogicTxt = turnLogicTxt;
        this.cardsTxt = cardsTxt;
        chanceDeck = new ChanceDeck(guiLogic, cardsTxt, board);
    }
    
    //todo implement an option when landing on a property if you want to buy it or not
    public String takeTurn(Player player) {
    
        String greeting = turnLogicTxt.getLine("It is") + " " +
                player.getName() + turnLogicTxt.getLine("s") + " " +
                turnLogicTxt.getLine("Choose option");
        
        boolean endTurn = false;
        hasThrown = false;
        String choice ="";
        
        
        //Start of user menu loop
        while(endTurn == false){
            if (player.getLost())
                return player.getName();

            ownsASquares = board.doesPlayerOwnAnySquares(player);

            //Chooses the correct menu items
            updateMenuItems();

            //Displays the menu
            choice = guiLogic.getUserButtonPressed(greeting, menuItems);

            //Depending on menu choice, program does...
            if (choice.equals(turnLogicTxt.getLine("Throw"))) {
                doTurn(player);

            } else if (choice.equals(turnLogicTxt.getLine("Properties"))) {
                manageProperties(player);

            }else if (getExtraturn()) {
                endTurn=false;
                guiLogic.showMessage("du får en ekstra tur");
            } else if (choice.equals(turnLogicTxt.getLine("End"))) {
                endTurn = true;
            }
        }
        return null;
    }

    String playRound(PlayerList playerList) {
        for (int i = 0; i < playerList.NumberOfPlayers(); i++) {

            Player currentPlayer = playerList.getPlayer(i);

            //If player is in jail
            if (currentPlayer.getJail()) {

                guiLogic.showMessage(turnLogicTxt.getLine("In jail pay now"));

                if (currentPlayer.attemptToPay(1000)) {
                    currentPlayer.withdraw(1000);
                    guiLogic.setPlayerBalance(currentPlayer);
                    currentPlayer.setJail(false);
                } else {
                    guiLogic.showMessage(turnLogicTxt.getLine("Does not have fonds to pay"));
                    guiLogic.setPlayerBalance(currentPlayer);
                    looser = currentPlayer.getName();
                    return looser;
                }
            }

            looser = takeTurn(currentPlayer);
            if (looser != null) {
                return looser;

            }
            // hvis spilleren slår to ens, får de en ekstra tur.
            if (getExtraturn()) {
                i = i - 1;
            }

        }
        return null;
    }

    private void doTurn(Player player){
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
        if (message.charAt(message.length() - 1) == 'T') {
            guiLogic.setSquareOwner(player);
            message = message.substring(0, message.length() - 1);
        }
        //Tjekker om man er landet på et chancefelt
        if (message.charAt(message.length() - 1) == 'S') {
            ChanceCard pulledCard = chanceDeck.pullRandomChanceCard();
            guiLogic.showChanceCard(pulledCard.getDescription());
            pulledCard.applyEffect(player);
            message = message.substring(0, message.length() - 1);
        }

        if (message.equals("GoToJail square"))
            guiLogic.moveToJail(player);

        guiLogic.showMessage(turnLogicTxt.getLine(message));

        if(taxSquare(message)){
            doTax(player, nextLocation);
        }

        if (nextLocation.getOwner() != null) {
            guiLogic.setPlayerBalance(nextLocation.getOwner());
        }
        guiLogic.setPlayerBalance(player);
        if(getExtraturn()){
            hasThrown = false;
        }
    }

    public boolean getExtraturn (){
        boolean ekstraturn = true;
        if (roll1==roll2){
            return ekstraturn;
        }else
            return ekstraturn = false;
    }

    private void rollDice() {
        die.roll();
        roll1 = die.getFaceValue();
        die.roll();
        roll2 = die.getFaceValue();
        rollSum = roll1 + roll2;
    }

    private boolean taxSquare(String message){
        return message.equals(("Tax square"));
    }

    private void doTax(Player p, Square nextLocation){
        if(nextLocation.getIndex() == 4){
            String[] items = new String[]{turnLogicTxt.getLine("pay 4000"),turnLogicTxt.getLine("pay 10")};
            String c = guiLogic.getUserButtonPressed(turnLogicTxt.getLine("tax"),items);
            if(c.equals(turnLogicTxt.getLine("pay 4000"))) {
                int tempTax = 4000;
                nextLocation.setTax(tempTax);
                nextLocation.payTax(p);
            }
            if(c.equals(turnLogicTxt.getLine("pay 10"))){
                int tempTax = (int)Math.round(p.getBalance()*0.1);
                nextLocation.setTax(tempTax);
                nextLocation.payTax(p);
            }
        } else {
            guiLogic.showMessage(turnLogicTxt.getLine("pay 2000"));
            int tempTax = 2000;
            nextLocation.setTax(tempTax);
            nextLocation.payTax(p);
        }
    }

    //Chooses the correect menu items, depending on whether or not the player has already thrown the dice and owns any squares
    private void updateMenuItems() {

        if (hasThrown == false && ownsASquares) {
            menuItems = new String[]{turnLogicTxt.getLine("Throw"), turnLogicTxt.getLine("Properties")};
        } else if (hasThrown == false && ownsASquares == false) {
            menuItems = new String[]{turnLogicTxt.getLine("Throw")};
        } else if (hasThrown && ownsASquares) {
            menuItems = new String[]{turnLogicTxt.getLine("Properties"), turnLogicTxt.getLine("End")};
        } else {
            menuItems = new String[]{turnLogicTxt.getLine("End")};
        }
    }

    private void manageProperties(Player player) {
        //Prompt player to choose a field
        String[] playerSquareNames = board.getPlayerSquareNames(player);
        String selection = guiLogic.getUserSelection(turnLogicTxt.getLine("Choose property"), playerSquareNames);
    
        Square squareToManage = board.getOwnableSquareFromName(selection);

        //todo do so that choosen property shows in the middle
        //Prompt player to choose something to do with that field
        guiLogic.getUserButtonPressed(turnLogicTxt.getLine("Choose option"), turnLogicTxt.getLine("House"), turnLogicTxt.getLine("Pledge"), turnLogicTxt.getLine("Trade"), turnLogicTxt.getLine("Back"));
    }

    private void updateGUI(Player player) {

    }


}
