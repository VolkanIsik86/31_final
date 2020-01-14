package controllers;

import static org.junit.Assert.*;

public class GameTest {
    
    public static void main(String[] args) {
        new GameStub(new int[]{1,2,1,2}, new int[]{5000, 5000, 5000}, new int[]{1,2,3}).playGame();
    }
    
}