package domain.squares;


import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

// Property square is the square that can be owned and other players, who land on it, pays to the owner.
public class PropertySquare extends Square {

    private int price;
    protected Player owner;
    private String message;
    
    public PropertySquare(String name, int index, GUILogic guiLogic, TxtReader landedOnTxt, int price) {
        super(name, index, guiLogic, landedOnTxt);
        this.price = price;
    }



    public int getPrice() {
        return price;
    }

    public Player getOwner() {
        return owner;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    
    //Pay rent logic: withdraws balance from player
    protected void payRent(Player p){
        p.withdraw(this.getPrice());
    }

    // get rent logic: Adds points to the owner of this square.
    protected void earnRent(){
        owner.deposit(this.getPrice());
    }
    
    protected void purchase(Player p){
        this.setOwner(p);
        payRent(p);
    }
    
    public String landedOn(Player player) {
        
        
        if (this.getOwner() != null && this.getOwner().equals(player)){

            message = "Owned by yourself property square";

        }
        
        //If property is not owned
        if (owner == null) {

            message = "Not owned property square";
            
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
            

            message = "Owned by another property square";
    
            //If player has the requested fonds
            if (player.attemptToPay(this.getPrice())){
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
