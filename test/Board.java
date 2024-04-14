package test;

import java.util.ArrayList;
import java.util.Iterator;

public class Board {
    private static Board board = null;
    public int wordsCounter = 0; //Parameter that checks the word quantity inside the board
    private ArrayList<Word> newWords = new ArrayList<>();
    private Tile[][] gameBoard;
    
    private Board(){
        this.gameBoard = new Tile[15][15];
        // for(int i = 0 ; i < 15 ; i++){
        //     for(int j = 0 ; j < 15 ; j++){
        //         this.gameBoard[i][j] = null;
        //     }
        // }
    }

    public static Board getBoard(){
        if(board == null){
            board = new Board();
        }
        return board;
    }

    public Tile[][] getTiles(){
        Tile[][] gameStateTiles = new Tile[15][15];
        for(int i = 0 ; i < 15 ; i++){
            System.arraycopy(gameBoard[i], 0, gameStateTiles[i], 0, 15);
        }
        return gameStateTiles;
    }
    //Method that checks if the word is legal and not empty
    private boolean legalWord(Word word){
        boolean checkWord = false;
        for(Tile checkTile : word.getWordTile()){
            if(checkTile != null){
                checkWord = true;
                break;
            }
        }
        return checkWord;
    }

    public boolean boardLegal(Word word){
        if(!legalWord(word)){
            return false;
        }
        if(!wordInsideTheBoard(word)){
            return false;
        }
        if(!ifWordLinked(word) && wordsCounter > 0){
            return false;
        }
        if(requireReplacement(word)){
            return false;
        }
        if(!ifFirstWord(word)){
            return false;
        }
        return true;
    }

    public boolean ifFirstWord(Word word){
        if(wordsCounter > 0 && gameBoard[7][7] == null){
            return false;
        }
        else if(wordsCounter == 0){
            if((word.isVertical()) && gameBoard[7][7] == null && word.getCol() != 7 ||
                word.getCol() == 7 && word.getRow() < 7 && (word.getRow() + word.getWordTile().length) < 7){
                return false;
            }
            if((!word.isVertical()) && gameBoard[7][7] == null && word.getRow() != 7 ||
                word.getRow() == 7 && word.getCol() < 7 && (word.getCol() + word.getWordTile().length) < 7){
                return false;
            }
        }
        return true;
    }

    private boolean wordInsideTheBoard(Word word){
        if(word.getRow() > 14 || word.getRow() < 0 ||
           word.getCol() > 14 || word.getCol() < 0){
            return false;
        }
        // Check if all tiles of the word fit within the board based on orientation
        if (word.isVertical()) {
        // Check if the word extends beyond the board vertically
        return word.getRow() + word.getWordTile().length <= 15;
        } else {
        // Check if the word extends beyond the board horizontally
            return word.getCol() + word.getWordTile().length <= 15;
            }
        }

    public boolean ifWordLinked(Word word){
        // Check for existing words adjacent based on word orientation
        if (word.isVertical()) {
        // Check above the new word (unless it's the first row)
        if (word.getRow() > 0 && gameBoard[word.getRow() - 1][word.getCol()] != null) {
        // Found an existing tile above, check if it connects to the new word's first tile
            return isConnected(gameBoard[word.getRow() - 1][word.getCol()], word.getWordTile()[0]);
        }
        } else {
            // Check left of the new word (unless it's the first column)
            if (word.getCol() > 0 && gameBoard[word.getRow()][word.getCol() - 1] != null) {
            // Found an existing tile to the left, check if it connects to the new word's first tile
                return isConnected(gameBoard[word.getRow()][word.getCol() - 1], word.getWordTile()[0]);
            }
        }
        // No adjacent tiles found above/left (for vertical/horizontal respectively), 
        // so the word must connect to an existing word below/right
            return checkConnectedTilesBelowRight(word, gameBoard);
        }

        // Helper function to check if two tiles connect (letters touch)
        private boolean isConnected(Tile tile1, Tile tile2) {
            return tile1.getLetter() != tile2.getLetter();
        }

        // Helper function to check connected tiles below/right
        private boolean checkConnectedTilesBelowRight(Word word, Tile[][] tiles) {
            if (word.isVertical()) {
                // Check below each tile of the new word
                for (int i = 1; i < word.getWordTile().length; i++) {
                    int rowToCheck = word.getRow() + i;
                    // Check if the row is within board boundaries and there's a tile
                        if (rowToCheck < 15 && tiles[rowToCheck][word.getCol()] != null) {
                            // Found a tile below, check if it connects to the corresponding tile in the new word
                                if (isConnected(tiles[rowToCheck][word.getCol()], word.getWordTile()[i])) {
                                    return true;
                                }
                            } else {
                                // Reached the board edge or empty space below, word placement is invalid
                                return false;
                            }
                }
            } else {
                // Check right of each tile of the new word
                for (int j = 1; j < word.getWordTile().length; j++) {
                    int colToCheck = word.getCol() + j;
                    // Check if the column is within board boundaries and there's a tile
                        if (colToCheck < 15 && tiles[word.getRow()][colToCheck] != null) {
                        // Found a tile to the right, check if it connects to the corresponding tile in the new word
                            if (isConnected(tiles[word.getRow()][colToCheck], word.getWordTile()[j])) {
                                return true;
                            }
                            } else {
                                // Reached the board edge or empty space to the right, word placement is invalid
                                    return false;
                                }
                }
            }
        return false;
    }

    public boolean requireReplacement(Word word){
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();
        
        for(Tile checkTile : word.getWordTile()){
            if(checkTile != null && gameBoard[row][col] != null){
                return true;
            }
            if(vertical){
                row += 1;
            } else{
                col += 1;
            }
        }
        return false;
    }

    public boolean dictionaryLegal(Word word){
        return true;
    }

    public ArrayList<Word> getWords(Word word){
        if(!dictionaryLegal(word)){
            return newWords;
        }
        newWords.add(word);
        return newWords;
    }

    void RemoveCurrWords() {
		Iterator<Word> iterator = newWords.iterator();
		while (iterator.hasNext()) {
			Word w = iterator.next();
			iterator.remove();
		}
	}
    
    public int getScore(Word word){
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();
        int currentScore = 0;
        int wordBonus = 1;
        int totalWordScore = 0;


        for(int i = 0 ; i < word.getWordTile().length ; i++){
            if(row < 0 || row > 14 || col < 0 || col > 14){
                return 0;
            }
            if(gameBoard[row][col] != null){
                currentScore += (gameBoard[row][col].getScore() * letterBonus(row, col));
            }
            wordBonus += checkWordBonus(row, col);
            if(vertical){
                row += 1;
            } else{
                col += 1;
            }
        } 
        totalWordScore += (currentScore * wordBonus);
		return totalWordScore;
    }

    public int checkWordBonus(int row, int col){
        if (row < 0 || row > 14 || col < 0 || col > 14) {
			return 0; // the word is illegal
		} else if (row == 7 && col == 7 && wordsCounter == 1) { // star bonus for the first word
			return 2;
		} else if ((row == 0 && col == 0) || (row == 0 && col == 7) || (row == 0 && col == 14) ||
				(row == 7 && col == 0) || (row == 7 && col == 14) ||
				(row == 14 && col == 0) || (row == 14 && col == 7) || (row == 14 && col == 14)) {
			return 3; // 3 word bonus for a red tile
		} else if ((row == 1 && col == 1) || (row == 1 && col == 13)
				|| (row == 2 && col == 2) || (row == 2 && col == 12)
				|| (row == 3 && col == 3) || (row == 3 && col == 11)
				|| (row == 4 && col == 4) || (row == 4 && col == 10)
				|| (row == 10 && col == 4) || (row == 10 && col == 10)
				|| (row == 11 && col == 3) || (row == 11 && col == 11)
				|| (row == 12 && col == 2) || (row == 12 && col == 12)
				|| (row == 13 && col == 1) || (row == 13 && col == 13)) {

			return 2; // word bonus for a yellow tile
		}

		return 1; // no bonus
    }

    public int letterBonus(int row, int col){
        if (row < 0 || row > 14 || col < 0 || col > 14) {
			return 0; // word is illegal
		} else if ((row == 0 && col == 3) || (row == 0 && col == 11)
				|| (row == 2 && col == 6) || (row == 2 && col == 8)
				|| (row == 3 && col == 0) || (row == 3 && col == 7) || (row == 3 && col == 14)
				|| (row == 6 && col == 2) || (row == 6 && col == 6) || (row == 6 && col == 8) || (row == 6 && col == 12)
				|| (row == 7 && col == 3) || (row == 7 && col == 11)
				|| (row == 8 && col == 2) || (row == 8 && col == 6) || (row == 8 && col == 8) || (row == 8 && col == 12)
				|| (row == 11 && col == 0) || (row == 11 && col == 7) || (row == 11 && col == 14)
				|| (row == 12 && col == 6) || (row == 12 && col == 8)
				|| (row == 14 && col == 3) || (row == 14 && col == 11)) {

			return 2; // double the letter score
		} else if ((row == 1 && col == 5) || (row == 1 && col == 9)
				|| (row == 5 && col == 2) || (row == 5 && col == 5) || (row == 5 && col == 9) || (row == 5 && col == 13)
				|| (row == 9 && col == 2) || (row == 9 && col == 5) || (row == 9 && col == 9) || (row == 9 && col == 13)
				|| (row == 13 && col == 5) || (row == 13 && col == 9)) {

			return 3; // triple the letter score
		}
		return 1; // no bonus
    }

    public int tryPlaceWord(Word word){
        int wordPutScore = 0;
        if(!boardLegal(word)){
            return 0;
        } else{
            if(!dictionaryLegal(word)){
                return 0;
            }
        }
        wordsCounter += 1;
        newWords = getWords(word);
        int row = word.getRow();
        int col = word.getCol();
        boolean vertical = word.isVertical();
        Tile[] wordTiles = word.getWordTile();
        for(Tile wordTile : wordTiles){
            if(row < 0 || row > 14 || col < 0 || col > 14){
                return 0;
            }
            if(gameBoard[row][col] == null){
                gameBoard[row][col] = wordTile;//Insert the word into the board
            }
            if (wordTile != null && (!vertical) && ((row > 0 && gameBoard[row - 1][col] != null)
						|| ((row < 14) && (gameBoard[row + 1][col] != null)))) {
                        verticalWordCreate(word, row, col); // create a new word to the dictionary and calc the new score
				}
				if (wordTile != null && (vertical) && ((col > 0 && gameBoard[row][col - 1] != null)
						|| ((col < 14) && (gameBoard[row][col + 1] != null)))) {
                        nonVerticalWordCreate(word, row, col);
				}

				if (vertical) {
					row += 1;
				} else {
					col += 1;
				}
        }
        return wordPutScore;
    }

    private void verticalWordCreate(Word word, int row, int col){
        int wordSize = 0;
        int tempRow = row;
        int startingIndex = 0;

        if (row < 0 || row > 14 || col < 0 || col > 14) {
			return;
		}
        while(tempRow > 0 && gameBoard[tempRow][col] != null){
            if(gameBoard[row - 1][col] != null){
                tempRow -= 1;
                wordSize += 1;
            } else{
                break;
            }
        }
        startingIndex = tempRow;
        wordSize = 0;

        while ((tempRow < 14) && gameBoard[tempRow][col] != null) {
			if (row < 0 || row > 14 || col < 0 || col > 14 || tempRow < 0) {
				return;
			}
			if (gameBoard[tempRow + 1][col] != null) {
				tempRow += 1;
				wordSize += 1;
			} else {
				break;
			}
		}

        tempRow = startingIndex;
        Tile[] newWordTiles = new Tile[wordSize + 1];
        for(int i = 0 ; i < newWordTiles.length ; i++){
            if(tempRow < 14){
                newWordTiles[i] = gameBoard[tempRow][col];
            }
            tempRow += 1;
        } 

        Word _newWord = new Word(newWordTiles,startingIndex,col,true);
        newWords = getWords(_newWord);
    }

    private void nonVerticalWordCreate(Word word, int row, int col){
        int wordSize = 0;
        int tempCol = col;
        int startingIndex = 0;

        if (row < 0 || row > 14 || col < 0 || col > 14) {
			return;
		}
        while(tempCol > 0 && gameBoard[row][tempCol] != null){
            if (tempCol < 0 || tempCol > 15) {
				break;
			}
            if(gameBoard[row][col - 1] != null){
                tempCol -= 1;
                wordSize += 1;
            } else{
                break;
            }
        }
        startingIndex = tempCol;
        wordSize = 0;

        while ((tempCol < 14 && tempCol >= 0) && gameBoard[row][tempCol] != null) {
			if (row < 0 || row > 14 || col < 0 || col > 14) {
				return;
			}
			if (gameBoard[row][tempCol + 1] != null) {
				tempCol += 1;
				wordSize += 1;
			} else {
				break;
			}
		}

        tempCol = startingIndex;
		Tile[] newWord = new Tile[wordSize + 1];
		for (int j = 0; j < newWord.length; j++) {
			newWord[j] = gameBoard[row][tempCol];
			tempCol += 1;

		}
		Word w = new Word(newWord, row, col, false);
		newWords = getWords(w);
    }
}
