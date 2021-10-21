package PokerHands;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Poker Hands
 * @author danie
 *
 */
public class PokerHands 
{
	protected static List<Character> 
			CARD_VALUES_RANKED = (List<Character>) Arrays.asList(
					new Character [] {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'}),
			CARD_SUITS = (List<Character>) Arrays.asList(
					new Character []{'C', 'D', 'H', 'S'});
	private static List<String> 
			CARD_VALUES_DISPLAY = (List<String>) Arrays.asList(
					new String [] {"2", "3", "4", "5", "6", "7", "8", "9", "Ten", "Jack", "Queen", "King", "Ace"});
	private static final int HAND_SIZE = 5;
	
	private static final String [] EMPTY_HAND = {null, null, null, null, null};
	
	/******SCORING*****/
	
	protected static Map<Score, String[]> getScores(List<String> handdealt)
	{
		Map<Score, String[]> methodAndReturn = new HashMap<Score, String[]>();
		
		Class<PokerHands> pokerHandsClass = PokerHands.class;
		Class<Score> scoreClass = Score.class;
		
		Method [] pokerHandsMethods = pokerHandsClass.getDeclaredMethods();//public methods
		
		Score [] scoreEnums = (Score[]) scoreClass.getEnumConstants();
		List<String> scoreNames = new ArrayList<String>();
		for(Score sc : scoreEnums)
		{
			String name = sc.name();
			if(!name.equals("main"))
			{
				scoreNames.add(name);
			}
		}
		
		for(Method m : pokerHandsMethods)
		{
			String baseName = m.getName().replace("get", "");
			baseName = Character.toLowerCase(baseName.charAt(0)) + baseName.substring(1);
			Score score = null;
			if(scoreNames.contains(baseName))
			{
				score = PokerHands.getScore(baseName);
			}
			else 
			{
				continue;
			}
			try {
				if(!methodAndReturn.containsKey(score))
				{
					methodAndReturn.put(score, (String[]) m.invoke(pokerHandsClass, handdealt));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return methodAndReturn;
	}
	
//	public static Map<Score, String[]> getScores(List<String> handdealt)
//	{
//		Map<Score, String[]> methodAndReturn = new HashMap<Score, String[]>();
//		
//		methodAndReturn.put(Score.highCard, getHighCard(handdealt));
//		methodAndReturn.put(Score.highPairCards, getHighPairCards(handdealt));
//		methodAndReturn.put(Score.twoPair, getTwoPair(handdealt));
//		methodAndReturn.put(Score.threeOfAKind, getThreeOfAKind(handdealt));
//		methodAndReturn.put(Score.straight, getStraight(handdealt));
//		methodAndReturn.put(Score.flush, getFlush(handdealt));
//		methodAndReturn.put(Score.fullHouse, getFullHouse(handdealt));
//		methodAndReturn.put(Score.fourOfAKind, getFourOfAKind(handdealt));
//		methodAndReturn.put(Score.straightFlush, getStraightFlush(handdealt));
//		
//		return methodAndReturn;
//	}
	
	/**
	 * print the winner
	 * @param retHandFullBlack
	 * @param retHandFullWhite
	 */
	protected void compareScores(Map<Score, String[]> retHandFullBlack, Map<Score, String[]> retHandFullWhite)
	{
		for(int i = Score.values().length - 1; i >= 0; i--)
		{
			Score sc = Score.values()[i];
			String [] scoreBlack = retHandFullBlack.get(sc);
			String [] scoreWhite = retHandFullWhite.get(sc);
			
			boolean blackNull = scoreBlack[0] == (null);
			boolean whiteNull = scoreWhite[0] == (null);
			
			if(!blackNull && whiteNull)
			{
				System.out.println("Black wins. - with " + sc.getDescriptor() + ": " + 
						CARD_VALUES_DISPLAY.get(getCardValueIndex(scoreBlack[0])));
				break;
			}
			else if(!whiteNull && blackNull)
			{
				System.out.println("White wins. - with " + sc.getDescriptor() + ": " + 
						CARD_VALUES_DISPLAY.get(getCardValueIndex(scoreWhite[0])));
				break;
			}
			else if(whiteNull && blackNull)
			{
				continue;
			}
			else
			{
				int winner = sc.compare(scoreBlack, scoreWhite);
				if(winner == 0)
				{
					System.out.println("Black wins. - with " + sc.getDescriptor() + ": " + 
							CARD_VALUES_DISPLAY.get(getCardValueIndex(scoreBlack[0])) +
							" over " + CARD_VALUES_DISPLAY.get(getCardValueIndex(scoreWhite[0])));
					break;
				}
				else if (winner == 1)
				{
					System.out.println("White wins. - with " + sc.getDescriptor() + ": " + 
							CARD_VALUES_DISPLAY.get(getCardValueIndex(scoreWhite[0])) +
							" over " + CARD_VALUES_DISPLAY.get(getCardValueIndex(scoreBlack[0])));
					break;
				}
				else
				{
					System.out.println("Tie.");
					break;
				}
			}
		}
	}
	
	/*****HELPER METHODS*******/
	
	/**
	 * 
	 * @param scoreName
	 * @return Score Enum
	 */
	private static Score getScore(String scoreName)
	{
		Score score = Score.valueOf(scoreName);
		return score;
	}
	
	/**
	 * 
	 * @param card -> the card to obtain ranked value
	 * @return card ranked value
	 */
	protected static int getCardValueIndex(String card)
	{
		return CARD_VALUES_RANKED.indexOf(card.charAt(0));
	}
	
	/**
	 * 
	 * @param card -> the card to obtain suit
	 * @return
	 */
	protected static int getCardSuitIndex(String card)
	{
		return CARD_SUITS.indexOf(card.charAt(1));
	}
	
	/**
	 * 
	 * @param hand -> the hand dealt
	 * @param card -> the card to remove from hand
	 * @return the hand minus the card given
	 */
	protected static List<String> removeMatch(List<String> hand, String card)
	{
		for(int i = 0; i < hand.size(); i++)
		{
			if(hand.get(i).equals(card))
			{
				hand.remove(i);
			}
		}
		return hand;
	}
	
	/**
	 * 
	 * @param indexCardValue -> indexed card value relative to ranked card values
	 * @param indexCardSuit -> the card suit index relative to defined card suits
	 * @return the matching card in defined String form
	 */
	protected static String getCard(int indexCardValue, int indexCardSuit)
	{
		Character c1 = CARD_VALUES_RANKED.get(indexCardValue);
		Character c2 = CARD_SUITS.get(indexCardSuit);
		StringBuffer sb = new StringBuffer();
		sb.append(c1);
		sb.append(c2);
		
		return sb.toString();
	}
	
	
	/**
	 * 
	 * @param hand -> the hand which dealt
	 * @return The high card defined "value index" & "suit index"
	 */
	private static int [] getHighCardValueIndex(List<String> hand)
	{
		int index = 0;
		int indexSuit = 0;
		for(String card : hand)
		{
			int indexNew = getCardValueIndex(card);
			if(indexNew > index)
			{
				index = indexNew;
				indexSuit = getCardSuitIndex(card);
			}
		}
		return new int[] {index, indexSuit};
	}
	
	
	/*****CARD IDENTIFICATION*******/
	
	/**
	 * only 4 2s in a deck no all 2s
	 * @param hand -> the hand which dealt
	 * @return The high card defined "value index" & "suit index"
	 */
	public static String [] getHighCard(List<String> hand)
	{
		int index = 0;
		String highCard = null;
		
		for(String card : hand)
		{
			int indexNew = getCardValueIndex(card);
			if(indexNew > index)
			{
				index = indexNew;
				highCard = card;
			}
		}
		return new String [] {highCard};
	}
	
	/**
	 * TODO verify
	 * @param hand -> the hand which dealt
	 * @return The Pair of cards matching OR empty null Characters
	 */
	public static String [] getHighPairCards(List<String> hand)
	{
		List<String> handCopy = new ArrayList<String>(hand);
		String [] 
				pairCards = {null, null};
		int count = 1;
		
		for(String card : handCopy)
		{
			int indexValue = getCardValueIndex(card);
			
			String [] handArr = handCopy.toArray(new String [] {});
			List<String> handStrip = Arrays.asList(Arrays.copyOfRange(handArr, count, handCopy.size()));
			
			for(String card2 : handStrip)
			{
				int indexValue2 = getCardValueIndex(card2);
				int
					indexDiff = indexValue,
					indexDiff2 = indexValue2;
				if(pairCards[0] != null)
				{
					indexDiff = indexValue - getCardValueIndex(pairCards[0]);
					indexDiff2 = indexValue2 - getCardValueIndex(pairCards[1]);
				}
				//first match
				else if(indexValue == indexValue2)
				{
					pairCards[0] = card;
					pairCards[1] = card2;
					continue;
				}
				
				//if 2nd match, check if match is higher
				if(indexValue == indexValue2 && (indexDiff + indexDiff2) >= 1)
				{
					pairCards[0] = card;
					pairCards[1] = card2;
				}
			}
			count++;
		}
		return pairCards;
	}
	
	/**
	 * 
	 * @param hand -> the hand which dealt
	 * @return two pairs which match
	 */
	public static String [] getTwoPair(List<String> hand)
	{
		List<String> handCopy = new ArrayList<String>(hand);
		String [] 
				twoPairCards = {null, null, null, null},
				pairCards = getHighPairCards(handCopy);
		
		if(pairCards[0] != null)
		{
			twoPairCards[0] = pairCards[0];
			twoPairCards[1] = pairCards[1];
			
			//remove match
			handCopy = removeMatch(handCopy, pairCards[0]);
			handCopy = removeMatch(handCopy, pairCards[1]);
			
			pairCards = getHighPairCards(handCopy);
			
			if(pairCards[0] != (null))
			{
				twoPairCards[2] = pairCards[0];
				twoPairCards[3] = pairCards[1];
			}
			else
			{
				return EMPTY_HAND;
			}
		}
		else
		{
			return EMPTY_HAND;
		}
		
		return twoPairCards;
	}
	
	/**
	 * 
	 * @param hand -> the hand which dealt
	 * @return Three of a kind OR Empty hand
	 */
	public static String [] getThreeOfAKind(List<String> hand)
	{
		List<String> handCopy = new ArrayList<String>(hand);
		String [] 
			threeOfAKind = {null, null, null};
				
		String [] twoPair = getHighPairCards(handCopy);
		
		if(twoPair[0] != (null))
		{
			handCopy = removeMatch(handCopy, twoPair[0]);
			handCopy = removeMatch(handCopy, twoPair[1]);
			for(String card : handCopy)
			{
				if(getCardValueIndex(card) == getCardValueIndex(twoPair[0]))
				{
					threeOfAKind[0] = card;
					threeOfAKind[1] = twoPair[0];
					threeOfAKind[2] = twoPair[1];
					return threeOfAKind;
				}
			}
		}
		
		return EMPTY_HAND;
	}
	
	/**
	 * @param hand -> the hand which dealt
	 * @return Straight OR Empty Hand
	 */
	public static String [] getStraight(List<String> hand)
	{
		List<String> handCopy = new ArrayList<String>(hand);
		
		String []  
			straightCards = {null, null, null, null, null};
		int 
			indexValueHigh=0,
			indexSuitHigh=0,
			indexValueNextHigh=0,
			count = 0;
		String cardBuffer;
		
		int [] indexHigh = getHighCardValueIndex(handCopy);
		indexValueHigh = indexHigh[0];
		indexSuitHigh = indexHigh[1];
		
		cardBuffer = getCard(indexValueHigh, indexSuitHigh);
		handCopy = removeMatch(handCopy, cardBuffer);
		straightCards[count++] = cardBuffer;
		
		while(!handCopy.isEmpty())
		{
			indexHigh = getHighCardValueIndex(handCopy);
			indexValueNextHigh = indexHigh[0];
			indexSuitHigh = indexHigh[1];
			if(indexValueHigh - indexValueNextHigh != 1)
				return EMPTY_HAND;
			cardBuffer = getCard(indexValueNextHigh, indexSuitHigh);
			handCopy = removeMatch(handCopy, cardBuffer);
			straightCards[count++] = cardBuffer;
		}
		
		return straightCards;
	}
	
	/**
	 * 
	 * @param hand -> the hand which dealt
	 * @return Flush OR Empty hand
	 */
	public static String [] getFlush(List<String> hand)
	{
		List<String> handCopy = new ArrayList<String>(hand);
		
		int cardSuitIndex = getCardSuitIndex(handCopy.get(0)),//defined suit
				cardSuitIndexNext = 0;
		handCopy = removeMatch(handCopy, handCopy.get(0));
		
		while(!handCopy.isEmpty())
		{
			cardSuitIndexNext = getCardSuitIndex(handCopy.get(0));
			if(cardSuitIndex != cardSuitIndexNext)
			{
				return EMPTY_HAND;
			}
			handCopy = removeMatch(handCopy, handCopy.get(0));
		}
		
		String [] handArr = new String[] {};
		return hand.toArray(handArr);
	}
	
	/**
	 * 
	 * @param hand -> the hand which dealt
	 * @return Full House OR Empty Hand
	 */
	public static String [] getFullHouse(List<String> hand)
	{
		List<String> handCopy = new ArrayList<String>(hand);
		String [] returnFullHouse = {null, null, null, null, null};
		
		String [] threeOfAKind = getThreeOfAKind(handCopy);
		if(threeOfAKind[0] != (null))
		{
			for(String card : threeOfAKind)
			{
				handCopy = removeMatch(handCopy, card);
			}
		}
		else
		{
			return EMPTY_HAND;
		}
		
		String [] pair = getHighPairCards(handCopy);
		if(pair[0] != (null))
		{
			returnFullHouse[0] = threeOfAKind[0];
			returnFullHouse[1] = threeOfAKind[1];
			returnFullHouse[2] = threeOfAKind[2];
			
			returnFullHouse[3] = pair[0];
			returnFullHouse[4] = pair[1];
		}
		else
		{
			return EMPTY_HAND;
		}
		
		return returnFullHouse;
	}
	
	/**
	 * @param hand -> the hand which dealt
	 * @return four of a kind OR Empty Hand
	 */
	public static String [] getFourOfAKind(List<String> hand)
	{
		List<String> handCopy = new ArrayList<String>(hand);
		String [] pair = {null, null},
				fourOfAKind = {null, null, null, null};
		
		pair = getHighPairCards(handCopy);
		if(pair[0] != (null))
		{
			fourOfAKind[0] = pair[0];
			fourOfAKind[1] = pair[1];
			handCopy = removeMatch(handCopy, pair[0]);
			handCopy = removeMatch(handCopy, pair[1]);
			
			pair = getHighPairCards(handCopy);
			if(pair[0] != (null) && getCardValueIndex(fourOfAKind[0]) == getCardValueIndex(pair[0]))
			{
				fourOfAKind[2] = pair[0];
				fourOfAKind[3] = pair[1];
				handCopy = removeMatch(handCopy, pair[0]);
				handCopy = removeMatch(handCopy, pair[1]);
			}
			else
			{
				return EMPTY_HAND;
			}
		}
		else
		{
			return EMPTY_HAND;
		}
		
		return fourOfAKind;
	}
	
	/**
	 * 
	 * @param hand -> the hand which dealt
	 * @return straight flush or empty hand
	 */
	public static String [] getStraightFlush(List<String> hand)
	{
		List<String> handCopy = new ArrayList<String>(hand);
		String [] straight = getStraight(handCopy);
		
		if(straight[0] != (null))
		{
			if(getFlush(handCopy)[0] == (null))
			{
				return EMPTY_HAND;
			}
		}
		else
		{
			return EMPTY_HAND;
		}
		
		return straight;
	}
	
	/**
	 * A poker deck contains 52 cards - 
	 * each card has a suit which is one of clubs, diamonds, hearts, or spades 
	 * (denoted C, D, H, and S in the input data). 
	 * Each card also has a value which is one of 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace 
	 * (denoted 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A).
	 * @param args -> 1st 5 args are black hand, 2nd 5 args are white hand
	 */
	public static void main(String [] args)
    {
		int count = 0;
		while(count + (HAND_SIZE*2) <= args.length)
		{
			List<String> hand_full_black = Arrays.asList(Arrays.copyOfRange(args, count, count+HAND_SIZE));
			List<String> hand_full_white = Arrays.asList(Arrays.copyOfRange(args, count+HAND_SIZE, count+(HAND_SIZE*2)));
			
			System.out.println("Hand Black:");
			for (String card : hand_full_black)
			{
				System.out.print(card);
			}
			System.out.println();
			System.out.println("Hand White:");
			for (String card : hand_full_white)
			{
				System.out.print(card);
			}
			System.out.println();
			
			PokerHands pokerHands = new PokerHands();
			Map<Score, String[]> retHandFullBlack = new HashMap<Score, String[]>();
			Map<Score, String[]> retHandFullWhite = new HashMap<Score, String[]>();
			
			retHandFullBlack = PokerHands.getScores(hand_full_black);
			
			retHandFullWhite = PokerHands.getScores(hand_full_white);
			
			pokerHands.compareScores(retHandFullBlack, retHandFullWhite);
			
			System.out.println();
			count += (HAND_SIZE*2);
		}
    }
}
