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
    protected Die die;
    private int roll1, roll2, rollSum = 0;
    private boolean hasThrown = false;
    private String looser;
    MenuLogic menuLogic;
    private int housePrice = 0;

    public TurnLogic (Board board, GUILogic guiLogic, TxtReader turnLogicTxt, TxtReader cardsTxt, Die die){
        this.die = die;
        this.board = board;
        this.guiLogic = guiLogic;
        this.turnLogicTxt = turnLogicTxt;
        this.cardsTxt = cardsTxt;
        chanceDeck = new ChanceDeck(guiLogic, cardsTxt, board);
        menuLogic = new MenuLogic(turnLogicTxt, board, guiLogic);
    }
    
    //todo hmm private..?
    String playRound(PlayerList playerList) {

        looser = "none";

        for (int i = 0; i < playerList.NumberOfPlayers(); i++) {

            Player currentPlayer = playerList.getPlayer(i);

            //If player is in jail
            if (currentPlayer.getJail()) {
                takeJailTurn(currentPlayer);
                if (currentPlayer.getLost()) break;
            } else {
                takeTurn(currentPlayer);
                if (currentPlayer.getLost()) break;
            }
        }

        return looser;
    }

    public void takeTurn(Player player) {

        boolean endTurn = false;
        String choice ="";

        //Start of user menu loop
        while(endTurn == false){

            if (player.getLost()) break;

            //Display start menu
            choice = menuLogic.displayStartMenu(player, hasThrown);

            //Depending on menu choice, program does...
            if (choice.equals(turnLogicTxt.getLine("Throw"))) {

                //Do turn as long as player gets two identical and has not lost
                do{
                    doTurn(player);

                    if(roll1 == roll2 && player.getLost() != true){
                        guiLogic.showMessage(turnLogicTxt.getLine("2 identical"));
                    }

                } while(roll1 == roll2 && player.getLost() != true);

            } else if (choice.equals(turnLogicTxt.getLine("Properties"))) {
                manageProperties(player);

            } else if (choice.equals(turnLogicTxt.getLine("End"))) {
                endTurn = true;
            }
        }
        hasThrown = false;

    }

    private void takeJailTurn(Player currentPlayer){

        //todo tjek tekstfilen

        //Displays the proper jail menu depending on player funds and returns choice
        String choice = menuLogic.displayJailMenu(currentPlayer);

        //If player chooses to buy out
        if(choice.equals(turnLogicTxt.getLine("Jail buy out"))){

            buyPlayerOutOfJail(currentPlayer);
            guiLogic.showMessage(turnLogicTxt.getLine("Out of jail"));
            takeTurn(currentPlayer);

        //If player chooses to throw the dice
        } else {

            //Keep track on number of attempts
            currentPlayer.incrementAttemptsToGetOutOfJail();

            //Roll the dice
            rollDice();
            currentPlayer.setLastRoll((rollSum));
            guiLogic.displayDie(roll1, roll2);

            //If 2 identical
            if(roll1 == roll2){

                //Free the player
                currentPlayer.setJail(false);
                currentPlayer.setAttemptsToGetOutOfJail(0);
                guiLogic.showMessage(turnLogicTxt.getLine("Out of jail"));

                //Do turn according to previous roll
                doJailTurn(currentPlayer);

                //While player gets two identical and has not lost
                while(roll1 == roll2 && currentPlayer.getLost() != true){
                    guiLogic.showMessage(turnLogicTxt.getLine("2 identical"));
                    doTurn(currentPlayer);
                }

                //Display menu
                takeTurn(currentPlayer);

            //If player if out of attempts but can pay
            } else if (currentPlayer.getAttemptsToGetOutOfJail() > 3 && currentPlayer.getBalance() >= 1000){

                //Buy player out and take a turn
                guiLogic.showMessage("Forced to buy out of jail");
                buyPlayerOutOfJail(currentPlayer);
                takeTurn(currentPlayer);

            //If player if out of attempts but cant pay
            } else if (currentPlayer.getAttemptsToGetOutOfJail() > 3){

                //Player has lost
                currentPlayer.setLost(true);
                looser = currentPlayer.getName();
            }
        }
    }

    private void doTurn(Player player){
        hasThrown = true;

        //Roll the dice
        rollDice();
        player.setLastRoll(rollSum);
        guiLogic.displayDie(roll1, roll2);

        //Calculate and move to next location
        Square nextLocation = board.nextLocation(player, rollSum);
        player.setLocation(nextLocation);
        guiLogic.movePiece(player, rollSum);

        //Apply effect of landed on square to player
        doLandedOnTurn(player);
    }

    //Overloading method so it can be used when in jail
    private void doJailTurn(Player player){
        hasThrown = true;

        guiLogic.displayDie(roll1, roll2);
        guiLogic.showChanceCard("");

        //Calculate and move to next location
        Square nextLocation = board.nextLocation(player, rollSum);
        player.setLocation(nextLocation);
        guiLogic.movePiece(player, rollSum);

        //Apply effect of landed on square to player
        doLandedOnTurn(player);
    }

    private void buyPlayerOutOfJail(Player currentPlayer){
        currentPlayer.withdraw(1000);
        guiLogic.setPlayerBalance(currentPlayer);
        currentPlayer.setJail(false);
        currentPlayer.setAttemptsToGetOutOfJail(0);
    }

    public int getOwnerIndex(Square nextLocation) {
        for (int i = 0; i < board.getOwnables().length; i++) {
            if (board.getOwnables()[i].getName() == nextLocation.getName()) {
                return i;
            }
        }
        return -1;
    }

    public void doLandedOnTurn(Player player){

        Square nextLocation = player.getLocation();
        String message = nextLocation.landedOn(player);

        //checker om en spiller har købt en grund. Hvis vedkommende har, så opdaterer GUILogic til at vise den nye ejer af grunden.
        if (message.charAt(message.length() - 1) == 'T') {
            if (player.getBalance() >= player.getLocationPrice((OwnableSquare) nextLocation)) {
                String choice = menuLogic.displayBuyNotBuyMenu();
                if (choice.equals(turnLogicTxt.getLine("buy"))) {
                    guiLogic.setSquareOwner(player);
                    player.attemptToPurchase((OwnableSquare) nextLocation);
                }
            } else {
                guiLogic.showMessage(turnLogicTxt.getLine("Does not have fonds to buy"));
            }
        }

        //Tjekker om man er landet på et chancefelt
        if (message.charAt(message.length() - 1) == 'S') {
            ChanceCard pulledCard = chanceDeck.pullRandomChanceCard();
            guiLogic.showChanceCard(pulledCard.getDescription());
            pulledCard.applyEffect(player);
            if(pulledCard.getType().equalsIgnoreCase("move")){
                doLandedOnTurn(player);
            }
            message = message.substring(0, message.length() - 1);
        }

        if (message.equals("GoToJail square"))
            guiLogic.moveToJail(player);

        if (message.charAt(message.length() - 1) != 'T') {
            guiLogic.showMessage(turnLogicTxt.getLine(message));
        }


        if (taxSquare(message)) {
            doTax(player, nextLocation);
        }

        if (getOwnerIndex(nextLocation) != -1 && message.charAt(message.length() - 1) != 'T') {
            guiLogic.setPlayerBalance(board.getOwnables()[getOwnerIndex(nextLocation)].getOwner());
        } else {
            message = message.substring(0, message.length() - 1);
        }

        guiLogic.setPlayerBalance(player);

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

            String choice = menuLogic.displayTaxMenu();

            if(choice.equals(turnLogicTxt.getLine("pay 4000"))) {
                p.withdraw(4000);
            }
            if(choice.equals(turnLogicTxt.getLine("pay 10"))){
                int tempTax = (int)Math.round(p.getBalance()*0.1);
                p.withdraw(tempTax);
            }
        } else {
            guiLogic.showMessage(turnLogicTxt.getLine("pay 2000"));
            p.withdraw(2000);
        }
        guiLogic.setPlayerBalance(p);
    }

    private void manageProperties(Player player) {

        //Prompt player to choose a field
        String selection = menuLogic.displayPropertyMenu(player);
    
        //Show property information in the middle of board
        OwnableSquare squareToManage = (OwnableSquare) board.getOwnableSquareFromName(selection);
        guiLogic.showChanceCard(squareToManage.getInfo());
        
        //Prompt player to choose something to do with that field
        String choice = menuLogic.displayManagePropertyMenu();
        
        if (choice.equals(turnLogicTxt.getLine("House")))
            housePrice = 0;
            if(board.searchColors(board.getOwnableSquareFromName(selection)) == 0){
                buildHouse(board.getPropertyFromName(selection));
            }
            else{
                guiLogic.showMessage(turnLogicTxt.getLine("attempt to buy"));
            }
            if(player.attemptToPay(housePrice)){
            player.withdraw(housePrice);
            guiLogic.setPlayerBalance(player);
            }
    }

    private void buildHouse(PropertySquare square){
        housePrice = square.addHouse();
        if (housePrice == 0) {
            guiLogic.showMessage(turnLogicTxt.getLine("no more house"));
        }
        int houses = square.getHouseCount();
        Square realSquare = board.getSquareFromName(square.getName());
        guiLogic.updateHouses(realSquare.getIndex(),houses);
    }

    private void updateGUI(Player player) {

    }


}
