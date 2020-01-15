package controllers;

import domain.DieStub;
import domain.PlayerList;

public class GameStub extends Game {
    
    private int[] dieRolls, playerBalances, playerLocations;
    
    public GameStub(int[] dieRolls, int[] playerBalances, int[] playerLocations){
        this.dieRolls = dieRolls;
        this.playerBalances = playerBalances;
        this.playerLocations = playerLocations;
    }
    
    @Override
    protected void initTurnLogic(){
        turnLogic = new TurnLogic(board, guiLogic, turnLogicTxt, cardsTxt, new DieStub(dieRolls),null);
    }
    
    @Override
    protected void initPlayerList(){
        
        //Creates a playerList and adds the players from guiLogic
        playerList = new PlayerList(board.getSquare(0), guiLogic);
        String[] playerNames = guiLogic.getPlayerNames();
        
        
        for (int i = 0; i < playerNames.length; i++) {
    
            //Add player from the gui to playerlist
            playerList.addPlayer(playerNames[i], playerBalances[i]);
    
            //Update playerbalances in the GUI
            guiLogic.setPlayerBalance(playerList.getPlayer(i));
            
            //Move placer from start to specificed location in GUI
            guiLogic.placePlayer(playerList.getPlayer(i), playerLocations[i]);
            
            //Move player in backend
            playerList.getPlayer(i).setLocation(board.getSquare(playerLocations[i]));
            
        }
    }
    
    protected void initGUILogic() {
        
        //Includes the initialization of the GUI itself
        guiLogic = new GUILogic(squaresTxt, guiTxt, STARTBALANCE);
        guiLogic.setDelay(30);
    }
    

}
