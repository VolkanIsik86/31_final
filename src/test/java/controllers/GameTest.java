package controllers;

import static org.junit.Assert.*;

public class GameTest {
    
    public static void main(String[] args) {
        
        new GameStub(new int[]{
            
                3,3,3,3,3,2,1,1,3,4,3,4,6,3,2,3,5,6,6,3,1,4,5,2
        
        }, new int[]{5200, 30000, 30000}, new int[]{0,0,0},new int[]{7,7,7,7,7,7,7,7,7,7,7}).playGame();
    
//        //Demonstrer hvordan spillet slutter, ift. auktionering af grunde
//        new GameStub(new int[]{
//
//                3,3,3,3,3,2,1,1
//
//        }, new int[]{5200, 2500, 30000}, new int[]{0,0,0},new int[]{7,7,7,7,7,7,7,7,7,7,7}).playGame();
    
    
    }
    
    
}