package PokerHands;

import java.util.List;
import java.util.Map;

public interface CardReaderBase
{
	/**
	 * @param card -> the card to display
	 * @return String -> describing the card for display to a console
	 */
	public String getCardDisplay(String card);
	
	/**
	 * @param hands
	 * @return String printable to a console
	 */
	public String getCardHandsPrintable(Map<String, List<String>> hands);
	
	/**
	 * @param input -> the array of cards without any delimiters
	 * @return String [] with a size of HandIdentification.HAND_SIZE
	 */
	public String[] inputToArray(String input);
}

