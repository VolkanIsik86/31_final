package domain.squares;

import domain.Player;
import services.TxtReader;

//All squares that belongs to monopoly junior board inherits from this abstract superclass
public abstract class Square {

    private final String name;
    private final int index;
    protected final TxtReader landedOnTxt;
    
    Square(String name, int index, TxtReader landedOnTxt) {
        this.name = name;
        this.index = index;
        this.landedOnTxt = landedOnTxt;
    }
    
    
    public String getName() {
        return name;
    }

    public int getIndex() {
        return index;
    }

    public int getValue(){
        return 0;
    }
    
    // landedOn is the key method of squares.
    // This polymorph method affects player with various effects.
    public abstract String landedOn(Player p);
    
    public TxtReader getLandedOnTxt(){
        return landedOnTxt;
    }
    
    @Override
    public String toString() {
        return "Square{" +
                "name='" + name + '\'' +
                ", index=" + index +
                '}';
    }
}
