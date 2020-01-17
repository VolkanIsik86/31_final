package systemtest;


import controllers.GameTestDriver;

public class ShipyardTest {

    public static void main(String[] args) {
        new GameTestDriver(new int[]{1,1,4,6,1,2}, new int[]{30000, 30000, 30000}, new int[]{3,12,0}, new int[]{1,1,1,1,1,1,1,1}).playGame();
    }

}
