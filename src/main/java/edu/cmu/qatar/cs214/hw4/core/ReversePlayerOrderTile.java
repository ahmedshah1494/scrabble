package edu.cmu.qatar.cs214.hw4.core;

import java.util.ArrayList;
import java.util.Queue;


/**
 * The Class ReversePlayerOrderTile.
 */
public class ReversePlayerOrderTile extends SpecialTile{

	/**
	 * Instantiates a new reverse player order tile.
	 *
	 * @param engine the engine
	 * @param p the p
	 */
	public ReversePlayerOrderTile(GameEngine engine, Player p) {
		super("RevOrder", engine, p, 10);

	}
	
	/**
	 * @see edu.cmu.qatar.cs214.hw4.core.SpecialTile#activate(edu.cmu.qatar.cs214.hw4.core.Location)
	 */
	public void activate(Location loc){
		Queue<Player> order = this.engine.getPlayerOrder();
		ArrayList<Player> temp = new ArrayList<Player>();
		while(!order.isEmpty()){
			temp.add(order.poll());
		}
		for(int i = temp.size() - 2 ; i >= 0 ; i--){
			order.add(temp.get(i));
		}
		order.add(temp.get(temp.size() - 1));
		this.engine.setPlayerOrder(order);
	}
}
