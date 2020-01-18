package systemtest;

import controllers.GameDriver;

public class FartkontrolTest {

    public static void main(String[] args) {
        new GameDriver(new int[]{2, 2, 3, 3, 5, 5}, new int[]{40000}, new int[]{0,}, new int[]{1, 1, 1, 1, 1, 1, 1, 1}).playGame();
    }

}
