package controllers;

import services.TxtReader;

public class GUILogicStub extends GUILogic {
    
    public GUILogicStub(TxtReader squaresTxt, TxtReader guiTxt, int startbalance){
        super(squaresTxt, guiTxt, startbalance);
    }
    
    @Override
    protected void makeUsers(int startbalance) {
    
        String nrPlayers = gui.getUserSelection(guiTxt.getLine("player numbers"), "1","3", "4", "5", "6");
        int NumberOfPlayers = Integer.parseInt(nrPlayers);
    
        addPlayers(NumberOfPlayers, startbalance);
    }

}
