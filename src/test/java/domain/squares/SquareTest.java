package domain.squares;

import domain.Piece;
import domain.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class SquareTest {
    int[] rentLadder = {1,2,3,4,5,6};
    Square square0 = new TaxSquare("square0",0,null);
    Square square1 = new PropertySquare("square1",1,null,1000,"PropertySquare","red",100, rentLadder, null);
    Square square2 = new RegularSquare("square2",2,null);
    Square square3 = new GoToJailSquare("square3",3,null);
    Square square4 = new ChanceSquare("square4",4,null,null);
    Piece testpiece = new Piece(square0);
    Player testPlayer = new Player("Test",1000,testpiece);

    @Test
    public void landedOn() {
        assertEquals("Tax square",square0.landedOn(testPlayer));

        assertEquals("Not owned "+"PropertySquare"+" squareT",square1.landedOn(testPlayer));

        assertEquals("Regular square",square2.landedOn(testPlayer));

        assertEquals("GoToJail square",square3.landedOn(testPlayer));
        assertEquals("Press OKS",square4.landedOn(testPlayer));
    }
}