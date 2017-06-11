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
import javax.swing.border.Border;


/**
 * The listener interface for receiving swap events.
 * The class that is interested in processing a swap
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addSwapListener<code> method. When
 * the swap event occurs, that object's appropriate
 * method is invoked.
 *
 * @see SwapEvent
 */
public class SwapListener implements ActionListener {
	
	/** The panel. */
	private GameBoardPanel panel;
	
	/** The default border. */
	private Border defaultBorder = null;

	/**
	 * Instantiates a new swap listener.
	 *
	 * @param panel the panel
	 */
	public SwapListener(GameBoardPanel panel) {
		this.panel = panel;
	}

	/** (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(this.panel.getSwapIdx());
		if (!this.panel.selectingSwapTiles) {
			this.panel.selectingSwapTiles = true;
			this.defaultBorder = this.panel.getSwapButton().getBorder();
			this.panel.getSwapButton().setBorder(BorderFactory
					.createLineBorder(Color.BLUE));
			return;
		}

		if (!this.panel.getGame().swapTiles(this.panel.getSwapIdx())) {
			JFrame parentFrame = (JFrame) SwingUtilities.getRoot(panel);
			JOptionPane.showMessageDialog(parentFrame, " Not Enough Tiles",
					"Tile Swap Failed", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (this.panel.getSwapIdx().size() == 0) {
			this.panel.selectingSwapTiles = false;
			this.panel.getSwapButton().setBorder(this.defaultBorder);
			return;
		}

		for (Integer i : this.panel.getSwapIdx()) {
			this.panel.getLetterButtons()[i].setBorder(this.defaultBorder);
		}
		this.panel.getSwapButton().setBorder(this.defaultBorder);
		this.panel.selectingSwapTiles = false;
		this.panel.getSwapIdx().clear();
	}

}
