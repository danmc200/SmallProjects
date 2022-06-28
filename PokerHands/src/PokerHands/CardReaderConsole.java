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
		
		if(indx == -1)
		{
			return null;
		}
		
		int indx2 = HandIdentification.getCardSuitIndex(card);
		if(indx2 == -1)
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
		
		for(int i = 0; i < input.length()-2; i+=2)
		{
			int index = (i == 0) ? i : i/2;
			handArr[index] = "" + input.charAt(i) + input.charAt(i+1);
		}
		
		return handArr;
	}
}
