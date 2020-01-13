package controllers;

import domain.Board;
import domain.Player;
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

    public String displayStartMenu(Player player, boolean hasThrown){
    
        //Create correct greeting message
        String greeting = turnLogicTxt.getLine("It is") + " " +
                player.getName() + turnLogicTxt.getLine("s") + " " +
                turnLogicTxt.getLine("Choose option");
        
        //Check if player owns any squares
        boolean ownsASquares = board.doesPlayerOwnAnySquares(player);
        
        //Chooses the correct start menu items
        startMenuItems = updateStartMenuItems(hasThrown,ownsASquares);
    
        //Displays the start menu and returns choice
        return guiLogic.getUserButtonPressed(greeting, startMenuItems);
        
    }
    
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
    
    public String displayPropertyMenu(Player player){
    
        //Find properties owned by player
        String[] properties = board.getPlayerSquareNames(player);
        
        //Display properties and return choice
        return guiLogic.getUserSelection(turnLogicTxt.getLine("Choose property"), properties);
    }
    
    public String displayTaxMenu(){
        
        String[] taxMenuItems = new String[]{turnLogicTxt.getLine("pay 4000"), turnLogicTxt.getLine("pay 10")};
        
        //Display tax menu and return choice
        return guiLogic.getUserButtonPressed(turnLogicTxt.getLine("tax"), taxMenuItems);
    }
    
    public String displayBuyNotBuyMenu(){
        
        String[] BuyNotBuy = new String[]{turnLogicTxt.getLine("buy"),turnLogicTxt.getLine("dont buy")};
    
        //Display BuyNotBuy menu and return choice
        return guiLogic.getUserButtonPressed(turnLogicTxt.getLine("buy choice"), BuyNotBuy);
        
    }
    
    public String displayManagePropertyMenu(){
    
        String[] options = new String[]{turnLogicTxt.getLine("House") + turnLogicTxt.getLine("Pledge"), turnLogicTxt.getLine("Trade"), turnLogicTxt.getLine("Back")};
        
        return guiLogic.getUserButtonPressed(turnLogicTxt.getLine("Choose option"), options);
        
    }

}
