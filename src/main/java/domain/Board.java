package domain;

import controllers.GUILogic;
import domain.squares.*;
import services.TxtReader;

/**
 * Game board for backend that consist of squares.
 */
public class Board {

    private int SIZE;
    private ChanceDeck chanceDeck;
    private Square[] squares;
    //todo skal da være private
    OwnableSquare[] ownables = new OwnableSquare[28];
    PropertySquare[] properties = new PropertySquare[22];
    int rekt = 0;
    int prop = 0;

    /**
     * Creates a board this constructor also create an ownablesquare array to manage them.
     */
    public Board(TxtReader squareTxt, TxtReader landedOnTxt, ChanceDeck chanceDeck) {
        
        this.chanceDeck = chanceDeck;
        
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

                squares[i] = ownables[rekt] = new FactorySquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, Integer.parseInt(oneLine[3]), oneLine[0], oneLine[4], this);
                rekt++;

            } else if ("Jail".equals(oneLine[0])) {
                squares[i] = new GoToJailSquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt);

            } else if ("Chance".equals(oneLine[0])) {
                squares[i] = new ChanceSquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, chanceDeck);

            } else if ("Property".equals(oneLine[0])) {

                //Read rents and then converts them to int
                String[] rentsString = oneLine[6].split("'");
                int[] rentsInts = new int[6];

                for (int j = 0; j < 6; j++) {
                    rentsInts[j] = Integer.parseInt(rentsString[j]);
                }

                squares[i] = ownables[rekt] = properties[prop] = new PropertySquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, Integer.parseInt(oneLine[3]), oneLine[0], oneLine[4], Integer.parseInt(oneLine[5]), rentsInts, this);
                rekt++;
                prop++;

            } else if ("Shipyard".equals(oneLine[0])) {

                squares[i] = ownables[rekt] = new ShipyardSquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt, Integer.parseInt(oneLine[3]), oneLine[0], oneLine[4], this);
                rekt++;

            } else if ("Tax".equals(oneLine[0])) {
                squares[i] = new TaxSquare(oneLine[1], Integer.parseInt(oneLine[2]), landedOnTxt);

            }
        }

    }

    /**
     * Return square by index
     *
     * @param index square number at board
     * @return the square at index
     */
    public Square getSquare(int index) {
        return squares[index];
    }

    /**
     * Returns square by name
     *
     * @param name square name
     * @return returns square by name
     */
    public Square getSquare(String name) {
        Square currentSquare = null;
        for (Square square : squares) {
            if (square.getName().equals(name))
                currentSquare = square;
        }
        return currentSquare;
    }

    /**
     * Ownablesquare
     *
     * @return Returns ownable squares
     */
    public OwnableSquare[] getOwnables() {
        return ownables;
    }

    /**
     * Propertysquare
     *
     * @return Returns property squares
     */
    public PropertySquare[] getProperties() {
        return properties;
    }

    /**
     * Searches through the ownablesquare and returns number of not owned by same player and color
     *
     * @param s ownablesquare that needs to be searched for
     * @return count of not owned square that is same color
     */

    public int searchColors(OwnableSquare s) {
        int countcolor = 0;
        int playerowns = 0;

        //Count number of squares with the same color
        for (int i = 0; i < ownables.length; i++) {
            if (s.getColor().equals(ownables[i].getColor()))
                countcolor++;
        }

        //
        for (int i = 0; i < ownables.length; i++) {
            if (ownables[i].getOwner() != null && s.getOwner() == ownables[i].getOwner() && s.getColor().equals(ownables[i].getColor())) {
                playerowns++;
            }
        }

        int getrekt = countcolor - playerowns;
        return getrekt;
    }

    /**
     * Return the square that player will be moved to
     *
     * @param player player that has to move
     * @param roll   facevalue of the dice
     * @return square that player will move to
     */
    public Square nextLocation(Player player, int roll) {

        int nextIndex;
        nextIndex = (player.getLocation().getIndex() + roll) % SIZE;
        return squares[nextIndex];
    }

    /**
     * Returns jail square
     *
     * @return Returns jail square
     */
    public Square getJail() {
        return squares[10];
    }

    /**
     * Returns start square
     *
     * @return Returns start square
     */
    public Square getStart() {
        return squares[0];
    }
    
    public ChanceDeck getChanceDeck(){
        return chanceDeck;
    }

    public int getPlayerValue(Player player) {
        int value = 0;
        value = value + player.getBalance();
        Square[] playerOwn = getPlayerSquares(player);
        for (Square square : playerOwn) {
            value = value + square.getValue();
        }
        return value;

    }

    /**
     * Finds all the squares belonging to a certain player and return them in an array
     *
     * @param player The player whose squares you want to find
     * @return An array of squares
     */
    public Square[] getPlayerSquares(Player player) {

        Square[] playerSquares = new Square[0];

        //For all ownable squares
        for (int i = 0; i < ownables.length; i++) {

            if (ownables[i].getOwner() != null) {

                //If owner is the same
                if (ownables[i].getOwner().equals(player)) {

                    //Increase size of array by 1
                    Square[] tempArr = new Square[playerSquares.length + 1];
                    for (int j = 0; j < playerSquares.length; j++) {
                        tempArr[j] = playerSquares[j];
                    }
                    playerSquares = tempArr;

                    //And add the square
                    playerSquares[playerSquares.length - 1] = ownables[i];
                }
            }


        }
        return playerSquares;
    }

    /**
     * Finds the names of all the squares belonging to a certain player
     *
     * @param player The player whose square names you want to find
     * @return An array of the names
     */
    public String[] getPlayerSquareNames(Player player) {

        //First find the square belonging to the player
        Square[] playerSquares = getPlayerSquares(player);

        String[] playerSquareNames = new String[playerSquares.length];

        //Then find the names
        for (int i = 0; i < playerSquares.length; i++) {
            playerSquareNames[i] = playerSquares[i].getName();
        }

        return playerSquareNames;
    }

    /**
     * Searches in ownablesquare array and returns boolean if player owns a ownablesquare
     *
     * @param player player needed for search in the array
     * @return boolean
     */
    public boolean doesPlayerOwnAnySquares(Player player) {
        if (getPlayerSquares(player).length > 0) {
            return true;
        } else {
            return false;
        }

    }

    public OwnableSquare getOwnableSquareFromName(String name) {
        for (int i = 0; i < ownables.length; i++) {
            if (ownables[i].getName().equals((name))) {
                return ownables[i];
            }
        }
        return null;
    }

    public Square getSquareFromName(String name) {
        for (int i = 0; i < squares.length; i++) {
            if (squares[i].getName().equals((name))) {
                return squares[i];
            }
        }
        return null;
    }

    public PropertySquare getPropertyFromName(String name) {
        for (int i = 0; i < properties.length; i++) {
            if (properties[i].getName().equals((name))) {
                return properties[i];
            }
        }
        return null;
    }

}
