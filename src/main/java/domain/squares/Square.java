package domain.squares;

import controllers.GUILogic;
import domain.Board;
import domain.Player;
import services.TxtReader;

//All squares that belongs to monopoly junior board inherits from this abstract superclass
public abstract class Square {

    private final String name;
    private Player owner;
    private int tax;
    private final int index;
    private Board board;
    protected final TxtReader landedOnTxt;
    
    Square(String name, int index, TxtReader landedOnTxt) {
        this.name = name;
        this.index = index;
        this.landedOnTxt = landedOnTxt;
    }
    
    

    public String getName() {
        return name;
    }

    public Player getOwner(){return owner;};

    public void setTax(int tax) {this.tax = tax;}

    public void payTax(Player p){p.withdraw(tax);}

    public int getIndex() {
        return index;
    }
    
    // landedOn is the key method of squares.
    // This polymorph method affects player with various effects.
    public abstract String landedOn(Player p);
    
    @Override
    public String toString() {
        return "Square{" +
                "name='" + name + '\'' +
                ", index=" + index +
                ", board=" + board +
                '}';
    }
}
