/*
 * 
 */
package edu.cmu.qatar.cs214.hw4.gui;

import java.util.ArrayList;


/**
 * The listener interface for receiving gameChange events.
 * The class that is interested in processing a gameChange
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addGameChangeListener<code> method. When
 * the gameChange event occurs, that object's appropriate
 * method is invoked.
 *
 * @see GameChangeEvent
 */
public interface GameChangeListener {

	/**
	 * Notify tile placed.
	 *
	 * @param symbol the symbol
	 * @param x the x
	 * @param y the y
	 */
	void notifyTilePlaced(String symbol, int x, int y);

	/**
	 * Notify rack changed.
	 *
	 * @param letterRack the letter rack
	 * @param specialRack the special rack
	 */
	void notifyRackChanged(ArrayList<String> letterRack,
			ArrayList<String> specialRack);

	/**
	 * Notify score changed.
	 *
	 * @param player the player
	 * @param score the score
	 */
	void notifyScoreChanged(String player, int score);

	/**
	 * Notify player changed.
	 *
	 * @param name the name
	 */
	void notifyPlayerChanged(String name);

	/**
	 * Notify move made.
	 */
	void notifyMoveMade();

	/**
	 * Notify board changed.
	 *
	 * @param board the board
	 */
	void notifyBoardChanged(String[][] board);

	/**
	 * Notify winner.
	 *
	 * @param name the name
	 */
	void notifyWinner(String name);

	/**
	 * Notify end.
	 */
	void notifyEnd();
}
