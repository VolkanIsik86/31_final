package controllers;

import domain.*;
import domain.chanceCards.ChanceCard;
import domain.chanceCards.EarnCard;
import domain.squares.*;
import services.TxtReader;

public class TurnLogic {

    protected final Board board;
    private final GUILogic guiLogic;
    private final TxtReader turnLogicTxt;
    protected final TxtReader cardsTxt;
    private final ChanceDeck chanceDeck;
    private final Die die;
    private int roll1, roll2, rollSum = 0;
    private boolean hasThrown = false;
    private final MenuLogic menuLogic;
    private final AuctionLogic auctionLogic;
    private final PlayerList playerList;


    public TurnLogic(Board board, GUILogic guiLogic, TxtReader turnLogicTxt, TxtReader cardsTxt, Die die, PlayerList playerList, ChanceDeck chanceDeck) {
        this.die = die;
        this.board = board;
        this.guiLogic = guiLogic;
        this.turnLogicTxt = turnLogicTxt;
        this.cardsTxt = cardsTxt;
        this.playerList = playerList;
        this.chanceDeck = chanceDeck;
        menuLogic = new MenuLogic(turnLogicTxt, board, guiLogic);
        auctionLogic = new AuctionLogic(playerList, menuLogic, guiLogic, turnLogicTxt);
    }

    /**
     * Runs the game until one player remains.
     */
    public void playRound() {

        for (int i = 0; i < playerList.getNumberOfPlayers(); i++) {

            Player currentPlayer = playerList.getPlayer(i);

            //If player is in jail
            if (currentPlayer.getJail()) {
                takeJailTurn(currentPlayer);
            } else {
                takeTurn(currentPlayer);
            }

            //Remove player from game and check if game is over
            if (currentPlayer.getLost()) {

                if (playerList.getNumberOfPlayers() > 2) {
                    i = i - 1;
                    guiLogic.deletePlayer(currentPlayer);
                    
                    if(board.getPlayerSquares(currentPlayer).length > 0){
                        auctionPlayerProperties(currentPlayer);
                    }
                    
                    playerList.removePlayer(currentPlayer);

                } else {
                    playerList.removePlayer(currentPlayer);
                    guiLogic.showMessage(turnLogicTxt.getLine("The game ends"));
                    break;
                }
            }
        }
    }

    /**
     * Shows auction start menu
     *
     * @param player
     */
    @SuppressWarnings("JavaDoc")
    private void auctionPlayerProperties(Player player) {

        guiLogic.showMessage(turnLogicTxt.getLine("All player properties auction"));

        //Find properties to auktion
        Square[] squaresToAuction = board.getPlayerSquares(player);

        //Auction them
        for (int i = 0; i < squaresToAuction.length; i++) {
            auctionLogic.auctioning(((OwnableSquare) squaresToAuction[i]), player);
        }
    }

    /**
     * take turn for player
     *
     * @param player
     */
    @SuppressWarnings("JavaDoc")
    private void takeTurn(Player player) {

        boolean endTurn = false;
        String choice;

        //Start of menu loop
        outer1:
        while (!endTurn) {

            if (player.getLost()) break;
            int throwCounter = 0;

            //Display user menu choices
            choice = menuLogic.displayStartMenu(player, hasThrown);

            //Depending on menu choice, program does...
            if (choice.equals(turnLogicTxt.getLine("Throw"))) {

                //Do a turn - as long as player gets two identical and has not lost. No more than three times
                outer2:
                do {

                    throwCounter++;
                    rollDice();

                    //If players has struck two identical three time, put him in jail, otherwise do his turn
                    if (throwCounter < 3 || roll1 != roll2) {
                        doTurn(player);

                    } else {
                        player.setLastRoll(rollSum);
                        guiLogic.displayDie(roll1, roll2);
                        guiLogic.showMessage(turnLogicTxt.getLine("too many identical"));
                        putInJail(player);
                        break outer2;
                    }

                    if (roll1 == roll2 && !player.getLost() && !player.getJail()) {
                        guiLogic.showMessage(turnLogicTxt.getLine("2 identical OK to throw"));
                    }

                    //If players has rolled 2 identical and ended up in jail
                    if (roll1 == roll2 && !player.getLost() && player.getJail()) {
                        guiLogic.showMessage(turnLogicTxt.getLine("2 identical"));
                        takeJailTurn(player);
                        break outer1;
                    }

                } while (roll1 == roll2 && !player.getLost());

            } else if (choice.equals(turnLogicTxt.getLine("Properties"))) {
                manageProperties(player);

            } else if (choice.equals(turnLogicTxt.getLine("End"))) {
                endTurn = true;
            }
        }

        hasThrown = false;

    }
    /**
     * Logic for turn if player is jailed
     *
     * @param currentPlayer
     */
    @SuppressWarnings("JavaDoc")
    private void takeJailTurn(Player currentPlayer) {

        //todo tjek tekstfilen

        hasThrown = false;

        //Displays the proper jail menu depending on player funds and return choice
        String choice = menuLogic.displayJailMenu(currentPlayer);

        //If player chooses to buy out
        if (choice.equals(turnLogicTxt.getLine("Jail buy out"))) {

            buyPlayerOutOfJail(currentPlayer);
            guiLogic.showMessage(turnLogicTxt.getLine("Out of jail take turn"));
            takeTurn(currentPlayer);

            //If player chooses to throw the dice
        } else {

            //Keep track on number of attempts
            currentPlayer.incrementAttemptsToGetOutOfJail();

            //Roll the dice
            rollDice();
            currentPlayer.setLastRoll((rollSum));
            guiLogic.displayDie(roll1, roll2);

            attemptEscapeWithDice(currentPlayer);

        }
    }

    /**
     * Logic for getting out of jail with dice rolls
     *
     * @param currentPlayer
     */
    @SuppressWarnings("JavaDoc")
    private void attemptEscapeWithDice(Player currentPlayer) {

        //If 2 identical
        if (roll1 == roll2) {

            //Free the player
            currentPlayer.setJail(false);
            currentPlayer.setAttemptsToGetOutOfJail(0);
            guiLogic.showMessage(turnLogicTxt.getLine("Out of jail take turn"));

            hasThrown = false;
            takeTurn(currentPlayer);

            //If player is out of attempts but can pay
        } else if (currentPlayer.getAttemptsToGetOutOfJail() > 2 && currentPlayer.getBalance() >= 1000) {

            //Buy player out and take a turn
            guiLogic.showMessage(turnLogicTxt.getLine("Forced to buy out of jail"));
            buyPlayerOutOfJail(currentPlayer);
            guiLogic.showMessage(turnLogicTxt.getLine("Out of jail take turn"));
            takeTurn(currentPlayer);

            //If player is out of attempts but cant pay
        } else if (currentPlayer.getAttemptsToGetOutOfJail() > 2) {

            //Player has lost
            currentPlayer.setLost(true);
            guiLogic.showMessage(turnLogicTxt.getLine("Out of jail throws and lost"));

            //If player is just still in jail
        } else {
            guiLogic.showMessage(turnLogicTxt.getLine("Did not come out of jail"));

            hasThrown = true;
            takeTurn(currentPlayer);
        }

    }


    /**
     * Does turn for player
     * Shows dice on GUI and moves piece
     * @param player
     */
    @SuppressWarnings("JavaDoc")
    private void doTurn(Player player) {
        hasThrown = true;

        player.setLastRoll(rollSum);
        guiLogic.displayDie(roll1, roll2);

        //Calculate and move to next location
        Square nextLocation = board.nextLocation(player, rollSum);
        player.setLocation(nextLocation);
        guiLogic.movePiece(player, rollSum);

        //Apply effect of landed on square to player
        doLandedOnTurn(player);
    }

    /**
     * If player wants to buy themselves out of jail
     *
     * @param currentPlayer
     */
    @SuppressWarnings("JavaDoc")
    private void buyPlayerOutOfJail(Player currentPlayer) {
        currentPlayer.withdraw(1000);
        guiLogic.setPlayerBalance(currentPlayer);
        currentPlayer.setJail(false);
        currentPlayer.setAttemptsToGetOutOfJail(0);
    }

    /**
     * Logic for when you land on a square.
     *
     * @param player
     */
    @SuppressWarnings("JavaDoc")
    public void doLandedOnTurn(Player player) {
    
        Square nextLocation = player.getLocation();
        String message = nextLocation.landedOn(player);
        char lastCharofMessage = message.charAt(message.length()-1);

        switch (lastCharofMessage) {
            /*
             * Shows menu to buy or not to buy if player lands on unowned square
             * if players chose is not to buy auctioning method will be invoked.
             */
            case 'N':
                doUnownedProperty(player, nextLocation, message);
                break;
                //Tjekker om man er landet på et chancefelt
            case 'S':
                doChanceSquare(player, message);
                break;
            // J for moveToJail (confirms that player has landed on jailsquare)
            case 'J':
                doMoveToJail(player, message);
                break;
            // j for owner in Jail (confirms that owner of ownablesquare is jailed)
            case 'j':
                guiLogic.showMessage(turnLogicTxt.getLine(message));
                break;
            // O for Owned by another (confirms that square is owned by another)
            case 'O':
                doOwenedProperty(player, nextLocation, message);
                break;
            // o for owned by yourself (confirms that square is owned by yourself)
            case 'o':
                guiLogic.showMessage(turnLogicTxt.getLine(message));
                break;
            // R for player hsa landed on regular square
            case 'R':
                guiLogic.showMessage(turnLogicTxt.getLine(message));
                break;
            // T for player has landed on Tax square
            case 'T':
                guiLogic.showMessage(turnLogicTxt.getLine(message));
                doTax(player, nextLocation);
                break;
        }
        updateBalances();
    }
    
    private void doOwenedProperty(Player player, Square nextLocation, String message){
        OwnableSquare location = ((OwnableSquare) nextLocation);
        location.updateRent(player.getLastRoll());
    
        location.updateRent(player.getLastRoll());
        guiLogic.showMessage(turnLogicTxt.getLine(message) + ": " + ((OwnableSquare) nextLocation).getRent() + " kr.");
        
        //If player has the requested fonds
        if (player.getBalance() >= location.getRent()) {
            location.payRent(player);
            location.earnRent();
        }
    
        //If player doesn't have the requested fonds
        else {
            guiLogic.showMessage(turnLogicTxt.getLine("Does not have fonds for rent"));
            player.setLost(true);
            player.setBalance(0);
        }
    }
    
    private void doMoveToJail(Player player, String message){
        player.setLocation(board.getJail());
        player.setJail(true);
        guiLogic.moveToJail(player);
        guiLogic.updatePlayerLocation(player);
        guiLogic.showMessage(turnLogicTxt.getLine(message));
    }
    
    /**
     * Logic for unowned property
     *
     * @param player current player
     * @param nextLocation the location we want to check
     * @param message the location message from landedOn()
     */
    @SuppressWarnings("JavaDoc")
    private void doUnownedProperty(Player player, Square nextLocation, String message){
        guiLogic.showMessage(turnLogicTxt.getLine(message));
        if (player.getBalance() >= ((OwnableSquare)nextLocation).getPrice()) {
            String choice = menuLogic.displayBuyNotBuyMenu();
            if (choice.equals(turnLogicTxt.getLine("buy"))) {
                guiLogic.setSquareOwner(player);
                player.attemptToPurchase((OwnableSquare) nextLocation);
            } else if (choice.equals(turnLogicTxt.getLine("dont buy"))) {
                auctionLogic.auctioning(((OwnableSquare) nextLocation), player);
            }
        } else {
            guiLogic.showMessage(turnLogicTxt.getLine("Does not have fonds to buy square"));
            auctionLogic.auctioning(((OwnableSquare) nextLocation), player);
        }
    }
    /**
     * Logic for landing on a chance square
     *
     * @param player current player
     * @param message the location message from landedOn()
     */
    @SuppressWarnings("JavaDoc")
    private void doChanceSquare(Player player, String message){
        guiLogic.showMessage(turnLogicTxt.getLine("Chance square C"));
        //Pulls a random card, shows it on the gui and applies the effect on the player
        ChanceCard pulledCard = chanceDeck.pullRandomChanceCard();
        guiLogic.showChanceCard(pulledCard.getDescription());
        guiLogic.showMessage(turnLogicTxt.getLine(message));
        guiLogic.showChanceCard("");
        int tempValue = pulledCard.applyEffect(player);
        String tempCard = pulledCard.getType();
        
        //Different types of chance cards
        if (tempCard.equalsIgnoreCase("move")) {
            Square nextLocation = board.nextLocation(player, tempValue);
            player.setLocation(nextLocation);
            guiLogic.movePiece(player, tempValue);
            nextLocation.landedOn(player);
            doLandedOnTurn(player);
            
        }
        if (tempCard.equalsIgnoreCase("PayHouseCard") && !player.attemptToPay(tempValue) || tempCard.equalsIgnoreCase("pay") && !player.attemptToPay(tempValue)) {
            guiLogic.showMessage(cardsTxt.getLine("Does not have fonds to pay"));
        }
        
        if(tempCard.equalsIgnoreCase("MoveToShipyardCard")){

            if(player.getLocation().getIndex() > 35 && tempValue < 35){
                guiLogic.passedStart(player);
            }
            player.setLocation(board.getSquare(tempValue));
            guiLogic.updatePlayerLocation(player);
            doLandedOnTurn(player);
            }
        }

    /**
     * Updates all players balance on the GUI
     *
     */
    private void updateBalances(){
        for (int i = 0; i < playerList.getPlayers().length ; i++) {
            guiLogic.setPlayerBalance(playerList.getPlayer(i));
        }
    }

    /**
     * Sets jail location and status for current player
     *
     * @param player current player
     */
    private void putInJail(Player player) {
        player.setLocation(board.getJail());
        player.setJail(true);
        guiLogic.moveToJail(player);
    }

    /**
     * Rolls the dice
     */
    private void rollDice() {
        die.roll();
        roll1 = die.getFaceValue();
        die.roll();
        roll2 = die.getFaceValue();
        rollSum = roll1 + roll2;
    }

    /**
     * Logic for paying tax depending on choice from player and square
     *
     * @param p current player
     * @param nextLocation square player is on
     */
    private void doTax(Player p, Square nextLocation) {
        if (nextLocation.getIndex() == 4) {

            String choice = menuLogic.displayTaxMenu();

            if (choice.equals(turnLogicTxt.getLine("pay 4000"))) {
                p.withdraw(4000);
            }
            if (choice.equals(turnLogicTxt.getLine("pay 10"))) {
                int tempTax = (int) Math.round(board.getPlayerValue(p) * 0.1);
                p.withdraw(tempTax);
            }
        } else {
            guiLogic.showMessage(turnLogicTxt.getLine("pay 2000"));
            p.withdraw(2000);
        }
        guiLogic.setPlayerBalance(p);
    }


    /**
     * Logic for managing properties owned by player
     * @param player current player
     */
    private void manageProperties(Player player) {

        //Prompt player to choose a field
        String selection = menuLogic.displayPropertyMenu(player);

        //Show property information in the middle of board
        OwnableSquare squareToManage = board.getOwnableSquareFromName(selection);
        guiLogic.showChanceCard(squareToManage.getInfo());

        String choice = "START";

        //Property menu loop
        while (!choice.equals("END")) {

            //Prompt player to choose something to do with that field
            choice = menuLogic.displayManagePropertyMenu(squareToManage);

            if (choice.equals(turnLogicTxt.getLine("House"))) {

                PropertySquare propertyToManage = ((PropertySquare) squareToManage);

                boolean playerOwnAllColors = board.searchColors(propertyToManage) == 0;
                boolean playerHasEnoughMoney = player.getBalance() >= propertyToManage.getHOUSE_PRICE();
                boolean numberOfHousesIsLessThan5 = propertyToManage.getHouseCount() < 5;

                if (playerOwnAllColors) {
                    if (numberOfHousesIsLessThan5) {
                        if (playerHasEnoughMoney) {
                            buildHouse(propertyToManage, player);
                        } else {
                            guiLogic.showMessage(turnLogicTxt.getLine("Player does not have enough money"));
                        }
                    } else {
                        guiLogic.showMessage(turnLogicTxt.getLine("Property has too many houses"));
                    }
                } else {
                    guiLogic.showMessage(turnLogicTxt.getLine("Player does not own all colors"));
                }

            } else if (choice.equals(turnLogicTxt.getLine("Pledge"))) {
                guiLogic.showMessage(turnLogicTxt.getLine("Not yet implemented"));

            } else if (choice.equals(turnLogicTxt.getLine("Trade"))) {
                guiLogic.showMessage(turnLogicTxt.getLine("Not yet implemented"));

            } else if (choice.equals(turnLogicTxt.getLine("Back"))) {
                choice = "END";
            }
        }
    }

    /**
     * Logic for building houses/hotels
     * @param propertyToManage The location we want to build a house/hotel on
     * @param player current player
     */
    private void buildHouse(PropertySquare propertyToManage, Player player) {
        player.withdraw(propertyToManage.getHOUSE_PRICE());
        propertyToManage.addHouse();
        guiLogic.setPlayerBalance(player);
        guiLogic.updateHouses(propertyToManage.getIndex(), propertyToManage.getHouseCount());

        if (propertyToManage.getHouseCount() == 5) {
            guiLogic.showMessage(turnLogicTxt.getLine("Hotel has been build"));
        } else {
            guiLogic.showMessage(turnLogicTxt.getLine("House has been build"));
        }
    }

}
