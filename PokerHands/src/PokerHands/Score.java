package PokerHands;

import java.util.HashMap;
import java.util.Map;

public enum Score
{
	highCard ("high card"),
	highPairCards ("pair"),
	twoPair ("two pair"),
	threeOfAKind ("three of a kind"),
	straight ("straight"),
	flush ("flush"),
	fullHouse ("full house"),
	fourOfAKind ("four of a kind"),
	straightFlush ("straight flush");
	
	private String descriptor = ""; 
	
	private Score(String descriptor)
	{
		this.descriptor = descriptor;
	}
	
	/**
	 * @return a text description of the type of hand
	 */
	public String getDescriptor()
	{
		return this.descriptor;
	}
	
	/**
	 * determine which hand has the highest card value on top.
	 * @param hands -> array containing card hands.
	 * @return index of hand with highest card, 0 for tie and -1 for neither
	 */
	public int compare(String[] ... hands)
	{
		int winner = 0;
		int ordinal = this.ordinal();
		switch(ordinal)
		{
		case 0://highCard
			winner = highCardFirst(hands);
			break;
		case 1://highPairCards
			winner = highCardFirst(hands);
			break;
		case 2://twoPair
			winner = highCardFirst(hands);
			break;
		case 3://threeOfAKind
			winner = highCardFirst(hands);
			break;
		case 4://straight
			winner = highCardFirst(hands);
			break;
		case 5://flush
			winner = highCardFirst(hands);
			break;
		case 6://fullHouse *make sure 3pair is high...
			winner = highCardFirst(hands);
			break;
		case 7://fourOfAKind
			winner = highCardFirst(hands);
			break;
		case 8://strightFlush
			winner = highCardFirst(hands);
			break;
		}
		
		return winner;
	}
	
	/**
	 * return the hand with the highest card in it.
	 * assuming the hands are sorted with highest on top (0 index)
	 * @param hands -> array containing card hands.
	 * @return index of hand with highest card, 0 for tie and -1 for neither
	 */
	public static int highCardFirst(String[] ... hands)
	{
		int 
			winner = -1,
			indexValueHigh = -1,
			indexValueHighNext = -1;
		Map<Integer, String[]> handsCopy = new HashMap<Integer, String[]>();
		
		for(int i = 0; i < hands.length; i++)
		{
			indexValueHighNext = HandIdentification.getCardValueIndex(hands[i][0]);
			if(indexValueHighNext != -1)
			{
				handsCopy.put(i+1, hands[i]);
			}
		}
		if(handsCopy.size() < 1)
			return -1;
		
		for(Integer key : handsCopy.keySet())
		{
			indexValueHighNext = HandIdentification.getCardValueIndex(handsCopy.get(key)[0]);
			if(indexValueHighNext > indexValueHigh)
			{
				winner = key;
				indexValueHigh = indexValueHighNext;
			}
			else if(indexValueHighNext == indexValueHigh)
			{
				winner = 0;
			}
		}
		return winner;
	}
	
	/**
	 * @param scoreName
	 * @return Score Enum
	 */
	public static Score getScore(String scoreName)
	{
		Score score = Score.valueOf(scoreName);
		return score;
	}
}

