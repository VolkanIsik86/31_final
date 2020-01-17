package domain.chanceCards;

import controllers.TurnLogic;
import domain.Board;
import domain.ChanceDeck;
import domain.Player;
import domain.PlayerList;
import domain.chanceCards.EarnCard;
import org.junit.Test;
import services.TxtReader;

import static org.junit.Assert.*;

public class WithDrawBalanceCardTest {
    private Board board;
    Player currentPlayer, OtherPlayers;
    ChanceDeck chanceDeck;
   WithDrawBalanceCard drawBalanceCard;
    PlayerList playerList = new PlayerList(null,null);

    public WithDrawBalanceCardTest() {
        TxtReader cardsTxt = new TxtReader();
        String languagePath = "src/main/java/services/languagefiles/";
        cardsTxt.openFile(languagePath, "chanceCards_da");
        cardsTxt.readLines();
        TxtReader landedOnTxt = new TxtReader();
        landedOnTxt.openFile(languagePath, "turnLogic_da");
        landedOnTxt.readLines();
        TxtReader squareTxt = new TxtReader();
        squareTxt.openFile(languagePath, "squares_da");
        squareTxt.readLines();
        board = new Board(squareTxt, landedOnTxt, new ChanceDeck(cardsTxt, board, playerList));
        chanceDeck = new ChanceDeck(cardsTxt, board, playerList);
    }

    @Test
    public void pullWithDrawBalanceCard() {
        playerList.addPlayer("talha", 5000);
        playerList.addPlayer("max", 5000);
        playerList.addPlayer("lucid", 5000);


        for (int i = 0; i < 1000; i++) {
            int x = 1;
            ChanceCard chanceCard = chanceDeck.pullRandomChanceCard();
        String tempChanceCardType = chanceCard.getType();
        if (tempChanceCardType.equals("WithDraw")) {
            currentPlayer = playerList.getPlayers()[0];
            OtherPlayers = playerList.getPlayers()[x];
            int otherplayerBalance = playerList.getPlayers()[x].getBalance();
            int currentPlayerBalance = playerList.getPlayers()[0].getBalance();
            chanceCard.applyEffect(currentPlayer);
            x++;
        }



        }
    }

}
