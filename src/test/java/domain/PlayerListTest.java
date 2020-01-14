package domain;

import controllers.GUILogic;
import domain.squares.Square;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerListTest {

    private final PlayerList testlist = new PlayerList(null,null);
    private final int STARTBALANCE = 30000;
    
    @Test
    public void addPlayer() {

        testlist.addPlayer("test", STARTBALANCE);
        String expectedName = "test";
        assertEquals(Player.class,testlist.getPlayer(0).getClass());
        assertEquals(expectedName,testlist.getPlayer(0).getName());
    }

    @Test
    public void sortPlayersByPoint() {
        testlist.addPlayer("test",STARTBALANCE);
        testlist.addPlayer("test1",STARTBALANCE);
        testlist.addPlayer("test2",STARTBALANCE);
        testlist.addPlayer("test3",STARTBALANCE);
        testlist.addPlayer("test4",STARTBALANCE);
        for (int i = 0; i < testlist.NumberOfPlayers() ; i++) {
            testlist.getPlayer(i).setBalance(i);
        }
        testlist.sortPlayersByPoint();

        int[] points = new int[5];
        for (int i = 0; i < testlist.NumberOfPlayers() ; i++){
            points[i] = testlist.getPlayer(i).getBalance();
        }

        for (int i = 0; i < (points.length - 1); i++){
            assertTrue(points[i] < points[i + 1]);
        }
        assertTrue(points[points.length - 2] < points[points.length - 1]);
    }

    @Test
    public void getWinner() {
        testlist.addPlayer("test",STARTBALANCE);
        testlist.addPlayer("test1",STARTBALANCE);

        testlist.getPlayer(0).setBalance(20000);
        testlist.getPlayer(1).setBalance(18000);

        Player expected = testlist.getPlayer(0);
        Player actual = testlist.getWinner();

        assertEquals(expected,actual);

        testlist.getPlayer(0).setBalance(20000);
        testlist.getPlayer(1).setBalance(20000);

        Player expectedDraw = null;
        Player actualDraw = testlist.getWinner();
        assertEquals(expectedDraw,actualDraw);


    }


}