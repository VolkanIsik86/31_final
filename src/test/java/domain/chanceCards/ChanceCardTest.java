package domain.chanceCards;


import domain.*;
import domain.squares.PropertySquare;
import domain.squares.Square;
import org.junit.Test;
import services.TxtReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ChanceCardTest {
    private final Board board;
    Player testPlayer;
    final ChanceDeck chanceDeck;
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
        board = new Board(squareTxt, landedOnTxt);
        chanceDeck = new ChanceDeck(cardsTxt, board, playerList);
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
                int expected = 3;
                assertEquals(expected,chanceCard.applyEffect(testPlayer));
            } else if(tempChanceCardType.equalsIgnoreCase("PayHouseCard")){
                int oldBalance = testPlayer.getBalance();
                assertEquals(testPlayer.getBalance(),oldBalance);
                chanceCard.applyEffect(testPlayer);
                if(oldBalance != 0){
                    assertNotEquals(testPlayer.getBalance(),oldBalance);
                }
            }else if(tempChanceCardType.equalsIgnoreCase("MoveToShipyardCard")){
                nearestShipyardTest(chanceCard, testPlayer, 2, 5);
                nearestShipyardTest(chanceCard, testPlayer, 10, 15);
                nearestShipyardTest(chanceCard, testPlayer, 9, 15);
                nearestShipyardTest(chanceCard, testPlayer, 39, 5);
                nearestShipyardTest(chanceCard, testPlayer, 20, 25);
            }
        }

    }

    private void nearestShipyardTest(ChanceCard chanceCard, Player testPlayer, int newLocation, int expected) {
        testPlayer.setLocation(board.getSquare(newLocation));
        testPlayer.setLocation(board.getSquare(chanceCard.applyEffect(testPlayer)));
        assertEquals(testPlayer.getLocation().getIndex(),expected);
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
