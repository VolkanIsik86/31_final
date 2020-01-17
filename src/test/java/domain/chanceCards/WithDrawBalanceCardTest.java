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


    @Test
    public void pullWithDrawBalanceCard() {
        PlayerList playerList = new PlayerList(null,null);
        WithDrawBalanceCard drawBalanceCard = new WithDrawBalanceCard(null, null,null,500, playerList);


        playerList.addPlayer("talha", 5000);
        playerList.addPlayer("max", 5000);
        playerList.addPlayer("lucid", 5000);

        drawBalanceCard.applyEffect(playerList.getPlayer(0)); //laver apply effect på player "talha".
        assertEquals(6000,playerList.getPlayer(0).getBalance());
        assertEquals(4500,playerList.getPlayer(1).getBalance());
        assertEquals(4500,playerList.getPlayer(2).getBalance());

        }
    }


