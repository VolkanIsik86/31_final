package domain;

import domain.chanceCards.ChanceCard;
import services.TxtReader;

public class ChanceDeckStub extends ChanceDeck {
    
    private int[] chanceCardSequence;
    private int counter = 0;
    
    public ChanceDeckStub(TxtReader cardsTxt, Board board, int[] chanceCardSequence){
        super(cardsTxt, board);
        this.chanceCardSequence = chanceCardSequence;
    }
    
    @Override
    public ChanceCard pullRandomChanceCard(){
        System.out.println(counter);
        return chanceCards[chanceCardSequence[counter++]];
        
    }
    
    
}
