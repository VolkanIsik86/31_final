package domain.squares;

import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

public abstract class OwnableSquare extends Square {

    private String type;
    private String color;
    private int price;
    private int rent;
    private String message;
    protected Player owner;

    public OwnableSquare(String name, int index, TxtReader landedOnTxt, int price,int rent,String type , String color) {
        super(name, index, landedOnTxt);
        this.price = price;
        this.rent = rent;
        this.type = type;
        this.color = color;
    }


    public String getType() { return type; }

    public int getPrice() {
        return price;
    }

    public int getRent() {return rent;}

    public Player getOwner() {
        return owner;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setRent(int rent) {this.rent = rent; }

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    //Pay rent logic: withdraws balance from player
    protected void payRent(Player p){
        p.withdraw(this.getRent());
    }

    protected void payPrice(Player p){
        p.withdraw(this.getPrice());
    }

    // get rent logic: Adds points to the owner of this square.
    protected void earnRent(){
        owner.deposit(this.getRent());
    }

    protected void purchase(Player p){
        this.setOwner(p);
        payPrice(p);
    }

    public String landedOn(Player player) {
        
        if (this.getOwner() != null && this.getOwner().equals(player)){

            message = "Owned by yourself "+this.type+" square";

        }

        //If property is not owned
        if (owner == null) {
            
            //todo hvorfor defineres meassage på ny her? -Mikkel
            message = "Not owned "+this.type+" square";

            //If player has the requested fonds
            if (player.attemptToPurchase(this)){
                purchase(player);
                message = message + "T";
            }

            //If player doesn't have the requested fonds
            else {
                player.setLost(true);
                player.setBalance(0);
                message = "Does not have fonds to buy";
            }
        }

        //If property is owned
        else{


            message = "Owned by another "+this.type+" square";

            //If player has the requested fonds
            if (player.attemptToPay(this.getRent())){
                payRent(player);
                earnRent();
            }

            //If player doesn't have the requested fonds
            else {
                player.setLost(true);
                player.setBalance(0);

                message = "Does not have fonds for rent";
            }

        }
        return message;
    }

}
