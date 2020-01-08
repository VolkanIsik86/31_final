package domain.squares;


import controllers.GUILogic;
import domain.Player;
import services.TxtReader;

// Property square is the square that can be owned and other players, who land on it, pays to the owner.
public class PropertySquare extends Square {

    private int price;
    protected Player owner;
    
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
    
    public void landedOn(Player player) {
        
        
        if (this.getOwner() != null && this.getOwner().equals(player)){
            guiLogic.showMessage(landedOnTxt.getLine("Owned by yourself property square"));
            return;
        }
        
        //If property is not owned
        if (owner == null) {
    
            guiLogic.showMessage(landedOnTxt.getLine("Not owned property square"));
            
            //If player has the requested fonds
            if (player.attemptToPurchase(this)){
                purchase(player);
            }
            
            //If player doesn't have the requested fonds
            else {
                player.setLost(true);
                player.setBalance(0);
                guiLogic.showMessage(landedOnTxt.getLine("Does not have fonds to buy"));
            }
        }
        
        //If property is owned
        else{
            
            guiLogic.showMessage(landedOnTxt.getLine("Owned by another property square"));
    
            //If player has the requested fonds
            if (player.attemptToPay(this.getPrice())){
                payRent(player);
                earnRent();
                            }
    
            //If player doesn't have the requested fonds
            else {
                player.setLost(true);
                player.setBalance(0);
                guiLogic.showMessage(landedOnTxt.getLine("Does not have fonds for rent"));
                            }
            
        }
    }
}
