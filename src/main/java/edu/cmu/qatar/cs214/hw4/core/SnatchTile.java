package edu.cmu.qatar.cs214.hw4.core;


/**
 * The Class SnatchTile.
 */
public class SnatchTile extends SpecialTile{

	/**
	 * Instantiates a new snatch tile.
	 *
	 * @param engine the engine
	 * @param p the p
	 */
	public SnatchTile( GameEngine engine, Player p) {
		super("Snatch", engine, p, 20);
	}

	/** (non-Javadoc)
	 * @see edu.cmu.qatar.cs214.hw4.core.SpecialTile#activate(edu.cmu.qatar.cs214.hw4.core.Location)
	 */
	public void activate(Location loc){
		this.engine.changeActivePlayer(this.getOwner());
	}
}
