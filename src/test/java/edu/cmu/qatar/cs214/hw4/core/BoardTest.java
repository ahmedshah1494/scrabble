package edu.cmu.qatar.cs214.hw4.core;

import java.io.FileNotFoundException;
import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {
	@Test
	public void boardTest() throws FileNotFoundException{
		Dictionary d = new Dictionary("/Users/Ahmed/Downloads/15-214/mshah1/homework/4/src/main/resources/myDict");
		GameBoard b = new GameBoard(d);
		Player p = new Player(" ");
		Move m = new Move(p);
		
		System.out.println(b);
		
		assertTrue(m.addLetterTile(new LetterTile("a", 10), 7, 7));
		assertTrue(m.addLetterTile(new LetterTile("n", 3), 8, 7));
		
		
		assertFalse(m.addLetterTile(new LetterTile("b", 10), 7, 8));
		assertFalse(m.addLetterTile(new LetterTile("b", 10), 9, 8));
		
		
		assertTrue(m.addLetterTile(new LetterTile("d", 5), 9, 7));

		assertTrue(b.validateMove(m));
		assertTrue(b.updateBoard(m) == 18);
		
		m = new Move(p);
		assertTrue(m.addLetterTile(new LetterTile("c", 10), 6, 7));
		assertTrue(m.addLetterTile(new LetterTile("y", 10), 10, 7));
		assertTrue(b.validateMove(m));
		assertTrue(b.updateBoard(m) == 38);
		
		m = new Move(p);
		assertTrue(m.addLetterTile(new LetterTile("s", 10), 11, 7));
		assertFalse(b.validateMove(m));
		
		m = new Move(p);
		assertTrue(m.addLetterTile(new LetterTile("c", 10), 7, 6));
		assertTrue(m.addLetterTile(new LetterTile("n", 10), 7, 8));
		assertTrue(b.validateMove(m));
		assertTrue(b.updateBoard(m) == 30);
		
		m = new Move(p);
		assertTrue(m.addLetterTile(new LetterTile("d", 6), 7, 9));
		assertTrue(m.addLetterTile(new LetterTile("l", 3), 7,10));
		assertTrue(m.addLetterTile(new LetterTile("e", 9), 7, 11));
		assertTrue(b.validateMove(m));
		assertTrue(b.updateBoard(m) == 57);
		
		m = new Move(p);
		assertTrue(m.addLetterTile(new LetterTile("l", 3), 6, 11));
		assertTrue(m.addLetterTile(new LetterTile("t", 5), 8, 11));
		assertTrue(b.validateMove(m));
		assertTrue(b.updateBoard(m) == 17);
		
		m = new Move(p);
		assertTrue(m.addLetterTile(new LetterTile("c", 3), 0, 0));
		assertTrue(m.addLetterTile(new LetterTile("a", 5), 0, 1));
		assertTrue(m.addLetterTile(new LetterTile("n", 5), 0, 2));
		assertFalse(b.validateMove(m));
		
		
		System.out.println(b);
		
		Bag bag = new Bag("/Users/Ahmed/Downloads/15-214/mshah1/homework/4/src/main/resources/scrabbleTiles");
	}
}
