package controllers;

import static org.junit.Assert.*;

public class GameTest {
    
    public static void main(String[] args) {
        
        //Kommer i fængsel. Slår med terningerne 3 gange og kommer ikke ud. Kommer i fængsel med to ens. Har ikke råd til at betale sig ud.
        new GameStub(new int[]{
        
                4,0,5,7,
                1,2,
                7,9,7,9,
                
                
        }, new int[]{28800, 30000, 30000}, new int[]{0,0,0}).playGame();
    }
    
//    //Kommer i fængsel. Slår med terningerne 3 gange og kommer ikke ud. Kommer i fængsel med to ens. Har ikke råd til at betale sig ud.
//        new GameStub(new int[]{
//
//        1,1,
//                1,2,1,2,1,2,
//                1,2,1,2,1,2,
//                1,2,1,2,1,2,
//                1,2,
//                9,11,1,2,
//                1,2,1,2,1,2,
//                1,2,1,2,1,2,
//
//    }, new int[]{6700, 30000, 30000}, new int[]{24,0,0}).playGame();
    
}