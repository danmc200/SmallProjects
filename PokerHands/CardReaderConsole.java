package PokerHands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CardReaderConsole implements CardReaderBase
{
	/**
	 * @param card -> the card to display
	 * @return String -> describing the card for display to a console
	 */
	public String getCardDisplay(String card)
	{
		
		int indx = HandIdentification.getCardValueIndex(card);
		int indx2 = HandIdentification.getCardSuitIndex(card);
		
		if(indx == -1 || indx2 == -1)
		{
			return null;
		}
		
		return HandIdentification.CARD_VALUES_DISPLAY.get(indx) + " of " + HandIdentification.CARD_SUITS_DISPLAY.get(indx2);
	}
	
	/**
	 * @param hands
	 * @return String printable to a console
	 */
	public String getCardHandsPrintable(Map<String, List<String>> hands)
	{
		StringBuffer cardsPrintable = new StringBuffer();
		
		Set<String> tmpHndLbls = hands.keySet();
		String [] emptyArr = new String [tmpHndLbls.size()];
		List<String> playerLabels = Arrays.asList(tmpHndLbls.toArray(emptyArr));
		Collections.sort(playerLabels);
		
		for(String pl : playerLabels)
		{
			cardsPrintable.append(pl + ": " + hands.get(pl).toString() + System.lineSeparator());
		}
		
		return cardsPrintable.toString();
	}
	
	/**
	 * @param input -> the array of cards without any delimiters
	 * @return String [] with a size of HandIdentification.HAND_SIZE
	 */
	public String[] inputToArray(String input)
	{
		String [] handArr = new String[HandIdentification.HAND_SIZE];
		
		int 
			i = 0,
			index = 0;
		do
		{
			handArr[index] = "" + input.charAt(i) + input.charAt(i+1);
			i+=2;
			index = i/2;
		} while(i < input.length()-2);
		
		return handArr;
	}
}
