package test;


public class Word {
    private Tile[] wordTile;
    private int row;
    private int col;
    private boolean vertical = false;

    public Word(Tile[] wordTile, int row, int col, boolean vertical) {
        this.wordTile = wordTile;
        this.row = row;
        this.col = col;
        this.vertical = vertical;
    }

    public Tile[] getWordTile() {
        return wordTile;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public boolean isVertical() {
        return vertical;
    }

    @Override
    public boolean equals(Object obj) {
        // TODO Auto-generated method stub
        return super.equals(obj);
    }
}
