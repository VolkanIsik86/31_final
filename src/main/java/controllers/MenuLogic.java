package controllers;

import domain.Board;
import domain.Player;
import services.TxtReader;

public class MenuLogic {
    TxtReader turnLogicTxt;

    public MenuLogic(TxtReader turnLogicTxt) {
        this.turnLogicTxt = turnLogicTxt;
    }

    public String[] updateMenu(boolean hasThrown, boolean ownsASquares) {
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

    public String[] updateMenu(Player player, Board board) {
        String[] menuItems = board.getPlayerSquareNames(player);
        return menuItems;
    }

    public String[] updateMenu(){
        String[] menuItems = {turnLogicTxt.getLine("pay 4000"),turnLogicTxt.getLine("pay 10")};
        return menuItems;
    }
}