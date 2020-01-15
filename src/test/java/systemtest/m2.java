package systemtest;

import controllers.GameStub;

public class m2 {

    public static void main(String[] args) {
        new GameStub(new int[]{3,5,2,3,3,5,6,6,5,1,4,6,3,4,1,3,2,2,3,3,5,5,6,6,4,6,5,5,2,6,5,5,5,5,1,2,1,1,1,3,5,6,3,2,1,1,2,2,3,3,2,3,4,6,2,3,1,3,1,2,3,4,2,1,1,2,2,3,3,4,5,1,3,3,3,4}, new int[]{30000, 30000, 30000}, new int[]{0,0,0}, new int[]{1,1,1,1,1,1}).playGame();
    }

}
