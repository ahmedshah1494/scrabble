/*
 * 
 */
package edu.cmu.qatar.cs214.hw4.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javafx.util.Pair;

import javax.swing.*;

import edu.cmu.qatar.cs214.hw4.core.GameEngine;
import edu.cmu.qatar.cs214.hw4.core.Rack;

/**
 * The Class GameBoardPanel.
 */
@SuppressWarnings("serial")
public class GameBoardPanel extends JPanel implements GameChangeListener {
	
	/** The Constant GRID_WIDTH. */
	private static final int GRID_WIDTH = 15;
	
	/** The Constant GRID_HEIGHT. */
	private static final int GRID_HEIGHT = 15;
	
	/** The squares. */
	private JButton[][] squares;
	
	/** The letter buttons. */
	private JButton[] letterButtons;
	
	/** The special buttons. */
	private ArrayList<JButton> specialButtons;
	
	/** The shop buttons. */
	private JButton[] shopButtons;
	
	/** The scores. */
	private HashMap<String, JLabel> scores;
	
	/** The current player label. */
	private JLabel currentPlayerLabel;
	
	/** The remaining tiles label. */
	private JLabel remainingTilesLabel;
	
	/** The board. */
	private String[][] board;
	
	/** The game. */
	private GameEngine game;
	
	/** The selected letter tile idx. */
	public int selectedLetterTileIdx = -1;
	
	/** The selected special tile. */
	public String selectedSpecialTile;
	
	/** The letter rack size. */
	private int letterRackSize = 7;
	
	/** The players. */
	private ArrayList<String> players;
	
	/** The selecting swap tiles. */
	public boolean selectingSwapTiles = false;
	
	/** The swap button. */
	private JButton swapButton;
	
	/** The swap idx. */
	private ArrayList<Integer> swapIdx;
	
	/** The last placed location. */
	public Pair<Integer, Integer> lastPlacedLocation;
	
	/** The is game over. */
	private boolean isGameOver = false;

	/**
	 * Instantiates a new game board panel.
	 *
	 * @param board the board
	 * @param game the game
	 */
	public GameBoardPanel(String[][] board, GameEngine game) {
		this.board = board.clone();
		this.game = game;
		this.squares = new JButton[GRID_HEIGHT][GRID_WIDTH];
		this.swapIdx = new ArrayList<Integer>();
		initGui();
	}
	
	/**
	 * Gets the letter buttons.
	 *
	 * @return the letter buttons
	 */
	public JButton[] getLetterButtons(){
		return this.letterButtons;
	}
	
	/**
	 * Gets the special buttons.
	 *
	 * @return the special buttons
	 */
	public ArrayList<JButton> getSpecialButtons(){
		return this.specialButtons;
	}
	
	/**
	 * Gets the current player label.
	 *
	 * @return the current player label
	 */
	public JLabel getCurrentPlayerLabel(){
		return this.currentPlayerLabel;
	}
	
	/**
	 * Gets the swap button.
	 *
	 * @return the swap button
	 */
	public JButton getSwapButton(){
		return this.swapButton;
	}
	
	/**
	 * Gets the swap idx.
	 *
	 * @return the swap idx
	 */
	public ArrayList<Integer> getSwapIdx(){
		return this.swapIdx;
	}
	
	/**
	 * Inits the gui.
	 */
	private void initGui() {
		setLayout(new BorderLayout());
		add(createBoardPanel(), BorderLayout.CENTER);
		add(createStatePanel(), BorderLayout.NORTH);
		add(createRackPanel(), BorderLayout.SOUTH);
		add(createScorePanel(), BorderLayout.EAST);
		add(createShopPanel(), BorderLayout.WEST);
	}

	/**
	 * Gets the game.
	 *
	 * @return the game
	 */
	public GameEngine getGame() {
		return this.game;
	}

	/**
	 * Update score.
	 */
	public void updateScore() {
		for (String p : this.players) {
			this.scores.get(p)
					.setText(p + " : " + this.game.getPlayerPoints(p));
		}
	}

	/**
	 * Creates the board panel.
	 *
	 * @return the j panel
	 */
	private JPanel createBoardPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(GRID_HEIGHT, GRID_WIDTH));
		System.out.println("hello");
		// Create all of the squares and display them
		for (int y = 0; y < GRID_HEIGHT; y++) {
			for (int x = 0; x < GRID_WIDTH; x++) {
				squares[y][x] = new JButton();
				squares[y][x].setText(board[y][x]);
				squares[y][x].addActionListener(new SquareListener(x, y, this));
				panel.add(squares[y][x]);
			}
		}
		return panel;
	}

	/**
	 * Creates the state panel.
	 *
	 * @return the j panel
	 */
	private JPanel createStatePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 3));
		this.currentPlayerLabel = new JLabel();
		this.currentPlayerLabel.setText("Current Player : "
				+ this.game.getActivePlayerName());
		this.remainingTilesLabel = new JLabel();
		this.remainingTilesLabel.setText("Tiles in Bag : "
				+ this.game.getRemainingTiles());
		panel.add(new JLabel());
		panel.add(this.currentPlayerLabel);
		panel.add(this.remainingTilesLabel);
		return panel;
	}

	/**
	 * Creates the letter rack panel.
	 *
	 * @return the j panel
	 */
	private JPanel createLetterRackPanel() {
		JPanel panel = new JPanel();
		JLabel rackLabel = new JLabel();
		panel.setLayout(new GridLayout());
		rackLabel.setText("Letter Tile Rack");
		panel.add(rackLabel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, this.letterRackSize));
		letterButtons = new JButton[this.letterRackSize];
		ArrayList<String> rack = this.game.getActivePlayerLetterRack();
		for (int x = 0; x < rack.size(); x++) {
			letterButtons[x] = new JButton();
			letterButtons[x].setText(rack.get(x));
			letterButtons[x].addActionListener(new RackListener(x, this,
					"letter"));
			panel.add(letterButtons[x]);
		}
		return panel;
	}

	/**
	 * Creates the special rack panel.
	 *
	 * @return the j panel
	 */
	private JPanel createSpecialRackPanel() {
		JPanel panel = new JPanel();
		JLabel rackLabel = new JLabel();
		panel.setLayout(new BorderLayout());
		rackLabel.setText("Special Tile Rack");
		panel.add(rackLabel, BorderLayout.NORTH);
		panel.setLayout(new FlowLayout()); // need to check this
		Iterator<String> specialTiles = this.game.getSpecialTilesList()
				.keySet().iterator();
		specialButtons = new ArrayList<JButton>();
		JButton b;
		String s;
		int i = 0;
		while (specialTiles.hasNext()) {
			s = specialTiles.next();
			b = new JButton();
			b.setText(s + " 0");
			b.addActionListener(new RackListener(i, this, "special"));
			panel.add(b);
			specialButtons.add(b);
			i++;
		}
		return panel;
	}

	/**
	 * Creates the rack panel.
	 *
	 * @return the j panel
	 */
	private JPanel createRackPanel() {
		JPanel panel = new JPanel();
		JButton b = new JButton();
		b.addActionListener(new EndMoveListener(this));
		b.setText("Make Move!");

		JButton skipButton = new JButton();
		skipButton.addActionListener(new SkipListener(this));
		skipButton.setText("Skip Move");

		JButton undoButton = new JButton();
		undoButton.addActionListener(new UndoListener(this));
		undoButton.setText("Undo Last Placement");

		swapButton = new JButton();
		swapButton.addActionListener(new SwapListener(this));
		swapButton.setText("Swap Tiles");
		
		panel.setLayout(new GridLayout(6, 1));
		panel.add(this.createLetterRackPanel());
		panel.add(this.createSpecialRackPanel());
		panel.add(b);
		panel.add(undoButton);
		panel.add(skipButton);
		panel.add(swapButton);
		return panel;
	}

	/**
	 * Creates the score panel.
	 *
	 * @return the j panel
	 */
	private JPanel createScorePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(4, 1));
		this.players = this.game.playerOrderToList();
		this.scores = new HashMap<String, JLabel>();
		JLabel label;
		for (String p : this.players) {
			label = new JLabel(p + " : " + this.game.getPlayerPoints(p));
			this.scores.put(p, label);
			panel.add(label);
		}
		return panel;
	}

	/**
	 * Creates the shop panel.
	 *
	 * @return the j panel
	 */
	private JPanel createShopPanel() {
		JPanel panel = new JPanel();
		HashMap<String, Integer> priceList = this.game.getSpecialTilesList();
		Iterator<Entry<String, Integer>> iterator = priceList.entrySet()
				.iterator();
		this.shopButtons = new JButton[priceList.size()];
		Map.Entry<String, Integer> e;
		int i = 0;
		panel.setLayout(new GridLayout(priceList.size() + 1, 1));
		panel.add(new JLabel("Special Tile Shop"));
		while (iterator.hasNext()) {
			e = iterator.next();
			this.shopButtons[i] = new JButton();
			this.shopButtons[i].setText(e.getKey() + " | " + e.getValue()
					+ "points");
			this.shopButtons[i].addActionListener(new ShopListener(e.getKey(),
					this));
			panel.add(this.shopButtons[i]);
		}
		return panel;
	}

	/** (non-Javadoc)
	 * @see edu.cmu.qatar.cs214.hw4.gui.GameChangeListener#notifyTilePlaced(java.lang.String, int, int)
	 */
	@Override
	public void notifyTilePlaced(String tile, int y, int x) {
		System.out.println(tile);
		if (tile.length() > 1) {
			this.squares[y][x].setText(tile);
			this.lastPlacedLocation = new Pair<Integer, Integer>(y, x);
			return;
		}
		this.squares[y][x].setText(tile);
		this.lastPlacedLocation = new Pair<Integer, Integer>(y, x);
		System.out.println(this.squares[y][x].getText());
	}

	/** (non-Javadoc)
	 * @see edu.cmu.qatar.cs214.hw4.gui.GameChangeListener#notifyRackChanged(java.util.ArrayList, java.util.ArrayList)
	 */
	@Override
	public void notifyRackChanged(ArrayList<String> letterRack,
			ArrayList<String> specialRack) {
		// TODO Auto-generated method stub
		System.out.println(letterRack);
		for (int i = 0; i < letterRackSize; i++) {
			if (i >= letterRack.size()) {
				this.letterButtons[i].setText("");
			} else {
				this.letterButtons[i].setText(letterRack.get(i));
			}
		}

		String tileName;
		int count;
		for (JButton b : this.specialButtons) {
			count = 0;
			tileName = b.getText().split(" ")[0];
			for (String s : specialRack) {
				if (s.equals(tileName)) {
					count++;
				}
			}
			b.setText(tileName + " " + count);
		}

	}

	/** (non-Javadoc)
	 * @see edu.cmu.qatar.cs214.hw4.gui.GameChangeListener#notifyPlayerChanged(java.lang.String)
	 */
	@Override
	public void notifyPlayerChanged(String name) {
		// TODO Auto-generated method stub
		this.currentPlayerLabel.setText("Current Player : " + name);
	}

	/** (non-Javadoc)
	 * @see edu.cmu.qatar.cs214.hw4.gui.GameChangeListener#notifyMoveMade()
	 */
	@Override
	public void notifyMoveMade() {
		// TODO Auto-generated method stub
		if (this.isGameOver) {
			JFrame parentFrame = (JFrame) SwingUtilities.getRoot(this);
			JOptionPane.showMessageDialog(parentFrame, "The game has ended",
					"Game Over", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		this.remainingTilesLabel.setText("Tiles in Bag : "
				+ this.game.getRemainingTiles());
	}

	/** (non-Javadoc)
	 * @see edu.cmu.qatar.cs214.hw4.gui.GameChangeListener#notifyWinner(java.lang.String)
	 */
	@Override
	public void notifyWinner(String name) {
		// TODO Auto-generated method stub
		JFrame parentFrame = (JFrame) SwingUtilities.getRoot(this);
		JOptionPane.showMessageDialog(parentFrame, name + " Won!", "Game Over",
				JOptionPane.INFORMATION_MESSAGE);
		return;
	}

	/** (non-Javadoc)
	 * @see edu.cmu.qatar.cs214.hw4.gui.GameChangeListener#notifyEnd()
	 */
	@Override
	public void notifyEnd() {
		// TODO Auto-generated method stub
		this.isGameOver = true;
	}

	/** (non-Javadoc)
	 * @see edu.cmu.qatar.cs214.hw4.gui.GameChangeListener#notifyScoreChanged(java.lang.String, int)
	 */
	@Override
	public void notifyScoreChanged(String player, int score) {
		this.scores.get(player).setText(player + " : " + score);

	}

	/** (non-Javadoc)
	 * @see edu.cmu.qatar.cs214.hw4.gui.GameChangeListener#notifyBoardChanged(java.lang.String[][])
	 */
	@Override
	public void notifyBoardChanged(String[][] board) {
		// TODO Auto-generated method stub
		for (int y = 0; y < this.GRID_HEIGHT; y++) {
			for (int x = 0; x < this.GRID_WIDTH; x++) {
				this.squares[y][x].setText(board[y][x]);
			}
		}
	}
}
