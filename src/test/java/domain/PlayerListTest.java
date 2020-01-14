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
    
}