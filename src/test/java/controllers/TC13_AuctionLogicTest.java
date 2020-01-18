package controllers;

import domain.Player;
import domain.PlayerList;
import org.junit.Test;

import static org.junit.Assert.*;

public class TC13_AuctionLogicTest {

    AuctionLogic auctionLogic = new AuctionLogic(null,null,null,null);

    @Test
    public void downgradePlayersarr() {

        Player[] players = new Player[3];
        players[0] = new Player("test1",5000,null);
        players[1] = new Player("test2",5000,null);
        players[2] = new Player("test3",5000,null);

        assertEquals(3, players.length);
        Player removePlayer = players[0];
        players = auctionLogic.downgradePlayersarr(players,removePlayer);
        assertEquals(2,players.length);
    }
}