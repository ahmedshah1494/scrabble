package edu.cmu.qatar.cs214.hw4.core;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class Player.
 */
public class Player {
	
	/** The name. */
	private String name;
	
	/** The rack. */
	private Rack rack;
	
	/** The score. */
	private int score = 20;
	
	/**
	 * Instantiates a new player.
	 *
	 * @param name the name
	 */
	public Player(String name){
		this.name = name;
		this.rack = new Rack();
	}
	
	/**
	 * Gets the rack.
	 *
	 * @return the rack
	 */
	public Rack getRack(){
		return this.rack;
	}
	
	/**
	 * Gets the letter rack.
	 *
	 * @return the letter rack
	 */
	public ArrayList<String> getLetterRack(){
		return this.rack.getLettersList();
	}
	
	/**
	 * Gets the special rack.
	 *
	 * @return the special rack
	 */
	public ArrayList<String> getSpecialRack(){
		return this.rack.getSpecialList();
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Update score.
	 *
	 * @param score the score
	 */
	public void updateScore(int score){
		this.score += score;
	}
	
	/**
	 * Gets the score.
	 *
	 * @return the score
	 */
	public int getScore(){
		return this.score;
//		return 100;
	}
}
