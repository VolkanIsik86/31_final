package systemtest;


import controllers.GameDriver;

public class TC14_ShipyardTest {

    public static void main(String[] args) {
        new GameDriver(new int[]{1,1,4,6,1,2}, new int[]{30000, 30000, 30000}, new int[]{3,12,0}, new int[]{1,1,1,1,1,1,1,1}).playGame();
    }

}
