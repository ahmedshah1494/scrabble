package edu.cmu.qatar.cs214.hw4.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * The Class Dictionary.
 */
public class Dictionary {
	
	/** The words. */
	private List<String> words;
	
	/**
	 * Instantiates a new dictionary.
	 *
	 * @param filename the filename
	 * @throws FileNotFoundException the file not found exception
	 */
	public Dictionary(String filename) throws FileNotFoundException{
		words = new ArrayList<String>();
		Scanner scanner = new Scanner(new File(filename));
		
		String word;
		while (scanner.hasNextLine()){
			word = scanner.nextLine();
			words.add(word.toLowerCase());
		}
		scanner.close();
	}
	
	/**
	 * Checks if is valid word.
	 *
	 * @param word the word
	 * @return true, if is valid word
	 */
	public boolean isValidWord(String word){
		return words.contains(word);
	}
}
