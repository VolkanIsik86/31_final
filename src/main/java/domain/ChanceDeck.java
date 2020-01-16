package domain;

import controllers.GUILogic;
import domain.chanceCards.ChanceCard;
import domain.chanceCards.EarnCard;
import domain.chanceCards.PayHouseCard;
import domain.chanceCards.MoveCard;
import domain.chanceCards.PayCard;
import services.TxtReader;

import java.util.Random;

public class ChanceDeck {
    
    private final int N_CARDS = 18;
    protected final ChanceCard[] chanceCards;
    private final Random rnd = new Random();
    
    //Creates all the Chance cards and adds them to an array
    public ChanceDeck(TxtReader cardsTxt, Board board) {
        
        chanceCards = new ChanceCard[N_CARDS];
    
        //For all cards
        for (int i = 0; i < N_CARDS; i++) {
    
            //Extract corresponding card description
            String[] oneLine = cardsTxt.getLine(String.valueOf(i)).split("-");
    
            //Creates the proper card subclass and places it in array
            if ("Earn".equalsIgnoreCase(oneLine[0])) {
                chanceCards[i] = new EarnCard(oneLine[0], oneLine[2], this, Integer.parseInt(oneLine[1]));
                
            } else if ("Move".equals(oneLine[0])) {
                chanceCards[i] = new MoveCard(oneLine[0], oneLine[2], this, Integer.parseInt(oneLine[1]), board);
                
            } else if ("Pay".equals(oneLine[0])) {
                chanceCards[i] = new PayCard(oneLine[0], oneLine[2], this, Integer.parseInt(oneLine[1]), cardsTxt);
    
            } else if ("PayHouseCard".equals(oneLine[0])) {
                chanceCards[i] = new PayHouseCard(oneLine[0], oneLine[2], this, Integer.parseInt(oneLine[1]), cardsTxt, board);
        }
        }
    }

    /**
     * method Withdraws given amount from other Players and desposits into current players account.
     *
     * @param amount
     * @param currentPlayer
     * @param guiLogic
     * @param playerList
     */
    public void withDrawMoneyFromPlayers(int amount, Player currentPlayer, PlayerList playerList, GUILogic guiLogic) {
        int tempo = 0;
        int totalMoneyFromOthers = -500;
        Player[] restOfPlayers = new Player[playerList.getPlayers().length - 1];
        for (int i = 0; i < playerList.getPlayers().length; i++) {
            if (!(currentPlayer.getName().equals(playerList.getPlayers()[i].getName()))) {
                restOfPlayers[tempo] = playerList.getPlayers()[i];
                tempo++;
            }
        }
        for (int i = 0; i < restOfPlayers.length; i++) {
            if (restOfPlayers[i].attemptToPay(amount)) {
                restOfPlayers[i].withdraw(amount);
                guiLogic.setPlayerBalance(restOfPlayers[i]);
                totalMoneyFromOthers += amount;
            }
        }

        currentPlayer.deposit(totalMoneyFromOthers);

    }
    
    public ChanceCard pullRandomChanceCard(){
    
        int i = rnd.nextInt(N_CARDS);
        return chanceCards[i];
        
    }

    
}

