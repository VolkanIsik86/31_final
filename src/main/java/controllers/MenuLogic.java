package controllers;

import domain.Board;
import domain.Player;
import domain.squares.OwnableSquare;
import services.TxtReader;

public class MenuLogic {

    private TxtReader turnLogicTxt;
    private Board board;
    private GUILogic guiLogic;
    private String[] startMenuItems;

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
        startMenuItems = updateStartMenuItems(hasThrown, ownsASquares);

        //Displays the start menu and returns choice
        return guiLogic.getUserButtonPressed(greeting, startMenuItems);

    }

    //Controls what to show in the GUI, depending on if player has thrown and/or own squares
    public String[] updateStartMenuItems(boolean hasThrown, boolean ownsASquares) {
        String[] menuItems;
        if (hasThrown == false && ownsASquares) {
            menuItems = new String[]{turnLogicTxt.getLine("Throw"), turnLogicTxt.getLine("Properties")};
        } else if (hasThrown == false && ownsASquares == false) {
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
    
    public String displayJailMenu(Player player){
        
        String[] jailMenuItems;
        String greeting;

        //Lets the user choose to either but out of jail, or try roll two identical dice
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
    public String PledgeOwnables(Player player){

        String[] properties = board.getPlayerSquareNames(player);

        return guiLogic.getUserSelection(turnLogicTxt.getLine("pledged properties"), properties);
    }

    /**
     * The menu will be showed when auctioning is invoked.
     * @param player the player who has bidding turn
     * @return String of bidding value or pass if he press pass
     */
    public String auctionMenu(Player player){
        String[] manageAuctionItems = {"byd","pass"};

        if(guiLogic.getUserButtonPressed(player.getName(),manageAuctionItems).equals("pass")){
            return "pass";
        }
        else
            return guiLogic.getUserString("indtast beløb du vil byde");
    }

}
