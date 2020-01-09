package domain;

import controllers.GUILogic;
import domain.squares.*;
import services.TxtReader;

/**
Game board for backend that consist of squares.
 */
public class Board {

    private int SIZE;
    private Square[] squares;
    OwnableSquare[] ownables = new OwnableSquare[28];
    int rekt = 0;
/**
Creates a board this constructor also create an ownablesquare array to manage them.
 */
    public void makeBoard(TxtReader squareTxt, TxtReader landedOnTxt, TxtReader cardsTxt, GUILogic guiLogic){
        
        //Chancefelterne skal bruge chancedækket i deres landOn, samtidig skal chancedækket bruge boardet, til at rykke spillerne
        ChanceDeck chanceDeck = new ChanceDeck(guiLogic, cardsTxt, this);
    
        SIZE = squareTxt.getN_LINES();
        squares = new Square[SIZE];
    
        //For all squares
        for (int i = 0; i < SIZE; i++) {
        
            //Extract corresponding square description
            String[] oneLine = squareTxt.getLine(String.valueOf(i)).split("-");
        
            //Create the proper square subclass and place in array
            if ("Regular".equalsIgnoreCase(oneLine[0])) {
                squares[i] = new RegularSquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt);

            } else if ("Factory".equals(oneLine[0])) {
                squares[i] = new FactorySquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, Integer.parseInt(oneLine[3]), 100, oneLine[0],oneLine[4]);
                ownables[rekt] = new FactorySquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, Integer.parseInt(oneLine[3]), 100, oneLine[0],oneLine[4]);
                rekt++;

            } else if ("Jail".equals(oneLine[0])) {
                squares[i] = new GoToJailSquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, this);

            } else if ("Chance".equals(oneLine[0])) {
                squares[i] = new ChanceSquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, chanceDeck);

            } else if ("Property".equals(oneLine[0])) {
                squares[i] = new PropertySquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, Integer.parseInt(oneLine[3]), 100, oneLine[0],oneLine[4]);
                ownables[rekt] = new PropertySquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, Integer.parseInt(oneLine[3]), 100, oneLine[0],oneLine[4]);
                rekt++;

            } else if ("Shipyard".equals(oneLine[0])) {
                squares[i] = new ShipyardSquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, Integer.parseInt(oneLine[3]), 100, oneLine[0],oneLine[4]);
                ownables[rekt] = new ShipyardSquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, Integer.parseInt(oneLine[3]), 100, oneLine[0],oneLine[4]);
                rekt++;

            }else if ("Tax".equals(oneLine[0])) {
                squares[i] = new TaxSquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, 1000000);

            }
        }
    }

    /**
     * Return square by index
     * @param index square number at board
     * @return the square at index
     */
    public Square getSquare(int index){
        return squares[index];
    }

    /**
     * Returns square by name
     * @param name square name
     * @return returns square by name
     */
    public Square getSquare(String name){
        Square currentSquare = null;
        for (Square square : squares){
            if (square.getName().equals(name))
                currentSquare = square;
        }
        return currentSquare;
    }

    /**
     * Ownablesquare
     * @return Returns ownable squares
     */
    public OwnableSquare[] getOwnables(){
        return ownables;
    }

    /**
     * Searches through the ownablesquare and returns number of not owned by same player and color
     * @param s ownablesquare that needs to be searched for
     * @return count of not owned square that is same color
     */
    public int searchColors(OwnableSquare s){
        int countcolor = 0;
        int playerowns = 0;
        int getrekt = 0;
        for (int i = 0; i <ownables.length ; i++) {
            if (s.getColor().equals(ownables[i].getColor()))
                countcolor++;
        }
        for (int i = 0; i <ownables.length ; i++) {
            if (ownables[i].getOwner()!=null && s.getOwner()==ownables[i].getOwner() && s.getColor().equals(ownables[i].getColor())){
                playerowns++;
            }
        }
        getrekt = countcolor - playerowns;
        return getrekt;
    }

    /**
     * Return the square that player will be moved to
     * @param player player that has to move
     * @param roll facevalue of the dice
     * @return square that player will move to
     */
    public Square nextLocation(Player player, int roll){
        
        int nextIndex;
        nextIndex = (player.getLocation().getIndex() + roll) % SIZE;
        return squares[nextIndex];
    }

    /**
     * Returns jail square
     * @return Returns jail square
     */
    public Square getJail(){
        return squares[6];
    }

    /**
     * Returns start square
     * @return Returns start square
     */
    public Square getStart() {
        return squares[0];
    }
    
//    public Square[] getPlayerSquares(Player player){
//
//        //For alle squares
//        for (int i = 0; i < squares.length; i++) {
//
//        }
//    }

}
