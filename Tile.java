package test;

import java.util.Random;

public class Tile {
    public final char letter;
    public final int score;

    public Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + letter;
        result = prime * result + score;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        if (letter != other.letter)
            return false;
        if (score != other.score)
            return false;
        return true;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }

    public static class Bag{
        int[] tilesValue = {9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
        char[] tileType = {A, B, C, D, E, F, G, H, J, K, L, M, N, O, P, Q, R, S, T, U, V, W, X, Y, Z};

        int calculateSum(){
            int _sum = 0;
            for(int val : this.tilesValue){
                _sum += val;
            }
            return _sum;
        }

        char getRand(){
            int sum = calculateSum();
            if(sum == 0){
                return null;
            }
            Random random = new Random();
            int randomIndex = random.nextInt(tileType.length);
            if(this.tilesValue != 0){
                this.tilesValue[randomIndex]--;
            }
            return this.tileType[randomIndex];
        }

        char getTile(char someTile){
            for(int i = 0 ; i < tileType.length ; i++){
                if(someTile == tileType[i]){
                    tilesValue[i]--;
                }
            }
            return null;
        }
        
        void put(char someTile){
            for(int i = 0 ; i < tileType.length ; i++){
                if(tileType[i] == someTile){
                    tilesValue[i]++;
                }
                //Didnt finish here 
            }
        }
        
    }
}
