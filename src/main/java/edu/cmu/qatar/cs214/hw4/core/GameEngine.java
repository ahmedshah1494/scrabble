package edu.cmu.qatar.cs214.hw4.core;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import edu.cmu.qatar.cs214.hw4.gui.GameBoardPanel;
import edu.cmu.qatar.cs214.hw4.gui.GameChangeListener;
import javafx.util.Pair;


/**
 * The Class GameEngine.
 */
public class GameEngine {
	
	/** The players. */
	private ArrayList<Player> players;
	
	/** The active player. */
	private Player activePlayer;
	
	/** The turn counter. */
	private int turnCounter;
	
	/** The game over. */
	private boolean gameOver;
	
	/** The game started. */
	private boolean gameStarted;
	
	/** The board. */
	private GameBoard board;
	
	/** The tile bag. */
	private Bag tileBag;
	
	/** The shop. */
	private SpecialTileShop shop;
	
	/** The curr move. */
	private Move currMove;
	
	/** The winner. */
	private String winner;
	
	/** The player order. */
	private Queue<Player> playerOrder;
	
	/** The listener. */
	private GameChangeListener listener;

	/**
	 * Instantiates a new game engine.
	 *
	 * @throws FileNotFoundException the file not found exception
	 */
	public GameEngine() throws FileNotFoundException {
		this.tileBag = new Bag(
				"/Users/Ahmed/Downloads/15-214/mshah1/homework/4/src/main/resources/scrabbleTiles");
		this.shop = new SpecialTileShop();
		this.board = new GameBoard(
				new Dictionary(
						"/Users/Ahmed/Downloads/15-214/mshah1/homework/4/src/main/resources/words.txt"));
		this.players = new ArrayList<Player>();
		this.turnCounter = 0;
		this.gameOver = false;
		this.playerOrder = new ConcurrentLinkedQueue<Player>();
		this.activePlayer = null;
	}

	/**
	 * Sets the game change listener.
	 *
	 * @param l the new game change listener
	 */
	public void setGameChangeListener(GameChangeListener l) {
		this.listener = l;
	}

	/**
	 * Start game.
	 *
	 * @return true, if successful
	 */
	public boolean startGame() {
		byte min = Byte.MAX_VALUE;
		LetterTile t;
		LetterTile[] tiles = new LetterTile[this.players.size()];
		if (players.size() < 2) {
			return false;
		}

		int i = 0;
		for (Player p : players) {
			System.out.println(this.tileBag.getRemainingTiles());
			t = this.tileBag.getRandomTile();
			tiles[i] = t;
			if (t.getLetter().getBytes()[0] < min) {
				min = t.getLetter().getBytes()[0];
				this.activePlayer = p;
			}
			i++;
		}

		for (int k = 0; k < this.players.size(); k++) {
			this.tileBag.replaceTile(tiles[k]);
		}

		for (Player p : players) {
			if (!p.equals(this.activePlayer)) {
				this.playerOrder.add(p);
			}
		}
		this.playerOrder.add(this.activePlayer);

		this.currMove = new Move(this.activePlayer);

		for (Player p : players) {
			this.tileBag.fillRack(p.getRack());
		}
		this.gameStarted = true;
		return true;
	}

	/**
	 * End game.
	 */
	public void endGame() {
		int max = Integer.MIN_VALUE;
		int score;
		ArrayList<Pair<Player, Integer>> tied = new ArrayList<Pair<Player, Integer>>();
		Player win = null;

		for (Player p : players) {
			score = p.getScore();

			if (p.getRack().getNumberOfTiles() == 0) {
				for (Player p2 : players) {
					if (!p.equals(p2)) {
						for (LetterTile lT : p2.getRack().getLetterTilesList()) {
							score += lT.getValue();
						}
					}
				}
			}

			else {
				for (LetterTile lT : p.getRack().getLetterTilesList()) {
					score -= lT.getValue();
				}
			}
			if (score > max) {
				max = score;
				win = p;
			}

			if (score == max && !win.equals(p)) {
				System.out.println(score);
				System.out.println(p.getName() + " " + win.getName());
				tied.add(new Pair<Player, Integer>(p, score));
				tied.add(new Pair<Player, Integer>(win, max));
			}
		}

		boolean isWinner = true;
		for (Pair<Player, Integer> p1 : tied) {
			if (p1.getValue() >= max) {
				for (Pair<Player, Integer> p2 : tied) {
					isWinner = (p1.getKey().getScore() > p2.getKey().getScore())
							&& isWinner;
				}

				if (isWinner) {
					this.winner = p1.getKey().getName();
					this.listener.notifyWinner(this.winner);
					return;
				}
			}
		}

		if (win != null) {
			this.winner = win.getName();
			this.listener.notifyWinner(this.winner);
		}
	}

	/**
	 * Gets the winner.
	 *
	 * @return the winner
	 */
	public String getWinner() {
		return this.winner;
	}

	/**
	 * Checks if is game over.
	 *
	 * @return true, if is game over
	 */
	public boolean isGameOver() {
		return this.gameOver;
	}

	/**
	 * Next turn.
	 */
	public void nextTurn() {
		if (this.tileBag.isEmpty()) {
			for (Player p : players) {
				if (this.board.isFull(p) || p.getRack().getNumberOfTiles() == 0) {
					this.gameOver = true;
					this.listener.notifyEnd();
					this.endGame();
					return;
				}
			}
		}
		this.turnCounter += 1;
		this.activePlayer = this.playerOrder.poll();
		this.playerOrder.add(this.activePlayer);
		this.currMove = new Move(this.activePlayer);
		this.listener.notifyMoveMade();
		this.listener.notifyPlayerChanged(this.activePlayer.getName());
		this.listener
				.notifyRackChanged(
						this.activePlayer.getRack().getLettersList(),
						this.activePlayer.getRack().getSpecialList());
		this.listener.notifyBoardChanged(this.getStringBoard());
	}

	/**
	 * Adds the player.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public boolean addPlayer(String name) {
		if (this.players.size() == 4 && !this.gameStarted) {
			return false;
		}

		Player p = new Player(name);
		this.players.add(p);
		return true;
	}

	/**
	 * Removes the player.
	 *
	 * @param name the name
	 */
	public void removePlayer(String name) {
		Player player = null;
		for (Player p : players) {
			if (p.getName().equals(name)) {
				player = p;
			}
		}
		if (player == null) {
			return;
		}
		players.remove(player);

		Queue<Player> newPlayerOrder = new ConcurrentLinkedQueue<Player>();
		while (!this.playerOrder.isEmpty()) {
			if (!this.playerOrder.peek().equals(player)) {
				newPlayerOrder.add(this.playerOrder.poll());
			} else {
				this.playerOrder.poll();
			}
		}
		this.playerOrder = newPlayerOrder;
	}

	/**
	 * Gets the active player name.
	 *
	 * @return the active player name
	 */
	public String getActivePlayerName() {
		return this.activePlayer.getName();
	}

	/**
	 * Gets the active player letter rack.
	 *
	 * @return the active player letter rack
	 */
	public ArrayList<String> getActivePlayerLetterRack() {
		return this.activePlayer.getLetterRack();
	}

	/**
	 * Gets the active player special rack.
	 *
	 * @return the active player special rack
	 */
	public ArrayList<String> getActivePlayerSpecialRack() {
		return this.activePlayer.getSpecialRack();
	}

	/**
	 * Gets the active player.
	 *
	 * @return the active player
	 */
	public Player getActivePlayer() {
		return this.activePlayer;
	}

	/**
	 * Gets the player points.
	 *
	 * @param name the name
	 * @return the player points
	 */
	public int getPlayerPoints(String name) {
		for (Player p : players) {
			if (p.getName().equals(name)) {
				return p.getScore();
			}
		}
		return Integer.MIN_VALUE;
	}

	/**
	 * Swap tiles.
	 *
	 * @param tiles the tiles
	 * @return true, if successful
	 */
	public boolean swapTiles(List<Integer> tiles) {
		if (this.tileBag.swapTiles(this.activePlayer.getRack(), tiles)) {
			this.nextTurn();
			return true;
		}
		this.listener
				.notifyRackChanged(
						this.activePlayer.getRack().getLettersList(),
						this.activePlayer.getRack().getSpecialList());
		return false;
	}

	/**
	 * Place letter tile.
	 *
	 * @param letterTileIdx the letter tile idx
	 * @param row the row
	 * @param col the col
	 * @return true, if successful
	 */
	public boolean placeLetterTile(int letterTileIdx, int row, int col) {
		LetterTile letterTile = this.activePlayer.getRack().getLetterTile(
				letterTileIdx);
		if (!this.currMove.addLetterTile(letterTile, row, col)) {
			this.activePlayer.getRack().returnTile(letterTile);
			return false;
		}
		this.listener.notifyTilePlaced(letterTile.getLetter(), row, col);
		this.listener
				.notifyRackChanged(
						this.activePlayer.getRack().getLettersList(),
						this.activePlayer.getRack().getSpecialList());
		return true;
	}

	/**
	 * Place special tile.
	 *
	 * @param tileName the tile name
	 * @param row the row
	 * @param col the col
	 * @return true, if successful
	 */
	public boolean placeSpecialTile(String tileName, int row, int col) {
		ArrayList<SpecialTile> specialTiles = this.activePlayer.getRack()
				.getSpecialTilesList();
		String name;
		for (int i = 0; i < specialTiles.size(); i++) {
			if (specialTiles.get(i).getName().equals(tileName)) {
				name = specialTiles.get(i).getName();
				if (this.currMove.addSpecialTile(this.activePlayer.getRack()
						.getSpecialTile(i), row, col)) {
					this.listener.notifyRackChanged(this.activePlayer.getRack()
							.getLettersList(), this.activePlayer.getRack()
							.getSpecialList());
					this.listener.notifyTilePlaced(name, row, col);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Removes the tile.
	 *
	 * @param row the row
	 * @param col the col
	 */
	public void removeTile(int row, int col) {
		this.removeLetterTile(row, col);
		this.removeSpecialTile(row, col);
		this.listener
				.notifyRackChanged(
						this.activePlayer.getRack().getLettersList(),
						this.activePlayer.getRack().getSpecialList());
		this.listener.notifyTilePlaced("", row, col);
	}

	/**
	 * Removes the letter tile.
	 *
	 * @param row the row
	 * @param col the col
	 */
	public void removeLetterTile(int row, int col) {
		LetterTile t = this.currMove.removeLetterTile(row, col);
		if (t != null) {
			this.activePlayer.getRack().returnTile(t);
		}
	}

	/**
	 * Removes the special tile.
	 *
	 * @param row the row
	 * @param col the col
	 */
	public void removeSpecialTile(int row, int col) {
		SpecialTile t = this.currMove.removeSpecailTile(row, col);
		if (t != null) {
			this.activePlayer.getRack().returnTile(t);
		}
	}

	/**
	 * Execute move.
	 *
	 * @return true, if successful
	 */
	public boolean executeMove() {
		if (!board.validateMove(currMove)) {
			System.out.println(board.validateMove(currMove));
			for (Pair<LetterTile, Location> lT : this.currMove.getLetterTiles()) {
				this.activePlayer.getRack().returnTile(lT.getKey());
			}
			for (Pair<SpecialTile, Location> sT : this.currMove
					.getSpecialTiles()) {
				this.activePlayer.getRack().returnTile(sT.getKey());
			}
			this.currMove = new Move(this.activePlayer);
			this.listener.notifyBoardChanged(this.getStringBoard());
			this.listener.notifyRackChanged(this.activePlayer.getRack()
					.getLettersList(), this.activePlayer.getRack()
					.getSpecialList());
			return false;
		}

		int score = board.updateBoard(currMove);
		this.activePlayer.updateScore(score);
		this.tileBag.fillRack(this.activePlayer.getRack());
		this.listener.notifyScoreChanged(this.activePlayer.getName(), this.activePlayer.getScore());
		return true;
	}

	/**
	 * Gets the special tiles list.
	 *
	 * @return the special tiles list
	 */
	public HashMap<String, Integer> getSpecialTilesList() {
		return this.shop.getSpecialTilePriceList();
	}

	/**
	 * Buy special tile.
	 *
	 * @param name the name
	 * @return true, if successful
	 */
	public boolean buySpecialTile(String name) {
		if (this.shop.getSpecialTilePrice(name) <= this.activePlayer.getScore()) {
			this.activePlayer.getRack().returnTile(
					this.shop.getSpecialTile(this, this.activePlayer, name));
			this.activePlayer.updateScore(-this.shop.getSpecialTilePrice(name));

			this.listener.notifyScoreChanged(this.activePlayer.getName(),
					this.activePlayer.getScore());
			this.listener.notifyRackChanged(this.activePlayer.getRack()
					.getLettersList(), this.activePlayer.getRack()
					.getSpecialList());
			return true;
		}
		return false;
	}

	/**
	 * Gets the board.
	 *
	 * @return the board
	 */
	public GameBoard getBoard() {
		return this.board;
	}

	/**
	 * Gets the player order.
	 *
	 * @return the player order
	 */
	public Queue<Player> getPlayerOrder() {
		return this.playerOrder;
	}

	/**
	 * Gets the string board.
	 *
	 * @return the string board
	 */
	public String[][] getStringBoard() {
		return this.board.getStringBoard(this.activePlayer);
	}

	/**
	 * Sets the player order.
	 *
	 * @param order the order
	 * @return true, if successful
	 */
	public boolean setPlayerOrder(Queue<Player> order) {
		Queue<Player> newPlayerOrder = new ConcurrentLinkedQueue<Player>();
		Player p;
		while (!order.isEmpty()) {
			p = order.poll();
			if (!this.players.contains(p)) {
				return false;
			}
			newPlayerOrder.add(p);
		}
		this.playerOrder = newPlayerOrder;
		return true;
	}

	/**
	 * Change active player.
	 *
	 * @param p the p
	 */
	public void changeActivePlayer(Player p) {
		if (!this.players.contains(p)) {
			return;
		}

		this.activePlayer = p;
	}

	/**
	 * Sets the curr move.
	 *
	 * @param m the new curr move
	 */
	// for testing only
	public void setCurrMove(Move m) {
		this.currMove = m;
	}

	/**
	 * Player order to string.
	 *
	 * @return the string
	 */
	public String playerOrderToString() {
		Queue<Player> order = new ConcurrentLinkedQueue<Player>();
		Player p;
		StringBuffer str = new StringBuffer();
		while (!this.playerOrder.isEmpty()) {
			p = this.playerOrder.poll();
			order.add(p);
			str.append(p.getName() + "->");
		}
		this.playerOrder = order;
		return str.toString();
	}

	/**
	 * Player order to list.
	 *
	 * @return the array list
	 */
	public ArrayList<String> playerOrderToList() {
		Queue<Player> order = new ConcurrentLinkedQueue<Player>();
		Player p;
		ArrayList<String> str = new ArrayList<String>();
		while (!this.playerOrder.isEmpty()) {
			p = this.playerOrder.poll();
			order.add(p);
			str.add(p.getName());
		}
		this.playerOrder = order;
		return str;
	}

	/**
	 * Gets the remaining tiles.
	 *
	 * @return the remaining tiles
	 */
	public int getRemainingTiles() {
		return this.tileBag.getRemainingTiles();
	}

	/**
	 * Empty bag.
	 */
	// for testing only
	public void emptyBag() {
		this.tileBag.emptyBag();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer str = new StringBuffer();
		if (this.gameOver) {
			str.append("Winner : " + this.winner + "\n\n");
		} else {
			str.append("Active Player : " + this.activePlayer.getName() + "\n\n");
		}
		str.append("\n---------------Order---------------\n"
				+ this.playerOrderToString());
		str.append("\n---------------Racks---------------\n");
		for (Player p : players) {
			str.append(p.getName() + " : " + p.getScore() + "\n"
					+ p.getRack().toString() + "\n");
		}
		str.append("\n---------------Board---------------\n");
		str.append(this.board.toString());

		return str.toString();
	}

}
