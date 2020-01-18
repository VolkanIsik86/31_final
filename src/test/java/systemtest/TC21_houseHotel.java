package systemtest;

import controllers.GameDriver;

public class TC21_houseHotel {

    public static void main(String[] args) {
        new GameDriver(new int[]{1,1,1,1,1,3,1,1,1,1}, new int[]{40000, 50000, 30000}, new int[]{35,35,0}, new int[]{1,1,1,1,1,3,1,1,1,1}).playGame();
    }
}
