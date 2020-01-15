package controllers;

import static org.junit.Assert.*;

public class GameTest {
    
    public static void main(String[] args) {
    
//        new GameStub(new int[]{
//
//                1,1,2,3,1,1
//
//        }, new int[]{9000, 30000, 30000}, new int[]{0,0,0}, new int[] {1,1,1}).playGame();
//
        //Kommer i fængsel. Slår med terningerne 3 gange og kommer ikke ud. Kommer i fængsel med to ens. Har ikke råd til at betale sig ud.
        new GameStub(new int[]{
            
                3,3,3,3,3,2,1,1
        
        }, new int[]{5200, 2500, 30000}, new int[]{0,0,0},new int[]{7,7,7,7,7,7,7,7,7,7,7}).playGame();
    
    
    }
    
    
}