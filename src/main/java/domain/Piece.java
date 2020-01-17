package domain;

import domain.squares.Square;

public class Piece {

    private Square location;
    private Square lastLocation;
    private static int pieceType = 0;

    public Piece(Square location) {
        this.location = location;

        //Every new piece gets a unique number
        pieceType++;
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
