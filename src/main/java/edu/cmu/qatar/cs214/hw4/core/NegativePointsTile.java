package edu.cmu.qatar.cs214.hw4.core;

/**
 * The Class NegativePointsTile.
 */
public class NegativePointsTile extends SpecialTile{

	/**
	 * Instantiates a new negative points tile.
	 *
	 * @param engine the engine
	 * @param p the p
	 */
	public NegativePointsTile(GameEngine engine, Player p) {
		super("NegPoints",engine, p, 10);
	}
	
	/** (non-Javadoc)
	 * @see edu.cmu.qatar.cs214.hw4.core.SpecialTile#activate(edu.cmu.qatar.cs214.hw4.core.Location)
	 */
	@Override
	public void activate(Location loc){
		this.engine.getBoard().setScoreMultiplier(-1);
	}
}
