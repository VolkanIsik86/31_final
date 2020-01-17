package domain.chanceCards;

import domain.Board;
import domain.Player;

public class MoveToShipyardCard extends ChanceCard {

    protected final Board board;

    public MoveToShipyardCard(String type, String description, Board board) {
        super(type, description);
        this.board = board;
    }

    //https://www.geeksforgeeks.org/find-closest-number-array/ Algorithm to find the nearest number in a sorted algorithm
    private int findClosest(int[] arr, int target)
    {
        int n = arr.length;

        //If the first instance in the array is less or equals to our target, we can just return the first position
        if (target <= arr[0])
            return arr[0];
        //If it's larger than the last or equal, we just return last position in array
        if (target >= arr[n - 1])
            return arr[n - 1];

        int i = 0, j = n, mid = 0;
        //We do Binary search to check if our target is in the array at all
        while (i < j) {
            mid = (i + j) / 2;

            if (arr[mid] == target)
                return arr[mid];

            if (target < arr[mid]) {
                //If our target is smaller than mid, but larger than mid-1, we use getClosest method
                if (mid > 0 && target > arr[mid - 1])
                    return getClosest(arr[mid - 1],
                            arr[mid], target);

                j = mid;
            }

            else {
                if (mid < n-1 && target < arr[mid + 1])
                    return getClosest(arr[mid],
                            arr[mid + 1], target);
                i = mid + 1;
            }
        }

        return arr[mid];
    }

    private int getClosest(int val1, int val2,
                                 int target)
    {
        if (target - val1 >= val2 - target)
            return val2;
        else
            return val1;
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
        player.setLocation(board.getSquare(findNearestShipyard(player)));
        return findNearestShipyard(player);
    }

}
