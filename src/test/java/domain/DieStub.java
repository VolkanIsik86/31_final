package domain;

public class DieStub extends Die {
    
    private final int[] dieRolls;
    private int counter = 0;
    
    public DieStub(int[] dieRolls){
        this.dieRolls = dieRolls;
    }
    
    public int  getFaceValue(){
        return dieRolls[counter++];
    }
    
}
