package test;


public class Board {
    private static Board board = null;
    
    private Board(){
        board = {[][][][][][][][][][][][][][][]}
    }

    public static Board getBoard(){
        if(board == null){
            board = new Board();
        }
        return board;
    }
}
