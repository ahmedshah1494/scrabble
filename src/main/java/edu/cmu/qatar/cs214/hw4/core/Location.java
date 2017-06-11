package edu.cmu.qatar.cs214.hw4.core;

import javafx.util.Pair;

/**
 * The Class Location.
 */
public class Location extends Pair<Integer,Integer>{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new location.
	 *
	 * @param arg0 the arg0
	 * @param arg1 the arg1
	 */
	public Location(Integer arg0, Integer arg1) {
		super(arg0, arg1);
	}
	
	/**
	 * Gets the row.
	 *
	 * @return the row
	 */
	public int getRow(){
		return this.getKey();
	}
	
	/**
	 * Gets the col.
	 *
	 * @return the col
	 */
	public int getCol(){
		return this.getValue();
	}

}
