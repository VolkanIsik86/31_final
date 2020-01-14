package domain.squares;

import domain.Board;
import domain.Player;
import services.TxtReader;

public abstract class OwnableSquare extends Square {

    private String type;
    private String color;
    private int price;
    private final int PLEDGE_VALUE;
    private String message;
    private int rent;
    protected Board board;
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

    public int getPLEDGE_VALUE() {
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

    public void setPrice(int price) {
        this.price = price;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public abstract boolean isRealEstate();

    public abstract int getHouseCount();

    //Pay rent logic: withdraws balance from player
    protected void payRent(Player p) {
        p.withdraw(rent);
    }

    protected void payPrice(Player p) {
        p.withdraw(this.getPrice());
    }

    // get rent logic: Adds points to the owner of this square.
    protected void earnRent() {
        owner.deposit(rent);
    }

    public void purchase(Player p) {
        this.setOwner(p);
        payPrice(p);
    }

    public int getValue(){
        int value = 0;
        value = this.price + value;
        return value;
    }

    public abstract String getInfo();

    public String landedOn(Player player) {

        if (this.getOwner() != null && this.getOwner().equals(player)) {

            message = "Owned by yourself " + this.type + " square";

        }

        //If property is not owned
        if (owner == null) {
            
            message = "Not owned " + this.type + " square";
            message = message + "T";

            //If player has the requested fonds
//            if (player.attemptToPurchase(this)){
//
//            }
//
//            //If player doesn't have the requested fonds
//            else {
//                message = "Does not have fonds to buy";
//            }
        }

        //If property is owned
        else {

            this.updateRent(player.getLastRoll());
            message = "Owned by another " + this.type + " square";

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
