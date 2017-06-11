/*
 * 
 */
package edu.cmu.qatar.cs214.hw4.gui;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.cmu.qatar.cs214.hw4.core.GameEngine;

/**
 * The listener interface for receiving rack events.
 * The class that is interested in processing a rack
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addRackListener<code> method. When
 * the rack event occurs, that object's appropriate
 * method is invoked.
 *
 * @see RackEvent
 */
public class RackListener implements ActionListener {
	
	/** The panel. */
	private GameBoardPanel panel;
	
	/** The x. */
	private int x;
	
	/** The tile type. */
	private String tileType;

	/**
	 * Instantiates a new rack listener.
	 *
	 * @param panel the panel
	 * @param tileType the tile type
	 */
	public RackListener(GameBoardPanel panel, String tileType) {
		this.panel = panel;
		this.tileType = tileType;
	}

	/**
	 * Instantiates a new rack listener.
	 *
	 * @param x the x
	 * @param panel the panel
	 * @param tileType the tile type
	 */
	public RackListener(int x, GameBoardPanel panel, String tileType) {
		this.panel = panel;
		this.x = x;
		this.tileType = tileType;
	}

	/** (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (this.panel.selectingSwapTiles) {
			if (this.tileType.equals("special")) {
				JFrame parentFrame = (JFrame) SwingUtilities.getRoot(panel);
				JOptionPane
						.showMessageDialog(
								parentFrame,
								"To proceed select tiles to be swapped or click Swap Tiles again",
								"", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			this.panel.getSwapIdx().add(x);
			this.panel.getLetterButtons()[x].setBorder(BorderFactory
					.createLineBorder(Color.CYAN));
			return;
		}

		if (this.tileType.equals("letter")) {
			if (this.panel.getLetterButtons()[x].getText().equals("")) {
				return;
			}
			System.out.println(x);
			panel.selectedLetterTileIdx = x;
			panel.selectedSpecialTile = null;
			// panel.selectedLetterTile
		}
		if (this.tileType.equals("special")) {
			if (Integer.parseInt(this.panel.getSpecialButtons().get(x).getText()
					.split(" ")[1]) == 0) {
				JFrame parentFrame = (JFrame) SwingUtilities.getRoot(panel);
				JOptionPane.showMessageDialog(parentFrame,
						" You don't have this tile", "",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			String s = this.panel.getSpecialButtons().get(x).getText();
			panel.selectedSpecialTile = s.substring(0, s.length() - 2);
			panel.selectedLetterTileIdx = -1;
			System.out.println(panel.selectedSpecialTile);
		}

	}
}
