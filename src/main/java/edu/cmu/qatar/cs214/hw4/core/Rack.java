package edu.cmu.qatar.cs214.hw4.core;

import java.util.ArrayList;

/**
 * The Class Rack.
 */
public class Rack {
	
	/** The letter tiles. */
	private ArrayList<LetterTile> letterTiles;
	
	/** The l tiles. */
	private LetterTile[] lTiles;
	
	/** The special tiles. */
	private ArrayList<SpecialTile> specialTiles;
	
	/** The rack size. */
	private static int RACK_SIZE = 7;
	
	/**
	 * Instantiates a new rack.
	 */
	public Rack(){
		this.letterTiles = new ArrayList<LetterTile>();
		this.specialTiles = new ArrayList<SpecialTile>();
		this.lTiles = new LetterTile[RACK_SIZE];
	}
	
	/**
	 * Gets the letter tile.
	 *
	 * @param idx the idx
	 * @return the letter tile
	 */
	public LetterTile getLetterTile(int idx){
		LetterTile t = this.lTiles[idx];
		this.lTiles[idx] = null;
		return t;
	}
	
	/**
	 * Return tile.
	 *
	 * @param t the t
	 */
	public void returnTile(LetterTile t){
		for(int i = 0 ; i < this.RACK_SIZE ; i++){
			if (this.lTiles[i] == null){
				this.lTiles[i] = t;
				return;
			}
		}
	}
	
	/**
	 * Gets the special tile.
	 *
	 * @param idx the idx
	 * @return the special tile
	 */
	public SpecialTile getSpecialTile(int idx){
		SpecialTile t = specialTiles.get(idx);
		specialTiles.remove(idx);
		return t;
	}
	
	/**
	 * Return tile.
	 *
	 * @param t the t
	 */
	public void returnTile(SpecialTile t){
		specialTiles.add(t);
	}
	
	/**
	 * Gets the number of tiles.
	 *
	 * @return the number of tiles
	 */
	public int getNumberOfTiles(){
		int count = 0;
		for(int i = 0 ; i < this.RACK_SIZE ; i++){
			if (this.lTiles[i] != null){
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Gets the letter tiles list.
	 *
	 * @return the letter tiles list
	 */
	public ArrayList<LetterTile> getLetterTilesList(){
		this.letterTiles.clear();
		for(int i = 0 ; i < this.RACK_SIZE ; i++){
			if (this.lTiles[i] != null){
				letterTiles.add(this.lTiles[i]);
			}
		}
		return this.letterTiles;
	}
	
	/**
	 * Gets the letters list.
	 *
	 * @return the letters list
	 */
	public ArrayList<String> getLettersList(){
		ArrayList<String> lst = new ArrayList<String>();
		for(int i = 0 ; i < this.RACK_SIZE ; i++){
			if (this.lTiles[i] != null){
				lst.add(this.lTiles[i].getLetter());
			}
			else{
				lst.add("");
			}
		}
		return lst;
	}
	
	/**
	 * Gets the special list.
	 *
	 * @return the special list
	 */
	public ArrayList<String> getSpecialList(){
		ArrayList<String> lst = new ArrayList<String>();
		for (SpecialTile sT : this.specialTiles){
			lst.add(sT.getName());
		}
		return lst;
	}
	
	/**
	 * Gets the special tiles list.
	 *
	 * @return the special tiles list
	 */
	public ArrayList<SpecialTile> getSpecialTilesList(){
		return this.specialTiles;
	}
	
	/** (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString(){
		StringBuffer str = new StringBuffer("[");
		for (LetterTile lT : letterTiles){
			str.append(lT.getLetter()+", ");
		}
		str.append("]\n[");
		for (SpecialTile sT : specialTiles){
			str.append(sT.getName());
		}
		str.append("]");
		return str.toString();
	}
	
	/**
	 * Empty rack.
	 */
	// for testing only
	public void emptyRack(){
		this.letterTiles.clear();
		this.specialTiles.clear();
	}
}
