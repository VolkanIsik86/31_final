package domain.squares;

import domain.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class SquareTest {
    Square square0 = new TaxSquare("square0",0,null);
    Square square1 = new PropertySquare("square1",1,null,1000,500,"PropertySquare","red");
    Square square2 = new GoToJailSquare("square2",2,null,null);
    Square square3 = new RegularSquare("square3",3,null);
    Square square4 = new ChanceSquare("square4",4,null,null);
    Player testPlayer = new Player("Test",1000,null);
    @Test
    public void landedOn() {
        assertEquals("Tax square",square0.landedOn(testPlayer));

        assertEquals("Not owned "+"PropertySquare"+" squareT",square1.landedOn(testPlayer));

//        assertEquals("GoToJail square",square2.landedOn(testPlayer));
//        assertEquals(true,testPlayer.getJail());

        assertEquals("Regular square",square3.landedOn(testPlayer));
        assertEquals("Press OKS",square4.landedOn(testPlayer));
    }
}