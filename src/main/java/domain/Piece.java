package domain;

import domain.squares.Square;

public class Piece {

    private Square location;
    private Square lastLocation;

    public Piece(Square location) {
        this.location = location;
    }

    public Square getLocation() {
        return location;
    }

    public void setLocation(Square newLocation) {
        
        lastLocation = location;
        location = newLocation;
    }

    public Square getLastLocation() {
        return lastLocation;
    }

}
