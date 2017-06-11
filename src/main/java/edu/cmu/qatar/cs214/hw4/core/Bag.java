package edu.cmu.qatar.cs214.hw4.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * The Class Bag.
 */
public class Bag {
	
	/** The tiles. */
	private ArrayList<LetterTile> tiles;
	
	/**
	 * Instantiates a new bag.
	 *
	 * @param filename the filename
	 * @throws FileNotFoundException the file not found exception
	 */
	public Bag(String filename) throws FileNotFoundException{
		Scanner scanner = new Scanner(new File(filename), "utf-8");
		this.tiles = new ArrayList<LetterTile>();
		
		String line;
		String[] values;
		String[] quant;
		int value;
		String letter;
		
		while(scanner.hasNextLine()){
			line = scanner.nextLine();
			values = line.split(":");
			quant = values[1].split(",");
			for (String s : quant){
				for (int i = 0 ; i < Integer.parseInt(s.split("\\s")[2]) ; i ++){
					value = Integer.parseInt(values[0].substring(0, values[0].length() -1));
					letter = s.split("\\s")[1];
//					System.out.println("Added " + letter + value);
					tiles.add(new LetterTile(letter, value));
				}
			}
		}
		
		scanner.close();
	}
	
	/**
	 * Fill rack.
	 *
	 * @param rack the rack
	 */
	public void fillRack(Rack rack){
		int rackSize = rack.getNumberOfTiles();
		int randInt;
		Random rand = new Random();
		int size;
		for(int i = rackSize; i < 7 ; i++){
			size = this.tiles.size();
			if (tiles.size() > 0){
				randInt = rand.nextInt(size);
				rack.returnTile(this.tiles.get(randInt));
				this.tiles.remove(randInt);
			}
		}
	}
	
	/**
	 * Gets the random tile.
	 *
	 * @return the random tile
	 */
	public LetterTile getRandomTile(){
		Random rand = new Random();
		LetterTile t = this.tiles.get(rand.nextInt(this.tiles.size()));
		this.tiles.remove(t);
		return t;
	}
	
	/**
	 * Gets the remaining tiles.
	 *
	 * @return the remaining tiles
	 */
	public int getRemainingTiles(){
		return this.tiles.size();
	}
	
	/**
	 * Swap tiles.
	 *
	 * @param rack the rack
	 * @param tiles the tiles
	 * @return true, if successful
	 */
	public boolean swapTiles(Rack rack, List<Integer> tiles){
		ArrayList<LetterTile> returnedTiles = new ArrayList<LetterTile>();
		if (this.getRemainingTiles() < 7){
			return false;
		}
		
		for (Integer i : tiles){
			returnedTiles.add(rack.getLetterTile(i));
		}
		
		for(int i = 0 ; i < tiles.size() ; i++){
			rack.returnTile(this.getRandomTile());
		}
		
		for(LetterTile t : returnedTiles){
			this.replaceTile(t);
		}
		return true;
	}
	
	/**
	 * Replace tile.
	 *
	 * @param t the t
	 */
	public void replaceTile(LetterTile t){
		this.tiles.add(t);
	}
	
	/**
	 * Checks if is empty.
	 *
	 * @return true, if is empty
	 */
	public boolean isEmpty(){
		return this.tiles.size() == 0;
	}
	
	/**
	 * Empty bag.
	 */
	public void emptyBag(){
		this.tiles.clear();
	}
}
