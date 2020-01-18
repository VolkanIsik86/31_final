package controllers;

import domain.DieStub;
import domain.PlayerList;
import domain.ChanceDeckStub;

public class GameDriver extends Game {

    private final int[] dieRolls;
    private final int[] playerBalances;
    private final int[] playerLocations;
    private final int[] chanceCardSequence;

    public GameDriver(int[] dieRolls, int[] playerBalances, int[] playerLocations, int[] chanceCardSequence) {
        this.dieRolls = dieRolls;
        this.playerBalances = playerBalances;
        this.playerLocations = playerLocations;
        this.chanceCardSequence = chanceCardSequence;
    }
    
    @Override
    public void playGame(){
        super.initializeGame();
    
        //If number of players are greater than one play game as normal
        if(playerList.getNumberOfPlayers() > 1){
            //Play game as long as at least two people are alive
            while (playerList.getNumberOfPlayers() > 1) {
                turnLogic.playRound();
            }
    
            super.announceWinner();
            
        //Else change while loop condition (for testing purposes)
        } else {
            //Play game as long as at least 1 player is alive
            while (playerList.getNumberOfPlayers() > 0) {
                turnLogic.playRound();
            }
        }
        
        //End game
        guiLogic.showMessage(guiTxt.getLine("Close"));
        guiLogic.close();
    }

    @Override
    protected void initGUILogic() {

        //Includes the initialization of the GUI itself
        guiLogic = new GUILogicStub(squaresTxt, guiTxt, STARTBALANCE);
        guiLogic.setDelay(30);
    }

    @Override
    protected void initPlayerList() {

        //Creates a playerList and adds the players from guiLogic
        playerList = new PlayerList(board.getSquare(0));
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

    @Override
    protected void initTurnLogic() {
        turnLogic = new TurnLogic(board, guiLogic, turnLogicTxt, cardsTxt, new DieStub(dieRolls), playerList, new ChanceDeckStub(cardsTxt, board, chanceCardSequence,playerList));
    }

}
