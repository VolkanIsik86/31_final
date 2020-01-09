package domain;

import controllers.GUILogic;
import domain.squares.OwnableSquare;
import domain.squares.PropertySquare;
import domain.squares.Square;

public class Player {

    private final Account account;
    private final Piece piece;
    
    private final String name;
   
    private boolean lost = false;
    private boolean jail = false;
    private int lastRoll = 0;
    
    public Player(String name, int balance, Piece piece){
        this.name = name;
        account = new Account(balance);
        this.piece = piece;
    }

    public void setLastRoll(int lastRoll) {
        this.lastRoll = lastRoll;
    }
    
    public void setJail(boolean status){
        jail = status;
    }
    
    public boolean getJail(){
        return jail;
    }
    
    public boolean attemptToPurchase(OwnableSquare property){
        return property.getPrice() <= this.getBalance();
    }
    
    public boolean attemptToPay(int amount){
        return amount <= this.getBalance();
    }
    
    public boolean equals(Player player){
        
        return this.getName().equals(player.getName());
        
    }
    
    public void setLocation(Square newLocation){
        piece.setLocation(newLocation);
    }

    public boolean getLost() {
        return lost;
    }

    public void setLost(boolean status) {
        this.lost = status;
    }

    public Square getLocation(){
        return piece.getLocation();
    }

    public int getBalance(){
        return account.getBalance();
    }
    
    public void setBalance(int points){
        account.setBalance(points);
    }
    
    public void withdraw(int amount){
         account.withdraw(amount);
    }
    
    public void deposit(int amount){
        account.deposit(amount);
    }
    
    public String getName(){
        return name;
    }
    
    public Square getLastLocation(){
        return piece.getLastLocation();
    }
    
    public int getLastRoll(){
        return lastRoll;
    }
    
    
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", account=" + account +
                ", piece=" + piece +
                ", lost=" + lost +
                ", jail=" + jail +
                '}';
    }
}

