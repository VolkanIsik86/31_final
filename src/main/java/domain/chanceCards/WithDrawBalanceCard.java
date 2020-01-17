package domain.chanceCards;

import domain.ChanceDeck;
import domain.Player;
import domain.PlayerList;

public class WithDrawBalanceCard extends ChanceCard{

    final int amount;
    PlayerList playerList;



    public WithDrawBalanceCard(String type, String description, ChanceDeck chanceDeck, int amount, PlayerList playerList) {
        super(type, description, chanceDeck);
        this.amount = amount;
        this.playerList = playerList;

    }

    public Player[] restOfPlayersList (Player player, PlayerList playerList) {
        int tempo = 0;
        Player[] restOfPlayers = new Player[playerList.getPlayers().length - 1];
        for (int i = 0; i < playerList.getPlayers().length; i++) {
            if (!(player.getName().equals(playerList.getPlayers()[i].getName()))) {
                restOfPlayers[tempo] = playerList.getPlayers()[i];
                tempo++;
            }
        }
        return restOfPlayers;
    }

    public int WithDrawMoneyFromOthers (int amount, Player player, PlayerList playerList) {
        int totalMoneyFromOther = 0;
        for (int i = 0; i < restOfPlayersList(player,playerList).length; i++) {
            if (restOfPlayersList(player,playerList)[i].attemptToPay(amount)) {
                restOfPlayersList(player,playerList)[i].withdraw(amount);
                totalMoneyFromOther += this.amount;
            }
        }
        return totalMoneyFromOther;
    }
    @Override
    public int applyEffect(Player p) {
        p.deposit(WithDrawMoneyFromOthers(amount,p,playerList));
        return 0;
    }
}
