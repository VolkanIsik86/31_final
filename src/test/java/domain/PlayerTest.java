package domain;

import domain.squares.OwnableSquare;
import domain.squares.PropertySquare;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
PropertySquare squareTest = new PropertySquare("Testsquare",0,null,1000,50,"PropertySquare","red");
    PropertySquare squareTest2 = new PropertySquare("Testsquare2",1,null,6000,50,"PropertySquare","blue");
    @Test
    public void attemptToPurchase() {
        Player testPlayer = new Player("Test",5000,null);
        assertEquals(true,testPlayer.attemptToPurchase(squareTest));
        assertEquals(false,testPlayer.attemptToPurchase(squareTest2));
    }

    @Test
    public void attemptToPay() {
        Player testPlayer = new Player("Test",5000,null);
        assertEquals(true,testPlayer.attemptToPay(1000));
        assertEquals(false,testPlayer.attemptToPay(7000));
    }

    @Test
    public void withdraw() {
        Player testPlayer = new Player("Test",5000,null);
        testPlayer.withdraw(1000);
        assertEquals(4000, testPlayer.getBalance());
    }

    @Test
    public void deposit() {
        Player testPlayer = new Player("Test",5000,null);
        testPlayer.deposit(1000);
        assertEquals(6000,testPlayer.getBalance());
    }
}