package systemtest;

import controllers.GameDriver;
import controllers.GameTest;


public class TC22_MenuLogic {
    public static void main(String[] args) {
        new GameDriver(new int[] {
                3,1, // lander på skatte felt og betaler 3000 til skat
                1,-1,
                3,1, // lander på en ejendom og  køber den for 2000
                1,-1,
                10,12, // ryger i fængsel.
                1,-1,
                11,9, // betaler 1000 og kaster terininger, ryger i fængsel i gen.
                1,-1,
                2,1 // har ikke råd til at betale sig ud af fængsel.

        }, new int[] {7500,7500}, new int[] {0,0}, new int[] {0}).playGame();
    }
}


