package edu.cmu.qatar.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

/**
 * The Class GameBoard.
 */
public class GameBoard {
	
	/** The board. */
	private BoardSquare[][] board;
	
	/** The size. */
	private int size = 15;
	
	/** The dict. */
	private Dictionary dict;
	
	/** The word locations. */
	private List<Pair<Location, Location>> wordLocations;
	
	/** The move count. */
	private int moveCount = 0;
	
	/** The score multiplier. */
	private int scoreMultiplier = 1;
	
	/**
	 * Instantiates a new game board.
	 *
	 * @param dict the dict
	 */
	public GameBoard(Dictionary dict) {
		this.dict = dict;
		this.board = new BoardSquare[15][15];
		this.wordLocations = new ArrayList<Pair<Location, Location>>();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (i % 7 == 0 && j % 7 == 0 && (j != i || i != 7)) {
					this.board[i][j] = new BoardSquare(3, 1);
				} else if (((i > 0 && i < 5) && (i == j || j == 14 - i))
						|| (i < 14 && i > 9) && (i == 14 - j || i == j)) {
					this.board[i][j] = new BoardSquare(2, 1);
				} else if (i % 4 == 1 && j % 4 == 1) {
					this.board[i][j] = new BoardSquare(1, 3);
				} else if ((Math.abs(i - 7) % 7 == 0 && (j == 3 || j == 11))
						|| (Math.abs(i - 7) == 5 && (j == 6 || j == 8))
						|| (Math.abs(i - 7) == 4 && (j % 7 == 0))
						|| (Math.abs(i - 7) == 1 && (j == 2 || j == 6 || j == 8 || j == 12))) {
					this.board[i][j] = new BoardSquare(1, 2);
				} else {
					this.board[i][j] = new BoardSquare(1, 1);
				}
			}
		}
	}
	
	/**
	 * Checks if is full.
	 *
	 * @param p the p
	 * @return true, if is full
	 */
	public boolean isFull(Player p){
		for(int i = 0 ; i < this.size ; i++){
			for(int j = 0 ; j < this.size ; j ++){
				if (this.board[i][j].isVacant(p)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * Checks if is vacant.
	 *
	 * @param p the p
	 * @param row the row
	 * @param col the col
	 * @return true, if is vacant
	 */
	public boolean isVacant(Player p, int row, int col) {
		return board[row][col].isVacant(p);
	}

	/**
	 * Checks if is vacant.
	 *
	 * @param p the p
	 * @param loc the loc
	 * @return true, if is vacant
	 */
	public boolean isVacant(Player p, Location loc) {
		return this.isVacant(p, loc.getRow(), loc.getCol());
	}
	
	/**
	 * Sets the score multiplier.
	 *
	 * @param mult the new score multiplier
	 */
	public void setScoreMultiplier(int mult){
		this.scoreMultiplier = mult;
	}

	/**
	 * Update board.
	 *
	 * @param m the m
	 * @return the int
	 */
	public int updateBoard(Move m) {
		ArrayList<Pair<LetterTile, Location>> letterTiles = m.getLetterTiles();
		ArrayList<Pair<SpecialTile, Location>> specialTiles = m
				.getSpecialTiles();
		ArrayList<Location> tileLocations = m.getTileLocations();
		int score = 0;
		int mult = 0;
		int startRow;
		int endRow;
		int startCol;
		int endCol;
		int row;
		int col;
		SpecialTile specialTile;

		for (Pair<LetterTile, Location> lT : letterTiles) {
			row = lT.getValue().getRow();
			col = lT.getValue().getCol();
			this.board[row][col].addLetterTile(lT.getKey(), m.getPlayer());
		}

		for (Pair<SpecialTile, Location> sT : specialTiles) {
			row = sT.getValue().getRow();
			col = sT.getValue().getCol();
			this.board[row][col].addSpecialTile(sT.getKey(), m.getPlayer());
		}

		for (Location l : tileLocations) {
			specialTile = this.getSquare(l.getRow(), l.getCol())
					.getSpecialTile();
			if (specialTile != null
					&& !specialTile.getOwner().equals(m.getPlayer())) {
				System.out.println("Special Tile Hit! at " + l.getRow()
						+ " " + l.getCol());
				specialTile.activate(l);
			}
		}
		for (Pair<Location, Location> p : this.wordLocations) {
			startRow = p.getKey().getRow();
			endRow = p.getValue().getRow();
			startCol = p.getKey().getCol();
			endCol = p.getValue().getCol();

			if (startRow == endRow) {
				for (int i = startCol; i < endCol + 1; i++) {
					if (this.board[i][startCol].getWordMultiplier() > 1) {
						mult += board[i][startCol].getWordMultiplier();
						this.board[i][startCol].multiplierUsed();
					}
					score += this.board[startRow][i].getScore();
				}
			}
			if (startCol == endCol) {
				for (int i = startRow; i < endRow + 1; i++) {
					if (this.board[i][startCol].getWordMultiplier() > 1) {
						mult += board[i][startCol].getWordMultiplier();
						this.board[i][startCol].multiplierUsed();
					}
					score += this.board[i][startCol].getScore();
				}
			}
		}

		if (mult > 0) {
			score *= mult;
		}
		score *= this.scoreMultiplier;
		this.scoreMultiplier = 1;
		
		if (letterTiles.size() >= 7){
			score += 50;
		}
		
		System.out.println("mult = " + mult);
		System.out.println(score);
		this.wordLocations.clear();
		this.moveCount += 1;
		return score;
	}

	/**
	 * Gets the letter tile at.
	 *
	 * @param row the row
	 * @param col the col
	 * @return the letter tile at
	 */
	public LetterTile getLetterTileAt(int row, int col) {
		return this.board[row][col].getLetterTile();
	}

	/**
	 * Gets the letter tile at.
	 *
	 * @param loc the loc
	 * @return the letter tile at
	 */
	public LetterTile getLetterTileAt(Location loc) {
		return this.getLetterTileAt(loc.getRow(), loc.getCol());
	}

	/**
	 * Gets the square.
	 *
	 * @param row the row
	 * @param col the col
	 * @return the square
	 */
	public BoardSquare getSquare(int row, int col) {
		return this.board[row][col];
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer str = new StringBuffer();
		str.append("|");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (this.board[i][j].getLetterTile() != null) {
					str.append(this.board[i][j].getLetterTile().getLetter() + " |");
				} else if (this.board[i][j].getLetterMultiplier() > 1) {
					str.append(this.board[i][j].getLetterMultiplier() + "L|");
				} else if (this.board[i][j].getWordMultiplier() > 1) {
					str.append(this.board[i][j].getWordMultiplier() + "W|");
				} else {
					str.append("  |");
				}
			}
			str.append("\n|");
		}
		return str.toString();
	}

	/**
	 * Lin search.
	 *
	 * @param l the l
	 * @param row the row
	 * @param col the col
	 * @return the letter tile
	 */
	private LetterTile linSearch(List<Pair<LetterTile, Location>> l, int row,
			int col) {
		for (Pair<LetterTile, Location> p : l) {
			if (p.getValue().getRow() == row && p.getValue().getCol() == col) {
				// System.out.println(row);
				return p.getKey();
			}
		}
		return null;
	}

	/**
	 * Check up.
	 *
	 * @param board the board
	 * @param row the row
	 * @param col the col
	 * @param endRow the end row
	 * @param words the words
	 * @param letterTileCount the letter tile count
	 * @return true, if successful
	 */
	private boolean checkUp(String[][] board, int row, int col, int endRow,
			List<String> words, int letterTileCount) {
		int startRow = row;
		Location start;
		Location end;
		boolean isAdjacent = false;
		while (row > 0 && board[row - 1][col].length() != 0) {
			row -= 1;
		}
		if (row != startRow) {
			isAdjacent = true;
		}
		start = new Location(row, col);
		StringBuffer buf = new StringBuffer();

		while (row < this.size && board[row][col].length() != 0) {
			buf.append(board[row][col]);
			row += 1;
		}
		String wrd = buf.toString();
		if (wrd.length() > 1) {
			end = new Location(row - 1, col);
			this.wordLocations.add(new Pair<Location, Location>(start, end));
			words.add(wrd);
			if (row - 1 > endRow || wrd.length() > letterTileCount) {
				isAdjacent =  true;
			}
		}
		return isAdjacent;
	}

	/**
	 * Check left.
	 *
	 * @param board the board
	 * @param row the row
	 * @param col the col
	 * @param endCol the end col
	 * @param words the words
	 * @param letterTileCount the letter tile count
	 * @return true, if successful
	 */
	private boolean checkLeft(String[][] board, int row, int col, int endCol,
			List<String> words, int letterTileCount) {
		int startCol = col;
		Location start;
		Location end;
		boolean isAdjacent = false;

		while (col > 0 && board[row][col - 1].length() != 0) {
			col -= 1;
		}
		if (col != startCol) {
			isAdjacent = true;
		}
		start = new Location(row, col);
		StringBuffer buf = new StringBuffer();
		while (col < this.size && board[row][col].length() != 0) {
			buf.append(board[row][col]);

			col += 1;
		}
		String wrd = buf.toString();
		if (wrd.length() > 1) {
			end = new Location(row, col - 1);
			this.wordLocations.add(new Pair<Location, Location>(start, end));
			words.add(wrd);
			if (col - 1 > endCol || wrd.length() > letterTileCount) {
				isAdjacent = true;
			}
		}
		return isAdjacent;
	}

	/**
	 * Validate move.
	 *
	 * @param m the m
	 * @return true, if successful
	 */
	public boolean validateMove(Move m) {
		List<String> words = new ArrayList<String>();
		ArrayList<Pair<LetterTile, Location>> letterTiles = m.getLetterTiles();
		ArrayList<Pair<SpecialTile, Location>> specialTiles = m
				.getSpecialTiles();
		String[][] boardCopy = this.stringBoard(m.getPlayer());
		boolean isAdjacent = this.moveCount == 0;

		if (this.moveCount == 0 && (this.linSearch(letterTiles, 7, 7) == null)) {
			return false;
		}

		if (letterTiles.isEmpty()) {
			return false;
		}

		for (Pair<LetterTile, Location> p : letterTiles) {
			if (!this.isVacant(m.getPlayer(), p.getValue())) {
				return false;
			}
		}

		for (Pair<SpecialTile, Location> p : specialTiles) {
			if (!this.isVacant(m.getPlayer(), p.getValue())) {
				return false;
			}
		}

		for (Pair<LetterTile, Location> lT : letterTiles) {
			boardCopy[lT.getValue().getRow()][lT.getValue().getCol()] = lT
					.getKey().getLetter();
		}

		int startRow;
		int startCol;
		boolean b1;
		boolean b2;

		for (int i = 0; i < letterTiles.size(); i++) {
			startRow = letterTiles.get(i).getValue().getRow();
			startCol = letterTiles.get(i).getValue().getCol();
			if (i == 0 || !m.isVerticle()) {
				b1 = this.checkUp(boardCopy, startRow, startCol,
								letterTiles.get(letterTiles.size() - 1)
										.getValue().getRow(), words, letterTiles.size());
				isAdjacent = isAdjacent || b1;
			}

			if (i == 0 || m.isVerticle()) {
				b2 = this.checkLeft(boardCopy, startRow, startCol,
								letterTiles.get(letterTiles.size() - 1)
										.getValue().getCol(), words, letterTiles.size());
				
				isAdjacent = isAdjacent || b2;
			}
		}

		System.out.println(words);
		if (words.size() == 0) {
			this.wordLocations.clear();
			return false;
		}

		for (String w : words) {
			if (!this.dict.isValidWord(w.toLowerCase())) {
				this.wordLocations.clear();
				return false;
			}
		}

		if (!isAdjacent) {
			this.wordLocations.clear();
		}
		
		System.out.println(isAdjacent);
		return isAdjacent;
	}

	/**
	 * String board.
	 *
	 * @param p the p
	 * @return the string[][]
	 */
	private String[][] stringBoard(Player p) {
		String[][] b = new String[this.size][this.size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!this.isVacant(p, i, j)) {
					b[i][j] = this.getLetterTileAt(i, j).getLetter();
				} else {
					b[i][j] = "";
				}
			}
		}
		return b;
	}
	
	/**
	 * Gets the string board.
	 *
	 * @param p the p
	 * @return the string board
	 */
	public String[][] getStringBoard(Player p){
		String[][] str = new String[15][15];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (!this.board[i][j].isVacant(p)){
					if (this.board[i][j].getLetterTile() != null){
						str[i][j] = this.board[i][j].getLetterTile().getLetter();
					}
					else{
						str[i][j] = this.board[i][j].getSpecialTile().getName();
					}
				}
				if (this.board[i][j].getLetterMultiplier() > 1) {
					str[i][j]= this.board[i][j].getLetterMultiplier() + "L";
				} else if (this.board[i][j].getWordMultiplier() > 1) {
					str[i][j]= this.board[i][j].getWordMultiplier() + "W";
				}
			}
		}
		return str;
	}
}
