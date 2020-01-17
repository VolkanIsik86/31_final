package systemtest;



import controllers.GameTestDriver;

public class StartTest {

    public static void main(String[] args) {
        new GameTestDriver(new int[]{3,4}, new int[]{30000, 30000, 30000}, new int[]{36,0,0}, new int[]{1,1,1,1,1,1,1,1}).playGame();
    }

}
