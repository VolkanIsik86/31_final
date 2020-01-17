package systemtest;

import controllers.GameTestDriver;

public class getPlayerValueTest {

    public static void main(String[] args) {
        new GameTestDriver(new int[]{1,1,1,1,0,1,2,4,1,2,1,3}, new int[]{40000, 30000, 5000}, new int[]{35,32,0}, new int[]{1,1,1,1,1,1,1,1}).playGame();
    }

}
