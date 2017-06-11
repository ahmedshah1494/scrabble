package edu.cmu.qatar.cs214.hw4.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

import javafx.util.Pair;

/**
 * The Class Move.
 */
public class Move {
	
	/** The letter tile locations. */
	private ArrayList<Pair<LetterTile, Location>> letterTileLocations;
	
	/** The special tile locations. */
	private ArrayList<Pair<SpecialTile, Location>> specialTileLocations;
	
	/** The tile locations. */
	private ArrayList<Location> tileLocations;
	
	/** The player. */
	private Player player;
	
	/** The vertical. */
	private boolean vertical;
	
	/**
	 * Instantiates a new move.
	 *
	 * @param player the player
	 */
	public Move(Player player){
		this.player = player;
		this.letterTileLocations = new ArrayList<Pair<LetterTile, Location>>();
		this.specialTileLocations = new ArrayList<Pair<SpecialTile, Location>>();
		this.tileLocations = new ArrayList<Location>();
	}
	
	/**
	 * Verify add.
	 *
	 * @param row the row
	 * @param col the col
	 * @return true, if successful
	 */
	private boolean verifyAdd(int row, int col){		
		if (this.tileLocations.size() == 1){
			Location l = this.tileLocations.get
								(this.tileLocations.size() - 1);
			if (l.getCol() == col && row > l.getRow()){
				this.vertical = true;
				return true;
			}
			
			if (l.getRow() == row && col > l.getCol()){
				this.vertical = false;
				return true;
			}
			return false;
		}
		
		else{
			if (this.tileLocations.size() == 0){
				return true;
			}
			
			Location l = this.tileLocations.get
					(this.tileLocations.size() - 1);
			if (this.vertical){
				return (l.getCol() == col && row > l.getRow());
			}
			else{
				return (l.getRow() == row && col > l.getCol());
			}
		}
	}
	
	/**
	 * Adds the letter tile.
	 *
	 * @param t the t
	 * @param row the row
	 * @param col the col
	 * @return true, if successful
	 */
	public boolean addLetterTile(LetterTile t, int row, int col){
		if (!verifyAdd(row, col)){
			return false;
		}
		
		Location loc = new Location(row,col);
		Pair<LetterTile, Location> p = new Pair<LetterTile, Location>
													(t, loc);
		this.letterTileLocations.add(p);
		this.tileLocations.add(loc);
		return true;
	}
	
	/**
	 * Adds the special tile.
	 *
	 * @param t the t
	 * @param row the row
	 * @param col the col
	 * @return true, if successful
	 */
	public boolean addSpecialTile(SpecialTile t, int row, int col){
		if (!verifyAdd(row, col)){
			return false;
		}
		Location loc = new Location(row,col);
		Pair<SpecialTile, Location> p = new Pair<SpecialTile, Location>
												(t, loc);
		this.specialTileLocations.add(p);
		this.tileLocations.add(loc);
		return true;
	}
	
	
	/**
	 * Removes the letter tile.
	 *
	 * @param row the row
	 * @param col the col
	 * @return the letter tile
	 */
	public LetterTile removeLetterTile(int row, int col){
		Pair<LetterTile, Location> loc = null;
		for (Pair<LetterTile, Location> p : this.letterTileLocations){
			if (p.getValue().getCol() == col &&
				p.getValue().getRow() == row){
				loc = p;
			}
		}
		if (loc != null){
			this.letterTileLocations.remove(loc);
			this.tileLocations.remove(loc.getValue());
			return loc.getKey();
		}

		return null;
	}
	
	/**
	 * Removes the specail tile.
	 *
	 * @param row the row
	 * @param col the col
	 * @return the special tile
	 */
	public SpecialTile removeSpecailTile(int row, int col){
		Pair<SpecialTile, Location> loc = null;
		for (Pair<SpecialTile, Location> p : this.specialTileLocations){
			if (p.getValue().getCol() == col &&
				p.getValue().getRow() == row){
				loc = p;
			}
		}
		if (loc != null){
			this.specialTileLocations.remove(loc);
			this.tileLocations.remove(loc.getValue());
			return loc.getKey();
		}	
		return null;
	}
	
	/**
	 * Gets the letter tiles.
	 *
	 * @return the letter tiles
	 */
	public ArrayList<Pair<LetterTile, Location>> getLetterTiles(){
		return this.letterTileLocations;
	}
	
	/**
	 * Gets the special tiles.
	 *
	 * @return the special tiles
	 */
	public ArrayList<Pair<SpecialTile, Location>> getSpecialTiles(){
		return this.specialTileLocations;
	}
	
	/**
	 * Gets the tile locations.
	 *
	 * @return the tile locations
	 */
	public ArrayList<Location> getTileLocations(){
		return this.tileLocations;
	}
	
	/**
	 * Gets the player.
	 *
	 * @return the player
	 */
	public Player getPlayer(){
		return this.player;
	}
	
	/**
	 * Checks if is verticle.
	 *
	 * @return true, if is verticle
	 */
	public boolean isVerticle(){
		return this.vertical;
	}
}
