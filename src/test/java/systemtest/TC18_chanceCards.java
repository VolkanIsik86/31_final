package systemtest;

import controllers.GameDriver;

public class TC18_chanceCards {

    public static void main(String[] args) {

        new GameDriver(new int[]{

                1,1,1,0,1,1,1,1,4,3,16,17,15,15,1,1

        }, new int[]{30000,30000,30000}, new int[]{0,0,0}, new int[]{

                18,18,18,18,18,18,17

        }).playGame();

    }

}
