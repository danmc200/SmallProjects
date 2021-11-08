package PokerHands;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface CardReader
{
	/**
	 * @param card -> the card to display
	 * @return String -> describing the card for display to a console
	 */
	public static String getCardDisplay(String card)
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
	public static String getCardHandsPrintable(Map<String, List<String>> hands)
	{
		Set<String> tmpHndLbls = hands.keySet();
		String [] emptyArr = new String [tmpHndLbls.size()];
		List<String> playerLabels = Arrays.asList(tmpHndLbls.toArray(emptyArr));
		StringBuffer sb = new StringBuffer();
		
		Collections.sort(playerLabels);
		for(String pl : playerLabels)
		{
			sb.append(pl + ": " + hands.get(pl).toString() + System.lineSeparator());
		}
		
		return sb.toString();
	}
	
	/**
	 * @param input -> the array of cards without any delimiters
	 * @return String [] with a size of HandIdentification.HAND_SIZE
	 */
	public static String[] inputToArray(String input)
	{
		String [] handArr = new String[HandIdentification.HAND_SIZE];
		
		for(int i = 0; i < input.length()-2; i+=2)
		{
			if(i == 0)
			{
				handArr[i] = "" + input.charAt(i) + input.charAt(i+1);
			}
			else
			{
				handArr[i/2] = "" + input.charAt(i) + input.charAt(i+1);
			}
		}
		
		return handArr;
	}
}
