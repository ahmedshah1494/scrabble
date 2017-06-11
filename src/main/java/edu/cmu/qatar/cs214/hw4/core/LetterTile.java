package edu.cmu.qatar.cs214.hw4.core;

/**
 * The Class LetterTile.
 */
public class LetterTile{
	
	/** The letter. */
	private String letter;
	
	/** The value. */
	private int value;
	
	/**
	 * Instantiates a new letter tile.
	 *
	 * @param letter the letter
	 * @param value the value
	 */
	public LetterTile(String letter, int value){
		this.letter = letter;
		this.value = value;
	}
	
	/**
	 * Gets the letter.
	 *
	 * @return the letter
	 */
	public String getLetter(){
		return this.letter;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public int getValue(){
		return this.value;
	}
}
