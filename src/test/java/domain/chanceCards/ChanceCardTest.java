package domain.chanceCards;


import domain.Board;
import domain.ChanceDeck;
import domain.Piece;
import domain.Player;
import domain.squares.PropertySquare;
import domain.squares.Square;
import org.junit.Test;
import services.TxtReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ChanceCardTest {
    private final Board board = new Board();
    Player testPlayer;
    ChanceDeck chanceDeck;


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
        board.makeBoard(squareTxt, landedOnTxt, cardsTxt);
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


}
