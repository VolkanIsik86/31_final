package domain;

import domain.squares.PropertySquare;
import org.junit.Test;

import static org.junit.Assert.*;

public class TC06_07_08_PlayerTest {
    final PropertySquare squareTest = new PropertySquare("Testsquare", 0, null, 1000, "PropertySquare", "red", 100, new int[]{1, 2, 3}, null);
    final PropertySquare squareTest2 = new PropertySquare("Testsquare2", 1, null, 6000, "PropertySquare", "blue", 100, new int[]{1, 2, 3}, null);

    @Test
    public void attemptToPurchase() {
        Player testPlayer = new Player("Test", 5000, null);
        assertTrue(testPlayer.attemptToPurchase(squareTest));
        assertFalse(testPlayer.attemptToPurchase(squareTest2));
    }

    @Test
    public void attemptToPay() {
        Player testPlayer = new Player("Test", 5000, null);
        assertTrue(testPlayer.attemptToPay(1000));
        assertFalse(testPlayer.attemptToPay(7000));
    }

    @Test
    public void withdraw() {
        Player testPlayer = new Player("Test", 5000, null);
        testPlayer.withdraw(1000);
        assertEquals(4000, testPlayer.getBalance());
    }

    @Test
    public void deposit() {
        Player testPlayer = new Player("Test", 5000, null);
        testPlayer.deposit(1000);
        assertEquals(6000, testPlayer.getBalance());
    }

    @Test
    public void getAttemptsToGetOutOfJail(){
        Player testPlayer = new Player("Test",5000,null);
        int actual = testPlayer.getAttemptsToGetOutOfJail();
        int expected = 0;
        assertEquals(expected,actual);
    }

    @Test
    public void incrementAttemptsToGetOutOfJail(){
        Player testPlayer = new Player("Test",5000,null);
        testPlayer.incrementAttemptsToGetOutOfJail();
        int expected = 1;
        assertEquals(expected,testPlayer.getAttemptsToGetOutOfJail());
    }
}