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
            
                1,2,4,3,2,2,4,4,2,6,3,2,4,5,2,2,4,6,4,3,6,4,3,2,4,1,3,4,5,1,3,5,6,3,2,5,6,6,4,2,2,3,3,4,2,3,5,6,1
        
        }, new int[]{999999, 5000, 5000}, new int[]{0,0,0},new int[]{7,7,7,7,7,7,7,7,7,7,7}).playGame();
    
    
    }
    
    
}