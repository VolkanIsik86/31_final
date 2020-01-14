package systemtest;

import controllers.GameStub;

public class getPlayerValueTest {

    public static void main(String[] args) {
        new GameStub(new int[]{1,1,1,1,0,1,3,4}, new int[]{40000, 30000, 5000}, new int[]{35,32,0}).playGame();
    }

}
