package controllers;

import domain.*;
import services.TxtReader;

public class Game {

    protected GUILogic guiLogic;
    protected Board board;
    protected ChanceDeck chanceDeck;
    protected TurnLogic turnLogic;
    protected PlayerList playerList;
    protected final int STARTBALANCE = 30000;

    protected TxtReader turnLogicTxt;
    protected TxtReader squaresTxt;
    protected TxtReader cardsTxt;
    private TxtReader winnerTxt;
    protected TxtReader guiTxt;

    public void playGame() {

        initializeGame();

        //Play game as long as at least two people are alive
        while (playerList.getNumberOfPlayers() > 1) {
            turnLogic.playRound();
        }

        //End game
        announceWinner();
        guiLogic.showMessage(guiTxt.getLine("Close"));
        guiLogic.close();

    }

    protected void announceWinner() {

        String coolwinner =

                "<table width=\"173\" cellspacing=\"11\" bgcolor=\"#000000\"><tr><td align=\"center\">" +
                        "<font color=\"white\" size=\"6\">" + winnerTxt.getLine("1") +
                        "</font>" +
                        "</td></tr>" +
                        "<tr><td align=\"center\">" +
                        "\n" +
                        "<font size=\"5\" color=\"red\">" +
                        winnerTxt.getLine("2") +
                        "</font>" +
                        "</td></tr>" +
                        "<tr><td align=\"center\">" +
                        "\n" +
                        "<font size=\"6\" color=\"yellow\">" +
                        playerList.getPlayer(0).getName() +
                        "</font>" +
                        "</td></tr></table>";
        guiLogic.showChanceCard(coolwinner);
    }


    protected void initializeGame() {


        initLanguage();
        initGUILogic();
        initBoard();
        initPlayerList();
        initTurnLogic();
    }

    //Method to initialize the language, the Language adds either "da" or en" to the filepath.
    private void initLanguage() {

        LanguageLogic languageLogic = new LanguageLogic();

        //Promts user to select language
        String language = languageLogic.selectLanguage();

        //Load txt files
        turnLogicTxt = new TxtReader();
        String languagePath = "src/main/java/services/languagefiles/";
        turnLogicTxt.openFile(languagePath, "turnLogic_" + language);
        turnLogicTxt.readLines();

        squaresTxt = new TxtReader();
        squaresTxt.openFile(languagePath, "squares_" + language);
        squaresTxt.readLines();

        cardsTxt = new TxtReader();
        cardsTxt.openFile(languagePath, "chanceCards_" + language);
        cardsTxt.readLines();

        winnerTxt = new TxtReader();
        winnerTxt.openFile(languagePath, "winner_" + language);
        winnerTxt.readLines();

        guiTxt = new TxtReader();
        guiTxt.openFile(languagePath, "guitext_" + language);
        guiTxt.readLines();

    }

    protected void initGUILogic() {

        //Includes the initialization of the GUI itself
        guiLogic = new GUILogic(squaresTxt, guiTxt, STARTBALANCE);
    }

    protected void initBoard() {
        //Includes the initialization of the chance deck
        board = new Board(squaresTxt, turnLogicTxt);
    }

    //Creates the logic object for running the game
    protected void initTurnLogic() {
        turnLogic = new TurnLogic(board, guiLogic, turnLogicTxt, cardsTxt, new Die(), playerList, new ChanceDeck(cardsTxt, board, playerList));
    }

    protected void initPlayerList() {

        //Creates a playerList and adds the players from guiLogic
        playerList = new PlayerList(board.getSquare(0));
        String[] playerNames = guiLogic.getPlayerNames();

        for (int i = 0; i < playerNames.length; i++) {
            playerList.addPlayer(playerNames[i], STARTBALANCE);
        }
    }

}


