package controllers;

import domain.Board;
import domain.Player;
import domain.PlayerList;
import services.TxtReader;

public class Game {

    private GUILogic guiLogic = new GUILogic();
    private final Board board = new Board();
    private final TurnLogic turnLogic = new TurnLogic();
    private PlayerList playerList;

    private String looser = "null";
    private TxtReader turnLogicTxt;
    private TxtReader squaresTxt;
    private TxtReader cardsTxt;
    private TxtReader winnerTxt;
    private TxtReader guiTxt;

    public void playGame() {

        initializeGame();

        while (true){
            looser = turnLogic.playRound(playerList);
            if (looser != null){
                announceWinner();
                guiLogic.showMessage(guiTxt.getLine("Close"));
                guiLogic.close();
                break;
            }

        }

      //  do {
      //      turnLogic.playRound(playerList);
      //  }
      //  while (looser.equals(null));
      //  announceWinner();

      //  guiLogic.showMessage(guiTxt.getLine("Close"));
      //  guiLogic.close();

    }

    private void announceWinner() {
        //If it's a draw
        if (playerList.getWinner() == null) {
            String coolWinner =

                    "<table width=\"173\" cellspacing=\"17\" bgcolor=\"#000000\"><tr><td>\n</td></tr><tr><td align=\"center\">" +
                            "<font color=\"white\" size=\"6" +
                            "" +
                            "\">" +
                            winnerTxt.getLine("3") +
                            "</font>" +
                            "</td></tr>" +
                            "<tr><td>\n</td></tr>" +
                            "</table>";

            guiLogic.getGui().displayChanceCard(coolWinner);
        }

        //Else if not a draw
        else {
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
                            playerList.getWinner().getName() +
                            "</font>" +
                            "</td></tr></table>";

            guiLogic.getGui().displayChanceCard(coolwinner);
        }
    }



    private void initializeGame() {

        looser = "null";

        initLanguage();
        initGUILogic();
        initBoard();
        initTurnLogic();
        initPlayerList();
    }

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

    private void initGUILogic() {

        //Includes the initialization of the GUI itself
        guiLogic = new GUILogic();
        guiLogic.init(squaresTxt, guiTxt);
    }

    private void initBoard() {

        //Includes the initialization of the chance deck
        board.makeBoard(squaresTxt, turnLogicTxt, cardsTxt, guiLogic);
   }
   
   private void initTurnLogic(){
       turnLogic.init(board, guiLogic, turnLogicTxt, cardsTxt);
   }
   
   private void initPlayerList(){
       
       //Creates a playerList and adds the players from guiLogic
       playerList = new PlayerList(board.getSquare(0), guiLogic);
       String[] playerNames = guiLogic.getPlayerNames();

       for (int i = 0; i < playerNames.length; i++) {
           playerList.addPlayer(playerNames[i], guiLogic.getSTARTBALANCE());
       }
       
   }
}


