package controllers;

import domain.Board;
import domain.Player;
import domain.squares.OwnableSquare;
import services.TxtReader;

public class MenuLogic {

    private final TxtReader turnLogicTxt;
    private final Board board;
    private final GUILogic guiLogic;

    public MenuLogic(TxtReader turnLogicTxt, Board board, GUILogic guiLogic) {
        this.turnLogicTxt = turnLogicTxt;
        this.board = board;
        this.guiLogic = guiLogic;
    }

    public String displayStartMenu(Player player, boolean hasThrown) {

        //Create correct greeting message
        String greeting = turnLogicTxt.getLine("It is") + " " +
                player.getName() + turnLogicTxt.getLine("s") + " " +
                turnLogicTxt.getLine("Choose option");

        //Check if player owns any squares
        boolean ownsASquares = board.doesPlayerOwnAnySquares(player);

        //Chooses the correct start menu items
        String[] startMenuItems = updateStartMenuItems(hasThrown, ownsASquares);

        //Displays the start menu and returns choice
        return guiLogic.getUserButtonPressed(greeting, startMenuItems);

    }

    //Controls what to show in the GUI, depending on if player has thrown and/or own squares
    public String[] updateStartMenuItems(boolean hasThrown, boolean ownsASquares) {
        String[] menuItems;
        if (!hasThrown && ownsASquares) {
            menuItems = new String[]{turnLogicTxt.getLine("Throw"), turnLogicTxt.getLine("Properties")};
        } else if (!hasThrown && !ownsASquares) {
            menuItems = new String[]{turnLogicTxt.getLine("Throw")};
        } else if (hasThrown && ownsASquares) {
            menuItems = new String[]{turnLogicTxt.getLine("Properties"), turnLogicTxt.getLine("End")};
        } else {
            menuItems = new String[]{turnLogicTxt.getLine("End")};
        }
        return menuItems;
    }

    public String displayPropertyMenu(Player player) {

        //Find properties owned by player
        String[] properties = board.getPlayerSquareNames(player);

        //Display properties and return choice
        return guiLogic.getUserSelection(turnLogicTxt.getLine("Choose property"), properties);
    }

    public String displayTaxMenu() {

        String[] taxMenuItems = new String[]{turnLogicTxt.getLine("pay 4000"), turnLogicTxt.getLine("pay 10")};

        //Display tax menu and return choice
        return guiLogic.getUserButtonPressed(turnLogicTxt.getLine("tax"), taxMenuItems);
    }

    public String displayBuyNotBuyMenu() {

        String[] BuyNotBuyOptions = new String[]{turnLogicTxt.getLine("buy"), turnLogicTxt.getLine("dont buy")};

        //Display BuyNotBuy menu and return choice
        return guiLogic.getUserButtonPressed(turnLogicTxt.getLine("buy choice"), BuyNotBuyOptions);

    }

    //Displays the management menu, wheree you can build, pledge, auction or end turn
    public String displayManagePropertyMenu(OwnableSquare squareToManage) {
        String[] managePropertyMenuItems;

        if (squareToManage.isRealEstate()) {
            managePropertyMenuItems = new String[]{turnLogicTxt.getLine("House"), turnLogicTxt.getLine("Pledge"), turnLogicTxt.getLine("Trade"), turnLogicTxt.getLine("Back")};
        } else {
            managePropertyMenuItems = new String[]{turnLogicTxt.getLine("Pledge"), turnLogicTxt.getLine("Trade"), turnLogicTxt.getLine("Back")};
        }
        //Display manage properies menu and return choice
        return guiLogic.getUserButtonPressed(turnLogicTxt.getLine("Choose option"), managePropertyMenuItems);
    }

    public String displayJailMenu(Player player) {

        String[] jailMenuItems;
        String greeting;

        //Lets the user choose to either buy out of jail or try to roll two identical dice
        if (player.getBalance() >= 1000) {
            greeting = turnLogicTxt.getLine("It is") + " " +
                    player.getName() + turnLogicTxt.getLine("s") + " " +
                    turnLogicTxt.getLine("In jail 2 options");
            jailMenuItems = new String[]{turnLogicTxt.getLine("Jail buy out"), turnLogicTxt.getLine("Jail roll dice")};

        //If player has a balance under 1000, they can only roll the dice
        } else {
            greeting = turnLogicTxt.getLine("It is") + " " +
                    player.getName() + turnLogicTxt.getLine("s") + " " +
                    turnLogicTxt.getLine("In jail 1 option");
            jailMenuItems = new String[]{turnLogicTxt.getLine("Jail roll dice")};
        }

        //Display jail menu and return choice
        return guiLogic.getUserButtonPressed(greeting, jailMenuItems);
    }


    /**
     * Shows auction start menu
     *
     * @param ownableSquare the square that will be auctioned.
     */
    public void auctionStartMenu(OwnableSquare ownableSquare) {
        guiLogic.showMessage(turnLogicTxt.getLine("auction") + " " + ownableSquare.getName());
    }

    /**
     * The menu will be showed when auctioning is invoked.
     *
     * @param player        the player who has bidding turn
     * @param highestbid    String of bidding value with the highest vid
     * @param ownableSquare Gets the square that is put up for auction
     * @return userInput return the highest bid from a user
     */
    public String auctionMenu(Player player, int highestbid, OwnableSquare ownableSquare) {
    
        String[] manageAuctionItems;
        String comment = "";
        
        //Choose correct menu items
        if (player.getBalance() >= highestbid + 100){
            manageAuctionItems = new String[]{turnLogicTxt.getLine("byd"), turnLogicTxt.getLine("pass")};
        } else {
            manageAuctionItems = new String[]{turnLogicTxt.getLine("pass")};
            comment = turnLogicTxt.getLine("Cant afford to bid");
        }
        
        String userInput = "";
        int currentBid = -1;
        
        String choice = guiLogic.getUserButtonPressed(ownableSquare.getName() + ", " + player.getName() + turnLogicTxt.getLine("s") + " " + turnLogicTxt.getLine("Highest bid") + " " + highestbid + " kr." +"\n" +  comment, manageAuctionItems);
        
        // shows a menu of choice that user can choose from (pass or bid)
        if (choice.equals(turnLogicTxt.getLine("pass"))) {
            return "pass";
        } else
            
            // user input has to be higher than highestbid
            while (currentBid < (highestbid + 100)) {
                currentBid = auctionInput(highestbid , player);
            }
            
        userInput = userInput + currentBid;
        return userInput;
    }

    /**
     * Makes sure that user input consists of numbers.
     *
     * @param highestbid used to show massage at gui*
     * @return returns user input in integer
     */
    private int auctionInput(int highestbid ,Player player) {
        int temp = 0;
        try {
            temp = Integer.parseInt(guiLogic.getUserString(turnLogicTxt.getLine("minimum") + " " + (highestbid + 100) + " kr."));
        } catch (NumberFormatException e) {
            guiLogic.showMessage(turnLogicTxt.getLine("onlynumbers"));
            //recursive function that repeats until user has the right input
            temp = auctionInput(highestbid, player);
        }
        // shows a message to player that he bidded over his balance
        if (temp > player.getBalance()){
            guiLogic.showMessage(turnLogicTxt.getLine("toohigh"));
            temp = auctionInput(highestbid, player);
        }
        return temp;
    }
}
