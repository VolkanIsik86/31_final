package systemtest;

import controllers.GameStub;

public class TC12_jail {
    
    public static void main(String[] args) {
        
        //Tester de forskellige måder at komme ind og ud af jail
        new GameStub(new int[]{
                
                3,3,3,3,3,3,
                1,-1,1,-1,
                10, 10,
                1,2,
                1,-1,1,-1,
                1,2,
                1,-1,1,-1,
                1,2,
                9,11,
                1,-1,1,-1,
                1,1,
                10,10,
                1,2,
                1,-1,1,-1,
                1,2,
                1,-1,1,-1,
                1,2,
        
        }, new int[]{7500, 30000, 30000}, new int[]{0, 0, 0}, new int[]{1,2,3,4,5,6}).playGame();
    }
    
//            3,3,3,3,3,3, Man kommer i fængsel ved at slå 3 ens
//            1,-1,1,-1, De andre spillere springes over
//            10, 10, Spilleren køber sig ud slår to ens og kommer i fængsel og får derfor sit første kast med det samme
//            1,2, Spilleren slår med terningerne men kommer ikke ud
//            1,-1,1,-1, De andre spillere springes over
//            1,2, Spilleren slår med terningerne men kommer ikke ud
//            1,-1,1,-1, De andre spillere springes over
//            1,2 Spilleren slår med terningerne men kommer ikke ud og skal derfor betale for at komme ud og må herefter tage sin tur
//            9,11, Spilleren kommer i fængsel uden at slå to ens, og får derfor ikke et sit første kast med det samme
//            1,-1,1,-1, De andre spillere springes over
//            1,1, Spilleren vælger at slå terningerne og kommer ud og må tage sin tur
//            10,10, Spilleren slår to ens og kommer i fængsel og får derfor sit første kast med det samme
//            1,2,Spilleren slår med terningerne men kommer ikke ud
//            1,-1,1,-1,De andre spillere springes over
//            1,2, Spilleren slår med terningerne men kommer ikke ud
//            1,-1,1,-1, De andre spillere springes over
//            1,2, Spilleren slår med terningerne men kommer ikke ud, da spilleren ikke har råd til at betale, har han tabt

}
