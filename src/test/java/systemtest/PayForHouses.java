package systemtest;

import controllers.GameDriver;

public class PayForHouses {

    public static void main(String[] args) {
        new GameDriver(new int[]{1,1,4,6,1,2}, new int[]{30000, 30000, 30000}, new int[]{0,0,0}, new int[]{16,1,1,1,1,1,1,1}).playGame();
    }
}
