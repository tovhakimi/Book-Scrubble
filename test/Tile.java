package test;

import java.util.Objects;
import java.util.Random;

//Change
public class Tile {
    private final char letter;
    private final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    public char getLetter(){
        return this.letter;
    }

    public int getScore(){
        return this.score;
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

   
    public static class Bag{
        private static Bag bag = null;
        private int[] tilesQuantity;
        private final int[] originialQuantity;
        private Tile[] tileType;

        private Bag(){
            originialQuantity = new int[]{9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1, 2, 1};
            System.arraycopy(originialQuantity, 0, tilesQuantity, 0, tilesQuantity.length);
            tileType = new Tile[]{
                new Tile('A', 1),
                new Tile('B', 3),
                new Tile('C', 3),
                new Tile('D', 2),
                new Tile('E', 1),
                new Tile('F', 4),
                new Tile('G', 2),
                new Tile('H', 4),
                new Tile('I', 1),
                new Tile('J', 8),
                new Tile('K', 5),
                new Tile('L', 1),
                new Tile('M', 3),
                new Tile('N', 1),
                new Tile('O', 1),
                new Tile('P', 3),
                new Tile('Q', 10),
                new Tile('R', 1),
                new Tile('S', 1),
                new Tile('T', 1),
                new Tile('U', 1),
                new Tile('V', 4),
                new Tile('W', 4),
                new Tile('X', 8),
                new Tile('Y', 4),
                new Tile('Z', 10)
            };
        }

        public int calculateSum(){
            int _sum = 0;
            for(int quantity : this.tilesQuantity){
                _sum += quantity;
            }
            return _sum;
        }

        public Tile getRand(){
            int sum = calculateSum();
            if(sum == 0){
                return null;
            }
            Random random = new Random();
            int randomIndex = random.nextInt(tileType.length);
            if(this.tilesQuantity[randomIndex] != 0){
                this.tilesQuantity[randomIndex]--;
                return tileType[randomIndex];          
            }
            return null;
        }

        public Tile getTile(char someTile){
            for(int i = 0 ; i < tileType.length ; i++){
                if(tileType[i].letter == someTile && tilesQuantity[i] > 0){
                    tilesQuantity[i]--;
                    return tileType[i];
                }
            }
            return null;
        }
        
        public void put(Tile someTile){
            for(int i = 0 ; i < tileType.length ; i++){
                if(someTile.letter == tileType[i].letter){
                    tilesQuantity[i]++;
                }
                if(tilesQuantity[i] > originialQuantity[i]){
                    tilesQuantity[i]--;
                }
            }
        }

        public int size(){
            return calculateSum();
        }

        public int[] getQuantities(){
            int[] clonedValueArray = tilesQuantity.clone();

            return clonedValueArray;
        }

        public static Bag getBag(){
            if(bag == null){
                bag = new Bag();
            }
            return bag;
        }
    } 
}
