package domain;

import domain.squares.*;
import org.junit.Test;
import services.TxtReader;
import static org.junit.Assert.*;

//Testes kun positivt, da brugeren ikke skal interegere direkte med objektet
public class BoardTest {

    private final Board board;
    
    private final Player player;
    
    public BoardTest(){
        TxtReader squareTxt = new TxtReader();
        String languagePath = "src/main/java/services/languagefiles/";
        squareTxt.openFile(languagePath,"squares_da");
        squareTxt.readLines();

        TxtReader landedOnTxt = new TxtReader();
        landedOnTxt.openFile(languagePath,"turnLogic_da");
        landedOnTxt.readLines();

        TxtReader cardsTxt = new TxtReader();
        cardsTxt.openFile(languagePath,"chanceCards_da");
        cardsTxt.readLines();

        TxtReader guiTxt = new TxtReader();
        guiTxt.openFile(languagePath, "guitext_da");
        guiTxt.readLines();
        
        board = new Board(squareTxt, landedOnTxt);
        
        player = new Player("Mikkel", 20, new Piece(board.getStart()));
    }
    
    @Test
    public void getSquare() {

        assertEquals("Hvidovrevej", board.getSquare(3).getName());
        assertEquals(3, board.getSquare(3).getIndex());
        assertEquals(PropertySquare.class, board.getSquare(3).getClass());

        assertEquals("Carlsberg", board.getSquare(28).getName());
        assertEquals(28, board.getSquare(28).getIndex());
        assertEquals(FactorySquare.class, board.getSquare(28).getClass());

        assertEquals("På Besøg",board.getSquare(10).getName());
        assertEquals(10, board.getSquare(10).getIndex());
        assertEquals(RegularSquare.class, board.getSquare(10).getClass());

        assertEquals("Vimmelskaftet",board.getSquare(32).getName());
        assertEquals(32, board.getSquare(32).getIndex());
        assertEquals(PropertySquare.class, board.getSquare(32).getClass());
    }
    
    @Test
    public void nextLocation() {

        player.setLocation(board.getSquare(4));
        assertEquals(9, board.nextLocation(player, 5).getIndex());

        player.setLocation(board.getSquare(39));
        assertEquals(1, board.nextLocation(player,2).getIndex());
        
    }

    @Test
    public void getOwnables() {
        assertEquals("blue",board.getOwnables()[0].getColor());
    }

    @Test
    public void searchColors() {
        
        //todo det er ikke godt at redigere i atributter der skal være private, brug i stedet en public metode til at sætte ejere
        //todo synes ikke testen er fyldestgørende - Mikkel
        board.getOwnables()[0].setOwner(player);
        board.getOwnables()[1].setOwner(player);
        assertEquals(0,board.searchColors(board.getOwnables()[1]));

    }

    @Test
    public void getPlayerSquares() {
        board.ownables[1].setOwner(player);
        board.ownables[7].setOwner(player);
        assertEquals(2,board.getPlayerSquares(player).length);
    }

    @Test
    public void getPlayerSquareNames() {
        board.ownables[2].setOwner(player);
        board.ownables[5].setOwner(player);
        assertEquals("Øresund",board.getPlayerSquareNames(player)[0]);
        assertEquals("Allegade",board.getPlayerSquareNames(player)[1]);
    }

    @Test
    public void doesPlayerOwnAnySquares() {
        assertFalse(board.doesPlayerOwnAnySquares(player));
        board.ownables[2].setOwner(player);
        assertTrue(board.doesPlayerOwnAnySquares(player));
    }
}