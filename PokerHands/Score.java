package PokerHands;

import java.util.Arrays;

public enum Score
{
	highCard ("high card"),
	highPairCards ("pair"),
	twoPair ("two pair"),
	threeOfAKind ("Three of a kind"),
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
	
	public String getDescriptor()
	{
		return this.descriptor;
	}
	
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
	
	public static int highCardFirst(String[] ... hands)
	{
		int 
			winner = 1,
			indexValueHigh = 0,
			indexValueHighNext = 0,
			count = 1;
		
		indexValueHigh = PokerHands.getCardValueIndex(hands[0][0]);
		for(String [] hand : Arrays.copyOfRange(hands, 1, hands.length))
		{
			count++;
			indexValueHighNext = PokerHands.getCardValueIndex(hand[0]);
			if(indexValueHighNext > indexValueHigh)
			{
				winner = count;
				indexValueHigh = indexValueHighNext;
			}
			else if(indexValueHighNext == indexValueHigh)
			{
				winner = 0;
			}
		}
		return winner;
	}
}

