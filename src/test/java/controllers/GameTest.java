package controllers;

public class GameTest {

    public static void main(String[] args) {

        new GameDriver(new int[]{
                
               2,2

        }, new int[]{3500, 30000, 30000}, new int[]{0,0,0}, new int[]{0, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18}).playGame();
    
//        Tester at huse fjernes når en grund auktioneres ved tab
//        new GameDriver(new int[]{
//
//                1,0,
//                1,0,
//                2,3,
//                1,1,1,0,
//                1,1,1,0,
//                2,1,
//                0,1,
//                18,19
//
//        }, new int[]{10500, 30000, 30000}, new int[]{0,0,0}, new int[]{0, 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18}).playGame();




//        //Demonstrer hvordan spillet slutter, ift. auktionering af grunde
//        new GameStub(new int[]{
//
//                3,3,3,3,3,2,1,1
//
//        }, new int[]{5200, 2500, 30000}, new int[]{0,0,0},new int[]{7,7,7,7,7,7,7,7,7,7,7}).playGame();

//        //Demonstrer hvordan en spillers grunde auktioneres når man taber
//        new GameStub(new int[]{
//
//                0,1, 1,2, 2,3, 2,3, 2,3, 2,2, 4,4
//
//        }, new int[]{30000, 20000, 7000}, new int[]{0, 0, 0}, new int[]{7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7}).playGame();

    }

}