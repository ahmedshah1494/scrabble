package edu.cmu.qatar.cs214.hw4.core;

/**
 * The Class SpecialTile.
 */
public abstract class SpecialTile{
	
	/** The owner. */
	private Player owner;
	
	/** The cost. */
	private int cost;
	
	/** The name. */
	private String name;
	
	/** The engine. */
	protected GameEngine engine;
	
	/**
	 * Instantiates a new special tile.
	 *
	 * @param name the name
	 * @param engine the engine
	 * @param p the p
	 * @param cost the cost
	 */
	public SpecialTile(String name, GameEngine engine, Player p, int cost){
		this.owner = p;
		this.cost = cost;
		this.engine = engine;
		this.name = name;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Gets the owner.
	 *
	 * @return the owner
	 */
	public Player getOwner(){
		return this.owner;
	}
	
	/**
	 * Gets the cost.
	 *
	 * @return the cost
	 */
	public int getCost(){
		return this.cost;
	}
	
	/**
	 * Activate.
	 *
	 * @param loc the loc
	 */
	public void activate(Location loc){
		
	}
}
