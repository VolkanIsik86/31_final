package controllers;

import domain.*;
import domain.chanceCards.ChanceCard;
import domain.chanceCards.EarnCard;
import domain.squares.*;
import services.TxtReader;

public class TurnLogic {
    
    protected Board board;
    private GUILogic guiLogic;
    private TxtReader turnLogicTxt;
    protected TxtReader cardsTxt;
    private ChanceDeck chanceDeck;
    private Die die;
    private int roll1, roll2, rollSum = 0;
    private boolean hasThrown = false;
    private String looser;
    private MenuLogic menuLogic;
    private AuctionLogic auctionLogic;
    private PlayerList playerList;


    public TurnLogic (Board board, GUILogic guiLogic, TxtReader turnLogicTxt, TxtReader cardsTxt, Die die, PlayerList playerList, ChanceDeck chanceDeck){
        this.die = die;
        this.board = board;
        this.guiLogic = guiLogic;
        this.turnLogicTxt = turnLogicTxt;
        this.cardsTxt = cardsTxt;
        this.playerList = playerList;
        this.chanceDeck = chanceDeck;
        menuLogic = new MenuLogic(turnLogicTxt, board, guiLogic);
        auctionLogic = new AuctionLogic(playerList, menuLogic, guiLogic);
    }

    //todo hmm private..?
    public void playRound() {
        
        for (int i = 0; i < playerList.NumberOfPlayers(); i++) {

            Player currentPlayer = playerList.getPlayer(i);

            //If player is in jail
            if (currentPlayer.getJail()) {
                takeJailTurn(currentPlayer);
            } else {
                takeTurn(currentPlayer);
            }
            
            //Remove player from game and check if game is over
            if (currentPlayer.getLost()){
                playerList.removePlayer(currentPlayer);
                
                if (playerList.NumberOfPlayers() > 1){
                    i = i -1;
                } else {
                    break;
                }
                
            }
        }
    }

    private void takeTurn(Player player) {

        boolean endTurn = false;
        String choice;

        //Start of user menu loop
        outer:
        while(!endTurn){

            if (player.getLost()) break;
            int throwCounter = 0;
            
            //Start menu loop
            choice = menuLogic.displayStartMenu(player, hasThrown);

            //Depending on menu choice, program does...
            if (choice.equals(turnLogicTxt.getLine("Throw"))) {
                
                //Do a turn - as long as player gets two identical and has not lost. No more than three times
                do{
                    
                    throwCounter++;
                    rollDice();
    
                    //If players has struck two identical three time, put him in jail, otherwise do his turn
                    if(throwCounter < 3 || roll1!=roll2){
                        doTurn(player);
                        
                    } else {
                        putInJail(player);
                        guiLogic.showMessage(turnLogicTxt.getLine("too many identical"));
                        break outer;
                    }
                    
                    if(roll1 == roll2 && player.getLost() != true && player.getJail() != true){
                        guiLogic.showMessage(turnLogicTxt.getLine("2 identical OK to throw"));
                    }
                    
                    //If players has rolled 2 identical and ended up in jail
                    if (roll1 == roll2 && player.getLost() != true && player.getJail()){
                        guiLogic.showMessage(turnLogicTxt.getLine("2 identical"));
                        takeJailTurn(player);
                        break outer;
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
    
    private void withDrawMoneyFromPlayers (int amount, Player currentPlayer) {
        int tempo = 0;
        int totalMoneyFromOthers = -500;
        Player[] restOfPlayers = new Player[playerList.getPlayers().length-1];
        for (int i = 0; i < playerList.getPlayers().length ; i++) {
            if(!(currentPlayer.getName().equals(playerList.getPlayers()[i].getName()))){
                restOfPlayers[tempo] = playerList.getPlayers()[i];
                tempo++;
            }
        }
        for (int i = 0; i < restOfPlayers.length ; i++) {
            if (restOfPlayers[i].attemptToPay(amount)) {
                restOfPlayers[i].withdraw(amount);
                guiLogic.setPlayerBalance(restOfPlayers[i]);
                totalMoneyFromOthers += amount;
            }
        }

        currentPlayer.deposit(totalMoneyFromOthers);

    }

    private void takeJailTurn(Player currentPlayer){

        //todo tjek tekstfilen

        hasThrown = false;

        //Displays the proper jail menu depending on player funds and return choice
        String choice = menuLogic.displayJailMenu(currentPlayer);

        //If player chooses to buy out
        if(choice.equals(turnLogicTxt.getLine("Jail buy out"))){

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
    
    private void attemptEscapeWithDice(Player currentPlayer){
        
        //If 2 identical
        if(roll1 == roll2){
        
            //Free the player
            currentPlayer.setJail(false);
            currentPlayer.setAttemptsToGetOutOfJail(0);
            guiLogic.showMessage(turnLogicTxt.getLine("Out of jail take turn"));

            hasThrown = false;
            takeTurn(currentPlayer);
        
        //If player is out of attempts but can pay
        } else if (currentPlayer.getAttemptsToGetOutOfJail() > 2 && currentPlayer.getBalance() >= 1000){
        
            //Buy player out and take a turn
            guiLogic.showMessage(turnLogicTxt.getLine("Forced to buy out of jail"));
            buyPlayerOutOfJail(currentPlayer);
            guiLogic.showMessage(turnLogicTxt.getLine("Out of jail take turn"));
            takeTurn(currentPlayer);
        
        //If player is out of attempts but cant pay
        } else if (currentPlayer.getAttemptsToGetOutOfJail() > 2){
        
            //Player has lost
            currentPlayer.setLost(true);
            looser = currentPlayer.getName();
            guiLogic.showMessage(turnLogicTxt.getLine("Out of jail throws and lost"));
        
        //If player is just still in jail
        } else {
            guiLogic.showMessage(turnLogicTxt.getLine("Did not come out of jail"));
    
            hasThrown = true;
            takeTurn(currentPlayer);
        }
        
    }

    private void doTurn(Player player){
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

    private void buyPlayerOutOfJail(Player currentPlayer){
        currentPlayer.withdraw(1000);
        guiLogic.setPlayerBalance(currentPlayer);
        currentPlayer.setJail(false);
        currentPlayer.setAttemptsToGetOutOfJail(0);
    }

    public int getOwnerIndex(Square nextLocation) {
        for (int i = 0; i < board.getOwnables().length; i++) {
            if (board.getOwnables()[i].getName().equals(nextLocation.getName())) {
                return i;
            }
        }
        return -1;
    }

    public void doLandedOnTurn(Player player){

        Square nextLocation = player.getLocation();
        String message = nextLocation.landedOn(player);

        /**
         * Shows menu to buy or not to buy if player lands on unowned square
         * if players chose is not to buy auctioning method will be invoked.
         */
        if (message.charAt(message.length() - 1) == 'T') {
            if (player.getBalance() >= player.getLocationPrice((OwnableSquare) nextLocation)) {
                String choice = menuLogic.displayBuyNotBuyMenu();
                if (choice.equals(turnLogicTxt.getLine("buy"))) {
                    guiLogic.setSquareOwner(player);
                    player.attemptToPurchase((OwnableSquare) nextLocation);
                }
                else if (choice.equals(turnLogicTxt.getLine("dont buy"))){
                    auctionLogic.auctioning(((OwnableSquare) nextLocation),player);
                    //auctioning(((OwnableSquare) nextLocation),playerList,player );
                }
            } else {
                guiLogic.showMessage(turnLogicTxt.getLine("Does not have fonds to buy"));
            }
        }

        //Tjekker om man er landet på et chancefelt
        if (message.charAt(message.length() - 1) == 'S') {
            ChanceCard pulledCard = chanceDeck.pullRandomChanceCard();
            guiLogic.showChanceCard(pulledCard.getDescription());
            int tempValue = pulledCard.applyEffect(player);
            String tempCard = pulledCard.getType();
            if(tempCard.equalsIgnoreCase("move")){
                guiLogic.movePiece(player, tempValue);
                doLandedOnTurn(player);
            }
            if(tempCard.equalsIgnoreCase("PayHouseCard")||(tempCard.equalsIgnoreCase("pay"))&& !player.attemptToPay(tempValue)){
                guiLogic.showMessage(cardsTxt.getLine("Does not have fonds to pay"));
            }
            if (tempCard.equalsIgnoreCase("Earn")){
                if (((EarnCard)pulledCard).getAmount() == 500) {
                    guiLogic.showMessage("du får 500 fra alle andre");
                    withDrawMoneyFromPlayers(500, player);
                }
            }


            message = message.substring(0, message.length() - 1);
        }

        if (message.equals("GoToJail square"))
            guiLogic.moveToJail(player);

        if (message.charAt(message.length() - 1) == 'f') {
            guiLogic.showMessage(turnLogicTxt.getLine(message)+ ": " + ((OwnableSquare) nextLocation).getRent() + " kr.");
        }

        if (message.charAt(message.length() - 1) == 'o') {
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
    
    private void putInJail(Player player){
        player.setLocation(board.getJail());
        player.setJail(true);
        guiLogic.moveToJail(player);
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
                int tempTax = (int)Math.round(board.getPlayerValue(p)*0.1);
                System.out.println(tempTax);
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
        OwnableSquare squareToManage = board.getOwnableSquareFromName(selection);
        guiLogic.showChanceCard(squareToManage.getInfo());

        String choice = "START";

            //Property menu loop
            while (!choice.equals("END")){

                //Prompt player to choose something to do with that field
                choice = menuLogic.displayManagePropertyMenu(squareToManage);

                if (choice.equals(turnLogicTxt.getLine("House"))){

                    PropertySquare propertyToManage = ((PropertySquare) squareToManage);

                    boolean playerOwnAllColors = board.searchColors(propertyToManage) == 0;
                    boolean playerHasEnoughMoney = player.getBalance() >= propertyToManage.getHOUSE_PRICE();
                    boolean numberOfHousesIsLessThan5 = propertyToManage.getHouseCount() < 5;

                    if (playerOwnAllColors){
                        if (numberOfHousesIsLessThan5){
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

                } else if (choice.equals(turnLogicTxt.getLine("Pledge"))){
                    guiLogic.showMessage(turnLogicTxt.getLine("Not yet implemented"));

                } else if (choice.equals(turnLogicTxt.getLine("Trade"))){
                    guiLogic.showMessage(turnLogicTxt.getLine("Not yet implemented"));

                } else if (choice.equals(turnLogicTxt.getLine("Back"))) {
                    choice = "END";
                }
            }
    }

    private void buildHouse(PropertySquare propertyToManage, Player player){
        player.withdraw(propertyToManage.getHOUSE_PRICE());
        propertyToManage.addHouse();
        guiLogic.setPlayerBalance(player);
        guiLogic.updateHouses(propertyToManage.getIndex(), propertyToManage.getHouseCount());

        if(propertyToManage.getHouseCount() == 5){
            guiLogic.showMessage(turnLogicTxt.getLine("Hotel has been build"));
        } else {
            guiLogic.showMessage(turnLogicTxt.getLine("House has been build"));
        }
    }
    private void updateGUI(Player player) {

    }
    
}
