package systemtest;

import controllers.GameDriver;

public class StartTest {

    public static void main(String[] args) {
        new GameDriver(new int[]{3,4}, new int[]{30000, 30000, 30000}, new int[]{36,0,0}, new int[]{1,1,1,1,1,1,1,1}).playGame();
    }

}
