package systemtest;

import controllers.GameDriver;

public class WithDrawBalanceCardTest {
    public static void main(String[] args) {
        new GameDriver(new int[] {1,1,1,2,1,1}, new int[] {5000,5000,5000}, new int[] {0,0,0}, new int[] {17,17,17}).playGame();
    }
}
