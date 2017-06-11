/*
 * 
 */
package edu.cmu.qatar.cs214.hw4.gui;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import edu.cmu.qatar.cs214.hw4.core.GameEngine;


/**
 * The Class Main.
 */
public class Main {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					createAndShowGameBoard();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Creates the and show game board.
	 *
	 * @throws FileNotFoundException the file not found exception
	 */
	private static void createAndShowGameBoard() throws FileNotFoundException {
		// Create and set-up the window
		JFrame gameWindow = new JFrame("Scrabble");
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		GameEngine game = new GameEngine();
		game.addPlayer("Cross");
		game.addPlayer("Knot");
		game.addPlayer("KnotyCross");
		game.startGame();
		// Create and set up the content pane
		GameBoardPanel gameBoard = new GameBoardPanel(game.getBoard()
				.getStringBoard(game.getActivePlayer()), game);
		game.setGameChangeListener(gameBoard);
		gameBoard.getCurrentPlayerLabel().setText("Current player: "
				+ game.getActivePlayerName());
		gameBoard.setOpaque(true);
		gameWindow.setContentPane(gameBoard);

		// Display the window.
		gameWindow.pack();
		gameWindow.setVisible(true);
	}
}
