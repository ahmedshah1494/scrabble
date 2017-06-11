/*
 * 
 */
package edu.cmu.qatar.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javafx.util.Pair;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * The listener interface for receiving undo events.
 * The class that is interested in processing a undo
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addUndoListener<code> method. When
 * the undo event occurs, that object's appropriate
 * method is invoked.
 *
 * @see UndoEvent
 */
public class UndoListener implements ActionListener {
	
	/** The panel. */
	private GameBoardPanel panel;

	/**
	 * Instantiates a new undo listener.
	 *
	 * @param panel the panel
	 */
	public UndoListener(GameBoardPanel panel) {
		this.panel = panel;
	}

	/** (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (this.panel.selectingSwapTiles) {
			JFrame parentFrame = (JFrame) SwingUtilities.getRoot(panel);
			JOptionPane
					.showMessageDialog(
							parentFrame,
							"To proceed select tiles to be swapped or click Swap Tiles again",
							"", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Pair<Integer, Integer> loc = this.panel.lastPlacedLocation;
		if (this.panel.lastPlacedLocation == null) {
			JFrame parentFrame = (JFrame) SwingUtilities.getRoot(panel);
			JOptionPane.showMessageDialog(parentFrame,
					"To go back you must move forward",
					"No Move History Found", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		this.panel.getGame().removeTile(loc.getKey(), loc.getValue());
		this.panel.lastPlacedLocation = null;
	}

}
