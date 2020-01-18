package domain.squares;

import domain.Piece;
import domain.Player;
import org.junit.Test;

import static org.junit.Assert.*;

public class TC05_SquareTest {
    final int[] rentLadder = {1,2,3,4,5,6};
    final Square square0 = new TaxSquare("square0",0,null);
    final Square square1 = new PropertySquare("square1",1,null,1000,"PropertySquare","red",100, rentLadder, null);
    final Square square2 = new RegularSquare("square2",2,null);
    final Square square3 = new GoToJailSquare("square3",3,null);
    final Square square4 = new ChanceSquare("square4",4,null);
    final Piece testpiece = new Piece(square0);
    final Player testPlayer = new Player("Test",1000,testpiece);

    @Test
    public void landedOn() {
        assertEquals("Tax square T",square0.landedOn(testPlayer));

        assertEquals("Not owned "+"PropertySquare"+" square N",square1.landedOn(testPlayer));

        assertEquals("Regular square R",square2.landedOn(testPlayer));

        assertEquals("GoToJail square J",square3.landedOn(testPlayer));
        assertEquals("Press OKS",square4.landedOn(testPlayer));
    }
}