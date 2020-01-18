package systemtest;

import controllers.GameDriver;

public class TC18_chanceCards {
    
    public static void main(String[] args) {
        
        new GameDriver(new int[]{
                
                1,1,1,1,4,6,2,3,5,6,1,2,3,3,1,-1,1,-1,1,-1,1,-1,1,-1,1,-1,1,-1,1,-1,1,-1,1,-1,
            
        }, new int[]{30000}, new int[]{0}, new int[]{0, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18}).playGame();
        
    }
}
