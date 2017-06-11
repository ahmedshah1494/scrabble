package edu.cmu.qatar.cs214.hw4.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * The Class SpecialTileShop.
 */
public class SpecialTileShop {
	
	/** The prices. */
	private HashMap<String,Integer> prices;
	
	/** The catalogue. */
	private HashMap<String, BiFunction<GameEngine,Player,SpecialTile>> catalogue;
	
	/**
	 * Instantiates a new special tile shop.
	 */
	public SpecialTileShop(){
		prices = new HashMap<String, Integer> ();
		catalogue = new HashMap<String, BiFunction<GameEngine,Player,SpecialTile>>();
		
		prices.put("Boom", 20);
		prices.put("NegPoints", 10);
		prices.put("RevOrder", 10);
		prices.put("Snatch", 30);
		
		catalogue.put("Boom", (e,p) -> new BoomTile(e,p));
		catalogue.put("NegPoints", (e,p) -> new NegativePointsTile(e,p));
		catalogue.put("RevOrder", (e,p) -> new ReversePlayerOrderTile(e,p));
		catalogue.put("Snatch", (e,p) -> new SnatchTile(e,p));
	}
	
	/**
	 * Gets the special tile price list.
	 *
	 * @return the special tile price list
	 */
	public HashMap<String,Integer> getSpecialTilePriceList(){
		return this.prices;
	}
	
	/**
	 * Gets the special tile.
	 *
	 * @param e the e
	 * @param p the p
	 * @param name the name
	 * @return the special tile
	 */
	public SpecialTile getSpecialTile(GameEngine e, Player p, String name){
		return this.catalogue.get(name).apply(e,p);
	}
	
	/**
	 * Gets the special tile price.
	 *
	 * @param name the name
	 * @return the special tile price
	 */
	public int getSpecialTilePrice(String name){
		return this.prices.get(name);
	}
}
