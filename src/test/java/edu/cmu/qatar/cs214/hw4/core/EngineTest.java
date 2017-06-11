package edu.cmu.qatar.cs214.hw4.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class EngineTest {
	@Test
	public void withEngine() throws FileNotFoundException{
		GameEngine engine = new GameEngine();
		
		assertTrue(engine.addPlayer("p1"));
		assertFalse(engine.startGame());
		assertTrue(engine.addPlayer("p2"));
		assertTrue(engine.addPlayer("p3"));
		assertTrue(engine.addPlayer("p4"));
		assertFalse(engine.addPlayer("p5"));
		assertTrue(engine.startGame());
		System.out.println(engine.playerOrderToString());
		SpecialTile sT = new ReversePlayerOrderTile(engine, new Player(" "));
		sT.activate(new Location(0,0));
		System.out.println(engine.playerOrderToString());
		sT.activate(new Location(0,0));
		System.out.println(engine.playerOrderToString());
		
		Move m = new Move(engine.getActivePlayer());
		assertTrue(m.addLetterTile(new LetterTile("a", 10), 7, 7));
		assertTrue(m.addLetterTile(new LetterTile("n", 3), 8, 7));
		assertTrue(m.addLetterTile(new LetterTile("d", 5), 9, 7));
		engine.setCurrMove(m);
		assertTrue(engine.executeMove());
		assertTrue(engine.getActivePlayer().getScore() == 18);
//		System.out.println(engine);
		engine.nextTurn();
		System.out.println(engine);
		
		System.out.println(engine.playerOrderToString());
		sT.activate(new Location(0,0));
		System.out.println(engine.playerOrderToString());
		sT.activate(new Location(0,0));
		System.out.println(engine.playerOrderToString());
		
		m = new Move(engine.getActivePlayer());
		assertTrue(m.addLetterTile(new LetterTile("c", 10), 6, 7));
		assertTrue(m.addLetterTile(new LetterTile("y", 10), 10, 7));
		engine.setCurrMove(m);
		assertTrue(engine.executeMove());
		assertTrue(engine.getActivePlayer().getScore() == 38);
		engine.nextTurn();
		System.out.println(engine);
		
		engine.removePlayer("p2");
		System.out.println(engine.playerOrderToString());
		
		m = new Move(engine.getActivePlayer());
		assertTrue(m.addLetterTile(new LetterTile("c", 10), 7, 6));
		assertTrue(m.addLetterTile(new LetterTile("n", 10), 7, 8));
		assertTrue(m.addSpecialTile(new BoomTile(engine, engine.getActivePlayer()), 7, 9));
		engine.setCurrMove(m);
		assertTrue(engine.executeMove());
		engine.nextTurn();
		System.out.println(engine);
		
		m = new Move(engine.getActivePlayer());
		assertTrue(m.addLetterTile(new LetterTile("s", 10), 7, 9));
		engine.setCurrMove(m);
		assertTrue(engine.executeMove());
		engine.nextTurn();
		System.out.println(engine);
		
		int s = engine.getActivePlayer().getScore();
		assertTrue(engine.buySpecialTile("NegPoints"));
		assertTrue(s - 10 == engine.getActivePlayer().getScore());
		
		m = new Move(engine.getActivePlayer());
		assertTrue(m.addLetterTile(new LetterTile("a", 10), 7, 7));
		assertTrue(m.addLetterTile(new LetterTile("n", 10), 7, 8));
		assertTrue(m.addSpecialTile(new NegativePointsTile(engine, engine.getActivePlayer()), 7, 9));
		engine.setCurrMove(m);
		assertTrue(engine.executeMove());
		engine.nextTurn();
		System.out.println(engine);
		
		m = new Move(engine.getActivePlayer());
		assertTrue(m.addLetterTile(new LetterTile("s", 10), 7, 9));
		engine.setCurrMove(m);
		s = engine.getActivePlayer().getScore();
		assertTrue(engine.executeMove());
		assertTrue(engine.getActivePlayer().getScore() == s - 40);
		engine.nextTurn();
		System.out.println(engine);
		
		s = engine.getActivePlayer().getScore();
		assertTrue(engine.buySpecialTile("RevOrder"));
		assertTrue(s - 10 == engine.getActivePlayer().getScore());
		
		m = new Move(engine.getActivePlayer());
		assertTrue(m.addLetterTile(new LetterTile("e", 10), 8, 9));
		assertTrue(m.addLetterTile(new LetterTile("t", 10), 9, 9));
		assertTrue(m.addSpecialTile(new ReversePlayerOrderTile(engine, engine.getActivePlayer()), 10, 9));
		engine.setCurrMove(m);
		assertTrue(engine.executeMove());
		engine.nextTurn();
		System.out.println(engine);
		
		m = new Move(engine.getActivePlayer());
		assertTrue(m.addLetterTile(new LetterTile("s", 10), 10, 9));
		engine.setCurrMove(m);
		assertTrue(engine.executeMove());
		engine.nextTurn();
		System.out.println(engine);
		
		m = new Move(engine.getActivePlayer());
		assertTrue(m.addLetterTile(new LetterTile("a", 10), 8, 6));
		assertTrue(m.addLetterTile(new LetterTile("t", 10), 9, 6));
		assertTrue(m.addSpecialTile(new SnatchTile(engine, engine.getActivePlayer()), 10, 6));
		engine.setCurrMove(m);
		assertTrue(engine.executeMove());
		engine.nextTurn();
		System.out.println(engine);
		
		Player aPlayer = engine.getActivePlayer();
		List<LetterTile> letterTiles = new ArrayList<LetterTile>(aPlayer.getRack().getLetterTilesList());
		ArrayList<Integer> l = new ArrayList<Integer> ();
		l.add(2);
		l.add(4);
		System.out.println(aPlayer.getRack().toString());
		engine.swapTiles(l);
		List<LetterTile> letterTiles2 = aPlayer.getRack().getLetterTilesList();
		System.out.println(aPlayer.getRack().toString());
		assertTrue(letterTiles2.size() == 7);
		for (int i = 0 ; i < 7 ; i ++){
			if (i == 2 || i == 4){
				assertFalse(letterTiles.get(i).equals(letterTiles2.get(i)));
			}
		}
		assertFalse(engine.getActivePlayer().equals(aPlayer));
		
		m = new Move(engine.getActivePlayer());
		assertTrue(m.addLetterTile(new LetterTile("s", 10), 10, 6));
		engine.setCurrMove(m);
		s = engine.getActivePlayer().getScore();
		Player p = engine.getActivePlayer();
		assertTrue(engine.executeMove());
		assertTrue(p.getScore() == s);
		engine.emptyBag();
		engine.getActivePlayer().getRack().emptyRack();
		engine.nextTurn();
		assertTrue(engine.getWinner() != null);
		assertTrue(engine.isGameOver());
		System.out.println(engine);
	}
}
