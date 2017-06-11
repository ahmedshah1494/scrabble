/*
 * 
 */
package edu.cmu.qatar.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import edu.cmu.qatar.cs214.hw4.core.GameEngine;

/**
 * The listener interface for receiving shop events.
 * The class that is interested in processing a shop
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addShopListener<code> method. When
 * the shop event occurs, that object's appropriate
 * method is invoked.
 *
 * @see ShopEvent
 */
public class ShopListener implements ActionListener {
	
	/** The panel. */
	private GameBoardPanel panel;
	
	/** The game. */
	private GameEngine game;
	
	/** The special tile name. */
	private String specialTileName;

	/**
	 * Instantiates a new shop listener.
	 *
	 * @param tileName the tile name
	 * @param panel the panel
	 */
	public ShopListener(String tileName, GameBoardPanel panel) {
		this.specialTileName = tileName;
		this.panel = panel;
		this.game = panel.getGame();
	}

	/** (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.panel.selectingSwapTiles) {
			JFrame parentFrame = (JFrame) SwingUtilities.getRoot(panel);
			JOptionPane
					.showMessageDialog(
							parentFrame,
							"To proceed select tiles to be swapped or click Swap Tiles again",
							"", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (!this.game.buySpecialTile(this.specialTileName)) {
			JFrame parentFrame = (JFrame) SwingUtilities.getRoot(panel);
			JOptionPane.showMessageDialog(parentFrame,
					"You do not have enough points to buy this tile",
					"Insufficient Points", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}

}
