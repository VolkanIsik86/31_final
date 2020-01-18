package domain;

import org.junit.Test;

import static org.junit.Assert.*;

//Testing the correctness and fairness of the die

public class TC11_12_DieTest {
    
    private final Die die = new Die();
    
    @Test
    public void roll() {
        
        double[] rolls = new double[6];
        
        for (int i = 0; i < 6000000; i++) {
            die.roll();
            
            //Check to see if facevalue is integer from 1-6
            assertEquals(0, die.getFaceValue() % 1);
            assertEquals(3.5, die.getFaceValue(),2.5);
            
            //And record in array
            rolls[die.getFaceValue()-1]++;

        }
    
        //Check to see if distribution is fair
        double[] expected = {1000000, 1000000, 1000000, 1000000, 1000000, 1000000};
        assertArrayEquals(expected, rolls, 10000);
        
    }
    @Test
    public void rollTwoDice() {


        for (int i = 0; i < 6000000; i++) {
            die.roll();
            int roll1 = die.getFaceValue();
            die.roll();
            int roll2 = die.getFaceValue();
            int rollSum = roll1 + roll2;


            // checks if the overall dice value is between 2-12. (tested for upto 6 million iterations)
            assertEquals(0, rollSum % 1);
            assertTrue(rollSum >= 2 && rollSum <= 12);
            assertEquals(7, rollSum, 5);

        }
    }
}
