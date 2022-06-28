package PokerHands;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface HandIdentification 
{
	public static final List<Character> 
		CARD_VALUES_RANKED = (List<Character>) Arrays.asList(
				new Character [] {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'}),
		CARD_SUITS = (List<Character>) Arrays.asList(
				new Character []{'C', 'D', 'H', 'S'});
	public static final List<String> 
		CARD_VALUES_DISPLAY = (List<String>) Arrays.asList(
				new String [] {"2", "3", "4", "5", "6", "7", "8", "9", "Ten", "Jack", "Queen", "King", "Ace"}),
		CARD_SUITS_DISPLAY = (List<String>) Arrays.asList(
				new String []{"Clubs", "Diamonds", "Hearts", "Spades"});
	
	public static final int HAND_SIZE = 5;
	public static final String [] EMPTY_HAND = {null, null, null, null, null};
	
	/**
	 * @param handdealt
	 * @return Map with the the "Score" enum types and resulting hands
	 */
	public static Map<Score, String[]> getScores(List<String> handdealt)
	{
		Map<Score, String[]> methodAndReturn = new HashMap<Score, String[]>();
		Class<HandIdentification> handIdentificationClass = HandIdentification.class;
		Class<Score> scoreClass = Score.class;
		Method [] handIdentificationMethods = handIdentificationClass.getDeclaredMethods();//public methods
		List<String> scoreNames = new ArrayList<String>();
		Score [] scoreEnums = (Score[]) scoreClass.getEnumConstants();
		
		for(Score sc : scoreEnums)
		{
			String name = sc.name();
			if(!name.equals("main"))
			{
				scoreNames.add(name);
			}
		}
		
		Score score = null;
		for(Method m : handIdentificationMethods)
		{
			String baseName = m.getName().replace("get", "");
			baseName = Character.toLowerCase(baseName.charAt(0)) + baseName.substring(1);
			if(scoreNames.contains(baseName))
			{
				score = Score.getScore(baseName);
				try {
					if(!methodAndReturn.containsKey(score))
					{
						methodAndReturn.put(score, (String[]) m.invoke(handIdentificationClass, handdealt));
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return methodAndReturn;
	}
	
	/**
	 * @param indexCardValue -> indexed card value relative to ranked card values
	 * @param indexCardSuit -> the card suit index relative to defined card suits
	 * @return the matching card in defined String form
	 */
	public static String getCard(int indexCardValue, int indexCardSuit)
	{
		StringBuffer card = new StringBuffer();
		
		Character c1 = CARD_VALUES_RANKED.get(indexCardValue);
		Character c2 = CARD_SUITS.get(indexCardSuit);

		card.append(c1);
		card.append(c2);
		
		return card.toString();
	}
	
	/**
	 * @param hand -> the hand which dealt
	 * @return The high card defined "value index" & "suit index"
	 */
	public static int [] getHighCardValueIndex(List<String> hand)
	{
		int 
			index = -1,
			indexSuit = -1;
		
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
	
	/**
	 * @param card -> the card to obtain suit
	 * @return the card suit index relative to defined card suits
	 */
	public static int getCardSuitIndex(String card)
	{
		if(card == null)
		{
			return -1;
		}
		return CARD_SUITS.indexOf(card.charAt(1));
	}
	
	public static int getCardValueIndex(String card)
	{
		if(card == null)
		{
			return -1;
		}
		return CARD_VALUES_RANKED.indexOf(card.charAt(0));
	}
	
	/**
	 * @param hand -> the hand dealt
	 * @param card -> the card to remove from hand
	 * @return the hand minus the card given
	 */
	public static List<String> removeMatch(List<String> hand, String card)
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
	 * @param handResults
	 * @param playerLabel
	 * @return
	 */
	public static Map<String, Map<Score, String[]>> removeHandResultsMatch(Map<String, Map<Score, String[]>> handResults, String playerLabel)
	{
		Map<String, Map<Score, String[]>> handResultsCopy = new HashMap<String, Map<Score, String[]>> (handResults);
		Set<String> playerLabelsSet = handResultsCopy.keySet();
		String [] emptyArr = new String [playerLabelsSet.size()];
		List<String> playerLabels = Arrays.asList(playerLabelsSet.toArray(emptyArr));
		Collections.sort(playerLabels);
		
		for(int i = 0; i < playerLabels.size(); i++)
		{
			if(playerLabels.get(i).equals(playerLabel))
			{
				handResultsCopy.remove(playerLabels.get(i));
			}
		}
		return handResultsCopy;
	}
	
	
	
	/*****HAND IDENTIFICATION*****/
	
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
	 * @param hand -> the hand which dealt
	 * @return Three of a kind OR Empty hand
	 */
	public static String [] getThreeOfAKind(List<String> hand)
	{
		List<String> handCopy = new ArrayList<String>(hand);
		String [] 
			threeOfAKind = {null, null, null},
			twoPair = getHighPairCards(handCopy);
		
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
			indexValueHigh = indexValueNextHigh;
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
		List<String> 
			handCopy = new ArrayList<String>(hand),
			handReturn = new ArrayList<String>();
		int [] highCardIndexes = getHighCardValueIndex(handCopy);
		int 
			cardValueIndex = highCardIndexes[0],
			cardSuitIndex = highCardIndexes[1],
			cardSuitIndexNext = 0;
		
		String card = getCard(cardValueIndex, cardSuitIndex);
		
		handReturn.add(card);
		handCopy = removeMatch(handCopy, card);
		
		while(!handCopy.isEmpty())
		{
			highCardIndexes = getHighCardValueIndex(handCopy);
			cardValueIndex = highCardIndexes[0];
			cardSuitIndexNext = highCardIndexes[1];
			
			card = getCard(cardValueIndex, cardSuitIndexNext);
			
			handReturn.add(card);
			handCopy = removeMatch(handCopy, card);
			
			if(cardSuitIndex != cardSuitIndexNext)
			{
				return EMPTY_HAND;
			}
		}
		
		return handReturn.toArray(new String [handReturn.size()]);
	}
	
	/**
	 * 
	 * @param hand -> the hand which dealt
	 * @return Full House OR Empty Hand
	 */
	public static String [] getFullHouse(List<String> hand)
	{
		List<String> handCopy = new ArrayList<String>(hand);
		String [] 
				returnFullHouse = {null, null, null, null, null},
				threeOfAKind = getThreeOfAKind(handCopy);
		
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
		String [] 
				pair = {null, null},
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
}
