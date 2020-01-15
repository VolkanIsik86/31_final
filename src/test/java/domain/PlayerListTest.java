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
    public void removePlayer(){
        testlist.addPlayer("p1", STARTBALANCE);
        Player p1 = testlist.getPlayer(0);
        
        testlist.addPlayer("p2", STARTBALANCE);
        Player p2 = testlist.getPlayer(1);
        
        testlist.addPlayer("p3", STARTBALANCE);
        Player p3 = testlist.getPlayer(2);
        
        testlist.removePlayer(p2);
        
        assertEquals(p1.getName(), testlist.getPlayer(0).getName());
        assertEquals(p3.getName(), testlist.getPlayer(1).getName());
        assertEquals(2, testlist.getPlayers().length);
        
    }
    
    
}