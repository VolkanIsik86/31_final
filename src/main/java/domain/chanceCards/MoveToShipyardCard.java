package domain.chanceCards;

import domain.Board;
import domain.Player;

public class MoveToShipyardCard extends ChanceCard {

    protected final Board board;

    public MoveToShipyardCard(String type, String description, Board board) {
        super(type, description);
        this.board = board;
    }

    //Check to find the closest shipyard above or equals to target
    private int findClosest(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++){
            //If target is less or equal to arr[i], return arr[i].
            if(target<=arr[i]){
                return arr[i];
            }
        }
        return arr[0];
    }

    /**
     * We make an array of all shipyards, which we use in the algorithm to find the nearest shipyard.
     * @param p
     * @return returns the position of the nearest shipyard
     */
    private int findNearestShipyard(Player p){
        int[] shipyardArray = new int[4];
        int j = 0;
        for(int i = 0; i < board.getOwnables().length; i ++){
            if(board.getOwnables()[i].getType().equals("Shipyard")){
                shipyardArray[j] = board.getOwnables()[i].getIndex();
                j++;
            }
        }

        return findClosest(shipyardArray,p.getLocation().getIndex());
    }

    //Sets the player location to the nearest shipyard
    public int applyEffect(Player player) {
//        player.setLocation(board.getSquare(findNearestShipyard(player)));
        return findNearestShipyard(player);
    }

}
