package domain;

//Creates and maintains a list of players

import domain.squares.Square;

public class PlayerList {
    
    private Player[] players = new Player[0];
    private final Square startSquare;

    public PlayerList(Square startSquare){
        this.startSquare = startSquare;
    }
    
    public void addPlayer(String name, int startBalance){
        
        // Increase size of player-array by 1
        Player[] temp = new Player[players.length+1];
        for (int i = 0; i < players.length; i++) {
            temp[i] = players[i];
        }
        players = temp;
        
        //Create player's piece
        Piece piece = new Piece(startSquare);
        
        //Create and add new player to array
        players[players.length-1] = new Player(name, startBalance, piece);
        
    }

    
    public Player getPlayer(int index){
        return players[index];
    }
    
    public int NumberOfPlayers(){
        return players.length;
    }

    public Player[] getPlayers(){
        return players;
    }
    
    //Removes the player with that exact name on the playerlist.
    //If the player is not on the list, the first player is removed.
    public void removePlayer(Player player){
        
        int indexToRemove = 0;
        Player[] tempPlayers = new Player[players.length-1];
        
        for (int i = 0; i < players.length; i++) {

            if(players[i].equals(player)){
                indexToRemove = i;
                break;
            }
        }
        
        int i = 0;
        int j = i;
       
        for (i = 0; i < players.length; i++) {
            
            if(i != indexToRemove){
                tempPlayers[j] = players[i];
                j++;
            }
        }
        
        players = tempPlayers;
    }
    
}
