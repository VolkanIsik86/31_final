package controllers;

import domain.Player;
import domain.squares.OwnableSquare;
import domain.squares.PropertySquare;
import gui_fields.GUI_Car;
import gui_fields.GUI_Field;
import gui_fields.GUI_Player;
import gui_fields.GUI_Street;
import gui_main.GUI;
import services.TxtReader;

import java.awt.*;


public class GUILogic {

    private int delay = 200;
    private final int N_FIELDS = 40;
    protected GUI_Field[] fields;
    protected GUI gui;
    protected String[] names = new String[0];
    protected GUI_Player[] guiPlayers = new GUI_Player[0];
    protected final Color[] carcolor = {Color.RED, Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW, Color.WHITE};
    protected final TxtReader guiTxt;

    public GUILogic(TxtReader squaresTxt, TxtReader guiTxt, int startbalance) {
        this.guiTxt = guiTxt;
        makeBoard(squaresTxt);
        makeUsers(startbalance);
    }

    /**
     * Creates an array of fields for the game and initialize it.
     *
     * @param squaresTxt loads in the square name for each square in the array
     */
    private void makeBoard(TxtReader squaresTxt) {
        gui = new GUI();
        fields = gui.getFields();
        for (int i = 0; i < N_FIELDS; i++) {
            //Generates an array for each line in .txt with strings.
            String[] tempField = squaresTxt.getLine("" + i).split("-");

            //Writes titel of the field.

            fields[i].setSubText(tempField[3]);
            if (fields[i].getSubText().equals("0"))
                fields[i].setSubText(tempField[1]);
        }
    }

    /**
     * Adds player to the Graphical User Interface (GUI).
     *
     * @param numberofPlayers Adds quantity of player into the GUI.
     * @param startbalance    Adds the startbalance to the add player method
     */
    protected void addPlayers(int numberofPlayers, int startbalance) {

        //Does the same for all player that is added into the game.
        for (int i = 0; i < numberofPlayers; i++) {

            //Extend players with 1 quantity.
            String[] temp = new String[names.length + 1];

            for (int j = 0; j < names.length; j++) {
                temp[j] = names[j];
            }
            names = temp;

            //Asks player to write their name.
            String name = gui.getUserString(guiTxt.getLine("Enter name"));

            while (name.length() >= 8 || name.length() <= 1) {
                name = gui.getUserString(guiTxt.getLine("length"));
            }
            for (String samename : names) {
                boolean sameNameTest = true;
                while (sameNameTest) {
                    if (name.equals(samename)) {
                        name = gui.getUserString(guiTxt.getLine("Already in use"));
                    } else
                        sameNameTest = false;

                }
            }
            //Crates an array of player names.

            names[i] = name;

            //Constructs figures for the players that can move on the game board. (Inspired From The teacher Daniel Kolditz Rubin-Grøn in class demonstration.)
            GUI_Car car = new GUI_Car(carcolor[i], carcolor[i], GUI_Car.Type.values()[1], GUI_Car.Pattern.values()[i]);

            // Constructs a player.
            GUI_Player player = new GUI_Player(name, startbalance, car);

            //Adds player to Player array.
            GUI_Player[] PArray = new GUI_Player[guiPlayers.length + 1];
            for (int j = 0; j < guiPlayers.length; j++) {
                PArray[j] = guiPlayers[j];
            }
            guiPlayers = PArray;
            guiPlayers[i] = player;

            //Adds player on to the board.
            gui.addPlayer(player);

            //Places the figures to start point.
            fields[0].setCar(player, true);
        }
    }

    /**
     * Define and creates players for the game and uses addPlayer method number of players.
     *
     * @param startbalance Adds the specified startbalance to the new
     */
    protected void makeUsers(int startbalance) {

        String nrPlayers = gui.getUserSelection(guiTxt.getLine("player numbers"), "3", "4", "5", "6");
        int NumberOfPlayers = Integer.parseInt(nrPlayers);

        addPlayers(NumberOfPlayers, startbalance);
    }

    /**
     * Moves players figure around the board.
     *
     * @param player Figure of this player will be moved.
     * @param moves  Count of fields that figure moves(face value of dice).
     */
    public void movePiece(Player player, int moves) {

        int currentField = player.getLastLocation().getIndex();
        GUI_Player guiPlayer = getGUIPlayer(player);
        int movesDone = 0; //Bruges til at holde styr på antal moves udført
        if (moves != 0) {

            //Controls figure position + move and board length.
            //Tells if the play passes the finish line, depending on "N_FIELDS"(40).
            if (currentField + moves >= N_FIELDS) {

                //Runs fields until the start  point.
                for (int i = 1; currentField + i < N_FIELDS; i++) {
                    moveRest(guiPlayer, currentField, i);
                    movesDone++;
                    sleep(delay);

                }
                //Pays the player 4000 for passing "Start"
                currentField = passStart(guiPlayer);
                passedStart(player);
                movesDone++;
                sleep(delay);

            }

            //Run figure and controls moves done.
            for (int i = 0; i + movesDone < moves; i++) {
                currentField = moveOnce(guiPlayer, currentField);
                sleep(delay);
            }

        } else {
            fields[0].setCar(guiPlayer, true);
        }
    }

    /**
     * Deposits 4000 into players balance.
     *
     * @param player Player figure needs to be moved.
     */
    public void passedStart(Player player) {
        int PASSEDSTART = 4000;
        player.deposit(PASSEDSTART);
        setPlayerBalance(player);
    }

    /**
     * Moves player to the end field of the board.
     *
     * @param player    Player figure needs to be moved.
     * @param field     Field number that figure stands on.
     * @param increment Counts moves that player has done.
     */
    private void moveRest(GUI_Player player, int field, int increment) {
        fields[field + increment - 1].setCar(player, false);
        fields[field + increment].setCar(player, true);
    }

    /**
     * Moves players figure. Remove figure from old location to new location.
     *
     * @param player Player figure needs to be moved.
     * @param field  Field number that figure stands on.
     * @return Field that players last position
     */
    private int moveOnce(GUI_Player player, int field) {
        fields[field].setCar(player, false);
        fields[field + 1].setCar(player, true);
        field = field + 1;
        return field;
    }

    /**
     * Places players figure to start point.
     *
     * @param player Player figure needs to be moved.
     * @return Field that players last position
     */
    private int passStart(GUI_Player player) {
        int currentField;
        fields[N_FIELDS - 1].setCar(player, false);
        currentField = 0;
        fields[currentField].setCar(player, true);
        return currentField;
    }

    /**
     * Places players figure to jail.
     *
     * @param player moves the current player to jail
     */
    public void moveToJail(Player player) {

        GUI_Player guiPlayer = getGUIPlayer(player);

        //Remove player from current field
        fields[player.getLastLocation().getIndex()].setCar(guiPlayer, false);

        //Place player on jail
        fields[10].setCar(guiPlayer, true);
    }

    /**
     * Removes player piece from all squares, then gets the square the player is standing on and places the piece on that square
     * @param player
     */
    public void updatePlayerLocation(Player player){
        GUI_Player guiPlayer = getGUIPlayer(player);

        for(int i = 0; i < N_FIELDS; i++){
            fields[i].setCar(guiPlayer, false);
        }
        fields[player.getLocation().getIndex()].setCar((guiPlayer),true);
    }


    /**
     * Changes border color and writes player name of a field.
     *
     * @param player New owner of the field.
     */
    public void setSquareOwner(Player player) {
        fields[player.getLocation().getIndex()].setSubText(player.getName());
        Color playercolor = getGUIPlayer(player).getCar().getPrimaryColor();
        if (player.getLocation() instanceof PropertySquare) {
            try {
                ((GUI_Street) fields[player.getLocation().getIndex()]).setBorder(playercolor, Color.black);
            } catch (ClassCastException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Changes border color and writes player name of a field.
     *
     * @param ownableSquare Uses an OwnableSquare to manage colors and text.
     */
    public void setSquareAuction(OwnableSquare ownableSquare) {
        fields[ownableSquare.getIndex()].setSubText(ownableSquare.getOwner().getName());
        Color playercolor = getGUIPlayer(ownableSquare.getOwner()).getCar().getPrimaryColor();
        if (ownableSquare.isRealEstate()) {
            try {
                ((GUI_Street) fields[ownableSquare.getIndex()]).setBorder(playercolor, Color.black);
            } catch (ClassCastException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Synchronize backend player with the GUI player.
     *
     * @param player Backend player that need to be initialized.
     * @return NULL
     */
    private GUI_Player getGUIPlayer(Player player) {

        String playerName = player.getName();

        //For all GUIPlayers
        for (GUI_Player guiPlayer : guiPlayers) {

            //If names match
            if (guiPlayer.getName().equals(playerName)) {
                return guiPlayer;
            }
        }

        return null;
    }

    /**
     * Displays Die on the GUI.
     *
     * @param faceValue  facevalue of die one
     * @param faceValue2 face value of die two
     */
    public void displayDie(int faceValue, int faceValue2) {
        gui.setDice(faceValue, faceValue2);
    }

    /**
     * Shows chance cards middle og the board.
     *
     * @param txt is the chance card description that is showed.
     */
    public void showChanceCard(String txt) {
        gui.displayChanceCard(txt);
    }

    /**
     * Names of all players who is created.
     *
     * @return names of the players.
     */
    public String[] getPlayerNames() {
        return names;
    }

    /**
     * Sleep time is initialized for more understandable game.(Stops figures to teleport.)
     *
     * @param n Sleep time in miliseconds.
     */
    private void sleep(long n) {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Configures players balances for the game.
     *
     * @param player whos balance will be configured.
     */
    public void setPlayerBalance(Player player) {
        GUI_Player guiPlayer = getGUIPlayer(player);
        assert guiPlayer != null;
        guiPlayer.setBalance(player.getBalance());
    }

    /**
     * Displays message on GUI.
     *
     * @param message is the message that will be showed.
     */
    public void showMessage(String message) {
        gui.showMessage(message);
    }

    public void close() {
        gui.close();
    }

    public String getUserButtonPressed(String msg, String... buttons) {
        return gui.getUserButtonPressed(msg, buttons);
    }

    public String getUserSelection(String msg, String... options) {
        return gui.getUserSelection(msg, options);
    }

    public String getUserString(String input) {
        return gui.getUserString(input);
    }

    /**
     * Updates the house, if user buys more than 4.
     *
     * @param square which square need to be updated visually
     * @param houses Needs the variable, to check if they need a hotel instead of 4 houses
     */
    public void updateHouses(int square, int houses) {
        try {
            if (houses < 5) {
                ((GUI_Street) fields[square]).setHouses(houses);
            } else {
                ((GUI_Street) fields[square]).setHotel(true);
            }

        } catch (ClassCastException e) {
            System.out.println(e);
        }

    }

    /**
     * Moves the player to the correct location on the GUI
     *
     * @param player Which player needs to be moved
     * @param index  Moves the player to the correct index on the board
     */
    protected void placePlayer(Player player, int index) {
        fields[player.getLocation().getIndex()].setCar(getGUIPlayer(player), false);
        fields[index].setCar(getGUIPlayer(player), true);
    }

    public void deletePlayer(Player player) {
        fields[player.getLocation().getIndex()].setCar(getGUIPlayer(player), false);
    }

    public void setDelay(int newDelay) {
        delay = newDelay;
    }

}







