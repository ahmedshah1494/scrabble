/*
 * 
 */
package edu.cmu.qatar.cs214.hw4.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


/**
 * The listener interface for receiving skip events.
 * The class that is interested in processing a skip
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSkipListener<code> method. When
 * the skip event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SkipEvent
 */
public class SkipListener implements ActionListener {
	
	/** The panel. */
	private GameBoardPanel panel;

	/**
	 * Instantiates a new skip listener.
	 *
	 * @param panel the panel
	 */
	public SkipListener(GameBoardPanel panel) {
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
		this.panel.getGame().nextTurn();
	}

}
