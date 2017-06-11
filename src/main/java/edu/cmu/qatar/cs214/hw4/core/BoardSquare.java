package edu.cmu.qatar.cs214.hw4.core;

/**
 * The Class BoardSquare.
 */
public class BoardSquare {
	
	/** The letter tile. */
	private LetterTile letterTile;
	
	/** The special tile. */
	private SpecialTile specialTile;
	
	/** The word multiplier. */
	private int wordMultiplier;
	
	/** The letter multiplier. */
	private int letterMultiplier;
	
	/** The multiplier used. */
	private boolean multiplierUsed = false;
	
	/**
	 * Instantiates a new board square.
	 *
	 * @param wordMultiplier the word multiplier
	 * @param letterMultiplier the letter multiplier
	 */
	public BoardSquare(int wordMultiplier, int letterMultiplier){
		this.wordMultiplier = wordMultiplier;
		this.letterMultiplier = letterMultiplier;
	}
	
	/**
	 * Checks if is vacant.
	 *
	 * @param p the p
	 * @return true, if is vacant
	 */
	public boolean isVacant(Player p){
		return (letterTile == null && 
			    (this.specialTile == null || 
			     !(this.specialTile.getOwner().equals(p))));
	}
	
	/**
	 * Adds the letter tile.
	 *
	 * @param t the t
	 * @param p the p
	 */
	public void addLetterTile(LetterTile t, Player p){
		this.letterTile = t;
	}
	
	/**
	 * Adds the special tile.
	 *
	 * @param t the t
	 * @param p the p
	 */
	public void addSpecialTile(SpecialTile t, Player p){		
		this.specialTile = t;
	}
	
	/**
	 * Gets the letter tile.
	 *
	 * @return the letter tile
	 */
	public LetterTile getLetterTile(){
		return this.letterTile;
	}
	
	/**
	 * Removes the letter tile.
	 */
	public void removeLetterTile(){
		this.letterTile = null;
	}
	
	/**
	 * Gets the special tile.
	 *
	 * @return the special tile
	 */
	public SpecialTile getSpecialTile(){
		return this.specialTile;
	}
	
	/**
	 * Removes the special tile.
	 */
	public void removeSpecialTile(){
		this.specialTile = null;
	}
	
	/**
	 * Gets the word multiplier.
	 *
	 * @return the word multiplier
	 */
	public int getWordMultiplier(){
		return this.wordMultiplier;
	}
	
	/**
	 * Gets the letter multiplier.
	 *
	 * @return the letter multiplier
	 */
	public int getLetterMultiplier(){
		return this.letterMultiplier;
	}
	
	/**
	 * Multiplier used.
	 *
	 * @return true, if successful
	 */
	public boolean multiplierUsed(){
		this.letterMultiplier = 1;
		this.wordMultiplier = 1;
		return this.multiplierUsed;
	}
	
	/**
	 * Sets the multiplier used.
	 */
	public void setMultiplierUsed(){
		this.multiplierUsed = true;
	}
	
	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore(){
		if (this.letterTile == null){
			return 0;
		}
		int score = this.letterTile.getValue() * this.letterMultiplier;
		this.multiplierUsed();
		return score;
	}
}
