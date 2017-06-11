/*
 * 
 */
package edu.cmu.qatar.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.cmu.qatar.cs214.hw4.core.GameEngine;

/**
 * The listener interface for receiving endMove events.
 * The class that is interested in processing a endMove
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addEndMoveListener<code> method. When
 * the endMove event occurs, that object's appropriate
 * method is invoked.
 *
 * @see EndMoveEvent
 */
public class EndMoveListener implements ActionListener {
	
	/** The panel. */
	private GameBoardPanel panel;
	
	/** The game. */
	private GameEngine game;

	/**
	 * Instantiates a new end move listener.
	 *
	 * @param panel the panel
	 */
	public EndMoveListener(GameBoardPanel panel) {
		this.panel = panel;
		this.game = this.panel.getGame();
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

		if (!game.executeMove()) {
			JFrame parentFrame = (JFrame) SwingUtilities.getRoot(panel);
			JOptionPane.showMessageDialog(parentFrame, " Invalid Move", "",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			game.nextTurn();
		}
	}

}
