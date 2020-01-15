package domain.chanceCards;


import controllers.TurnLogic;
import domain.*;
import domain.squares.ChanceSquare;
import domain.squares.PropertySquare;
import domain.squares.Square;
import org.junit.Test;
import services.TxtReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ChanceCardTest {
    private Board board;
    Player testPlayer;
    ChanceDeck chanceDeck;
    EarnCard earnCard;
    TurnLogic turnLogic;
    PlayerList playerList;
    public ChanceCardTest() {
        TxtReader cardsTxt = new TxtReader();
        String languagePath = "src/main/java/services/languagefiles/";
        cardsTxt.openFile(languagePath, "chanceCards_da");
        cardsTxt.readLines();
        TxtReader landedOnTxt = new TxtReader();
        landedOnTxt.openFile(languagePath,"turnLogic_da");
        landedOnTxt.readLines();
        TxtReader squareTxt = new TxtReader();
        squareTxt.openFile(languagePath,"squares_da");
        squareTxt.readLines();
        board = new Board(squareTxt, landedOnTxt, new ChanceDeck(cardsTxt, board));
        chanceDeck = new ChanceDeck(cardsTxt, board);
    }

    @Test
    public void pullRandomChanceCard() {
        testPlayer = new Player(null, 30000, new Piece(board.getStart()));
        PropertySquare tempProperty = board.getPropertyFromName(board.getOwnables()[11].getName());
        tempProperty.addHouse();
        tempProperty.setOwner(testPlayer);



        for (int i = 0; i < 1000; i++) {
            ChanceCard chanceCard = chanceDeck.pullRandomChanceCard();
            testPlayer.setLocation(tempProperty);
            String tempChanceCardType = chanceCard.getType();
            if (tempChanceCardType.equals("Earn") || tempChanceCardType.equals("Pay")) {
                int oldBalance = testPlayer.getBalance();
                assertEquals(testPlayer.getBalance(), oldBalance);
                chanceCard.applyEffect(testPlayer);
                if(oldBalance != 0){
                    assertNotEquals(oldBalance, testPlayer.getBalance());
                }
            } else if (tempChanceCardType.equals("Move")) {
                Square tempSquare = testPlayer.getLocation();
                assertEquals(testPlayer.getLocation(), tempSquare);
                chanceCard.applyEffect(testPlayer);
                assertNotEquals(tempSquare, testPlayer.getLocation());
            } else if(tempChanceCardType.equalsIgnoreCase("PayHouseCard")){
                int oldBalance = testPlayer.getBalance();
                assertEquals(testPlayer.getBalance(),oldBalance);
                chanceCard.applyEffect(testPlayer);
                if(oldBalance != 0){
                    assertNotEquals(testPlayer.getBalance(),oldBalance);
                }
            }
        }

    }

//    @Test
//    public void pullEarnChanceCards(){
//        Player testPlayer = new Player("testPlayer1",30000,null);
//        playerList = new PlayerList(null,null);
//        playerList.addPlayer("testplayer2", 30000);
//        playerList.addPlayer("testplayer3", 30000);
//        playerList.addPlayer("testplayer4", 30000);
//        turnLogic.playRound(playerList);
//
//        for (int i = 0; i < 1000; i++) {
//            ChanceCard chanceCard = chanceDeck.pullRandomChanceCard();
//            if (chanceCard.getDescription().equalsIgnoreCase("Earn") && earnCard.getAmount()==500) {
//                int oldBalance = testPlayer.getBalance();
//                turnLogic.withDrawMoneyFromPlayers(500,testPlayer);
//                if(oldBalance != 0){
//                    assertNotEquals(oldBalance, testPlayer.getBalance());
//
//                }
//
//
//            }
//
//        }
//
//    }


}