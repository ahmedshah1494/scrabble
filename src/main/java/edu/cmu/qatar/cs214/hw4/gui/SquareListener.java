/*
 * 
 */
package edu.cmu.qatar.cs214.hw4.gui;

import java.awt.event.*;

import javafx.util.Pair;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.cmu.qatar.cs214.hw4.core.GameEngine;


/**
 * The listener interface for receiving square events.
 * The class that is interested in processing a square
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSquareListener<code> method. When
 * the square event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SquareEvent
 */
public class SquareListener implements ActionListener {
	
	/** The panel. */
	private GameBoardPanel panel;
	
	/** The x. */
	private int x;
	
	/** The y. */
	private int y;
	
	/** The game. */
	private GameEngine game;

	/**
	 * Instantiates a new square listener.
	 *
	 * @param x the x
	 * @param y the y
	 * @param panel the panel
	 */
	public SquareListener(int x, int y, GameBoardPanel panel) {
		this.panel = panel;
		this.game = panel.getGame();
		this.x = x;
		this.y = y;
	}

	/** (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(e.paramString());
		// make move
		if (this.panel.selectingSwapTiles) {
			JFrame parentFrame = (JFrame) SwingUtilities.getRoot(panel);
			JOptionPane
					.showMessageDialog(
							parentFrame,
							"To proceed select tiles to be swapped or click Swap Tiles again",
							"", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (panel.selectedSpecialTile != null) {
			if (!game.placeSpecialTile(panel.selectedSpecialTile, y, x)) {
				JFrame parentFrame = (JFrame) SwingUtilities.getRoot(panel);
				JOptionPane
						.showMessageDialog(
								parentFrame,
								" Place Tiles either vertically or horizontally, in the order that they appear in a word",
								"Invalid Move", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			panel.selectedSpecialTile = null;
			panel.lastPlacedLocation = new Pair<Integer, Integer>(y, x);
			return;
		}

		if (panel.selectedLetterTileIdx == -1) {
			JFrame parentFrame = (JFrame) SwingUtilities.getRoot(panel);
			JOptionPane.showMessageDialog(parentFrame, " Please Select a Tile",
					"No Tile Selected", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		if (!game.placeLetterTile(panel.selectedLetterTileIdx, y, x)) {
			JFrame parentFrame = (JFrame) SwingUtilities.getRoot(panel);
			JOptionPane
					.showMessageDialog(
							parentFrame,
							" Place Tiles either vertically or horizontally, in the order that they appear in a word",
							"Invalid Move", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		panel.lastPlacedLocation = new Pair<Integer, Integer>(y, x);
		panel.selectedLetterTileIdx = -1;
	}
}
