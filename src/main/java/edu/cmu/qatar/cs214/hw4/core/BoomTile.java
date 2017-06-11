package edu.cmu.qatar.cs214.hw4.core;


/**
 * The Class BoomTile.
 */
public class BoomTile extends SpecialTile{

	/**
	 * Instantiates a new boom tile.
	 *
	 * @param engine the engine
	 * @param p the p
	 */
	public BoomTile(GameEngine engine, Player p) {
		super("Boom", engine, p, 30);
	}
	
	/** (non-Javadoc)
	 * @see edu.cmu.qatar.cs214.hw4.core.SpecialTile#activate(edu.cmu.qatar.cs214.hw4.core.Location)
	 */
	@Override
	public void activate(Location loc){
		System.out.println("Boom! activated");
		
		int row = loc.getRow() - 2;
		int col = loc.getCol() - 2;
		BoardSquare square;
		GameBoard board = this.engine.getBoard();
		
		for (int i = row ; i < row + 6 ; i ++){
			for (int j = col ; j < col + 6 ; j++){
				if (i < 0 || i > 15 || j < 0 || j > 15){
					continue;
				}
				square = board.getSquare(i, j);
				square.removeLetterTile();
				square.removeSpecialTile();
			}
		}
	}
}
