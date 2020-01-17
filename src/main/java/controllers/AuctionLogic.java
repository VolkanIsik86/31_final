package controllers;

import controllers.GUILogic;
import controllers.MenuLogic;
import domain.Player;
import domain.PlayerList;
import domain.squares.OwnableSquare;
import services.TxtReader;

public class AuctionLogic {

    final PlayerList playerList;
    final MenuLogic menuLogic;
    final GUILogic guiLogic;
    final TxtReader turnLogicTxt;


    public AuctionLogic(PlayerList playerList, MenuLogic menuLogic, GUILogic guiLogic, TxtReader turnLogicTxt) {
        this.playerList = playerList;
        this.menuLogic = menuLogic;
        this.guiLogic = guiLogic;
        this.turnLogicTxt = turnLogicTxt;
    }

    /**
     * Start auction if player does not want to buy the square he landed on.
     *
     * @param square the squre where player landed on
     * @param player the player who landed on a square
     */
    public void auctioning(OwnableSquare square, Player player) {
        menuLogic.auctionStartMenu(square);
        // the current bid
        int bid = 0;
        int highestBid = 0;
        // player count in auction
        int count;
        Player auctionWinner = null;
        // removes the player who does not want to buy and creates a new array of auctioning players
        Player[] biddingPlayers = downgradePlayersarr(playerList.getPlayers(), player);
        count = biddingPlayers.length;

        // auctioning goes on until there no player left in count
        while (count != 0) {
            // auction within players bidding
            for (int i = 0; i < biddingPlayers.length; i++) {
                String bided = menuLogic.auctionMenu(biddingPlayers[i], highestBid, square);
                // if a player presses pass biddingplayers array will me rearranged.
                if (bided.equals("pass")) {
                    Player[] temparr = downgradePlayersarr(biddingPlayers, biddingPlayers[i]);
                    i = i-1;
                    count--;
                    biddingPlayers = temparr;
                    if (auctionWinner != null && biddingPlayers.length == 1) {
                        finalizeAuction(auctionWinner,square,highestBid);
                        return;
                    }
                } else {
                    // auction winner will be set to current bidding player
                    if (count == 1) {
                        count--;
                    }
                    bid = Integer.parseInt(bided);
                    if (bid > highestBid) {
                        highestBid = bid;
                    }
                    auctionWinner = biddingPlayers[i];
                }
            }
            // if all passes the auction then th square will be given to a random player in playerlist.
            if (count == 0 && auctionWinner == null) {
                guiLogic.showMessage(turnLogicTxt.getLine("Nobody bid randon new owner"));
                auctionWinner = playerList.getPlayer((int)(Math.random()*(playerList.getPlayers().length)));
                finalizeAuction(auctionWinner,square,highestBid);
                return;
            }
        }
        finalizeAuction(auctionWinner,square,highestBid);
    }

    /**
     * removes the player who does not want to buy and creates a new array of auctioning players
     *
     * @param players array of players that has to be downgraded.
     * @param player  the player who will be removed from array
     * @return an array of players that has reduced by 1
     */
    
    //todo logikken er også skrevet i playerlist -Mikkel
    public Player[] downgradePlayersarr(Player[] players, Player player) {
        // used to create a new array that consist of players who has not pressed pass
        int temp = 0;
        Player[] newPlayers = new Player[players.length - 1];

        for (int i = 0; i < players.length; i++) {
            if (!(player.getName().equals(players[i].getName()))) {
                newPlayers[temp] = players[i];
                temp++;
            }
        }
        return newPlayers;
    }

    /**
     * Finalizes auction by setting square owner to auction winner
     * @param auctionWinner player who wins auction
     * @param square the square that is auctioned for
     * @param highestBid  current highest bid
     */
    public void finalizeAuction(Player auctionWinner , OwnableSquare square , int highestBid){
        square.setOwner(auctionWinner);
        guiLogic.setSquareAuction(square);
        auctionWinner.withdraw(highestBid);
        guiLogic.setPlayerBalance(auctionWinner);
        guiLogic.showMessage(turnLogicTxt.getLine("The winner of the auction is") + " " + auctionWinner.getName());
    }
}
