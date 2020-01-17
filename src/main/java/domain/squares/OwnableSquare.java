package domain.squares;

import domain.Board;
import domain.Player;
import services.TxtReader;

public abstract class OwnableSquare extends Square {

    private final String type;
    private final String color;
    private final int price;
    private final int PLEDGE_VALUE;
    private int rent;
    protected final Board board;
    protected Player owner;

    public OwnableSquare(String name, int index, TxtReader landedOnTxt, int price, String type, String color, Board board) {
        super(name, index, landedOnTxt);
        this.price = price;
        this.type = type;
        this.color = color;
        this.PLEDGE_VALUE = price / 2;
        this.board = board;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int newRent) {
        rent = newRent;
    }

    public int getPledge_Value() {
        return PLEDGE_VALUE;
    }

    public String getType() {
        return type;
    }

    public int getPrice() {
        return price;
    }

    public abstract void updateRent(int lastRoll);

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getColor() {
        return color;
    }

    public abstract boolean isRealEstate();


    public abstract int getHouseCount();

    //Pay rent logic: withdraws balance from player
    protected void payRent(Player p) {
        p.withdraw(rent);
    }


    //Pays the amount for a given property
    protected void payPrice(Player p) {
        p.withdraw(this.getPrice());
    }

    // get rent logic: Adds points to the owner of this square.
    protected void earnRent() {
        owner.deposit(rent);
    }

    /**
     * Sets the owner of the property and withdraws price for property from player
     * @param p
     */
    public void purchase(Player p) {
        this.setOwner(p);
        payPrice(p);
    }

    public int getValue() {
        int value = 0;
        value = this.price + value;
        return value;
    }

    public abstract String getInfo();

    /**
     * landedOn() is called everytime a player stops at a square.
     * @param player
     * @return Returns a message that turnLogic can use to figure out what action to take
     */

    public String landedOn(Player player) {

        String message;
        if (this.getOwner() != null && this.getOwner().equals(player)) {

            message = "Owned by yourself " + this.type + " square"+" "+"o";

        }

        //If property is not owned
        else if (owner == null) {

            message = "Not owned " + this.type + " square";
            message = message + " N";
        }

        else if (owner.getJail()){
            message = "Owner is in jail" + " j";
        }

        //If property is owned
        else {

            this.updateRent(player.getLastRoll());

            message = "Owned by another " + this.type + " square" +" " + "O";

            //If player has the requested fonds
            if (player.attemptToPay(this.getRent())) {
                payRent(player);
                earnRent();
            }

            //If player doesn't have the requested fonds
            else {
                player.setBalance(0);
                message = "Does not have fonds for rent";
            }

        }
        return message;
    }

}
