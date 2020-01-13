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
    private int attemptsToGetOutOfJail = 0;
    
    public Player(String name, int balance, Piece piece){
        this.name = name;
        account = new Account(balance);
        this.piece = piece;
    }

    public void setAttemptsToGetOutOfJail(int value){
        attemptsToGetOutOfJail = value;
    }
    
    public int getAttemptsToGetOutOfJail(){
        return attemptsToGetOutOfJail;
    }
    
    public void incrementAttemptsToGetOutOfJail(){
        attemptsToGetOutOfJail = attemptsToGetOutOfJail+1;
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

        if (property.getPrice() <= this.getBalance()) {
            property.purchase(this);
            return true;
        }
        else{
            setLost(true);
            setBalance(0);
            return false;
        }
    }
    
    public boolean attemptToPay(int amount){
        if(amount <= this.getBalance()){
            return true;
        }
        else{
            setLost(true);
            setBalance(0);
            return false;
        }

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

    public int getLocationPrice(OwnableSquare property){
        return property.getPrice();

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

