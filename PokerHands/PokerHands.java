package PokerHands;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Poker Hands
 * @author danie
 */
public class PokerHands 
{
	private static final List<Character> 
			CARD_VALUES_RANKED = (List<Character>) Arrays.asList(
					new Character [] {'2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A'}),
			CARD_SUITS = (List<Character>) Arrays.asList(
					new Character []{'C', 'D', 'H', 'S'});
	private static final List<String> 
			CARD_VALUES_DISPLAY = (List<String>) Arrays.asList(
					new String [] {"2", "3", "4", "5", "6", "7", "8", "9", "Ten", "Jack", "Queen", "King", "Ace"}),
			CARD_SUITS_DISPLAY = (List<String>) Arrays.asList(
					new String []{"Clubs", "Diamonds", "Hearts", "Spades"});
	
	private static final int HAND_SIZE = 5;
	private static final String [] EMPTY_HAND = {null, null, null, null, null};
	
	private Map<String, Map<Score, String[]>> handResults = new HashMap<String, Map<Score, String[]>>();
	private Map<String, List<String>> hands = new HashMap<String, List<String>>();
	
	
	/*****Getters and Setters*****/
	
	/**
	 * TODO do scoring
	 * @return
	 */
	public Map<String, Map<Score, String[]>> getHandResults()
	{
		for(String s : hands.keySet())
		{
			List<String> hnd = hands.get(s);
			handResults.put(s, getScores(hnd));
		}
		return handResults;
	}
	public Map<Score, String []> getHandResult(String key)
	{
		for(String s : handResults.keySet())
		{
			if(s.equals(key))
			{
				return handResults.get(s);
			}
		}
		return null;
	}
	
	public List<String> getHand(String playerLabel)
	{
		for(String s : hands.keySet())
		{
			if(s.equals(playerLabel))
			{
				return hands.get(s);
			}
		}
		return null;
	}
	public Map<String, List<String>> getHands()
	{
		return hands;
	}
	
	public void addHand(String playerLabel, List<String> hand)
	{
		hands.put(playerLabel, hand);
	}
	public void addHand(String playerLabel, String [] hand)
	{
		addHand(playerLabel, new ArrayList<String>(Arrays.asList(hand)));
	}
	public void clearHands()
	{
		hands = new HashMap<String, List<String>>();
	}
	
	
	/******SCORING*****/
	
	/**
	 * TODO currently set for 2 players
	 * print the winner
	 * @param retHandFullBlack
	 * @param retHandFullWhite
	 */
	public void compareScores()
	{
		String winner = null;
		
		Set<String> tmpHndLbls = getHandResults().keySet();
		String [] emptyArr = new String [tmpHndLbls.size()];
		List<String> playerLabels = Arrays.asList(tmpHndLbls.toArray(emptyArr));
		Collections.sort(playerLabels);
		int result = 0; //result is index + 1 OR 0 for tie
		
		for(int i = Score.values().length - 1; i >= 0; i--)
		{
			Score currentScoreType = Score.values()[i];
			String[][] scrs = new String [playerLabels.size()][];
			for(int j = 0; j < playerLabels.size(); j++)
			{
				scrs[j] = handResults.get(playerLabels.get(j)).get(currentScoreType);
			}
			result = currentScoreType.compare(scrs);
			if(result != 0)
			{
				winner = playerLabels.get(result - 1);
				String card = handResults.get(winner).get(currentScoreType)[0];
				String displayCard = getCardDisplay(card);
				
				int result2 = result == 1 ? 2 : 1;
				String loser = playerLabels.get(result2 - 1);
				String card2 = handResults.get(loser).get(currentScoreType)[0];
				String displayCard2 = getCardDisplay(card2);
				String suffix = displayCard2 == null ? "" : " over " + displayCard2;
				
				System.out.println(winner + " wins. - with " + currentScoreType.getDescriptor() + 
						" " + displayCard + suffix);
				break;
			}
		}
		if(winner == null)
		{
			System.out.println("Tie.");
		}
		clearHands();
	}
	
	protected Map<Score, String[]> getScores(List<String> handdealt)
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
	
	/*****HELPERS*******/

	protected static String[] inputToArray(String input)
	{
		String [] handArr = new String[HAND_SIZE];
		
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
	
	protected void printCardHands()
	{
		Map<String, List<String>> hands = getHands();
		Set<String> tmpHndLbls = getHands().keySet();
		String [] emptyArr = new String [tmpHndLbls.size()];
		List<String> playerLabels = Arrays.asList(tmpHndLbls.toArray(emptyArr));
		Collections.sort(playerLabels);
		
		for(String pl : playerLabels)
		{
			System.out.println(pl + ": " + hands.get(pl).toString());
		}
	}
	
	/**
	 * 
	 * @param scoreName
	 * @return Score Enum
	 */
	protected static Score getScore(String scoreName)
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
		if(card == null)
		{
			return -1;
		}
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
	
	protected static String getCardDisplay(String card)
	{
		int indx = getCardValueIndex(card);
		if(indx == -1)
		{
			return null;
		}
		
		int indx2 = getCardSuitIndex(card);
		if(indx2 == -1)
		{
			return null;
		}
		
		return CARD_VALUES_DISPLAY.get(indx) + " of " + CARD_SUITS_DISPLAY.get(indx2);
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
	protected static int [] getHighCardValueIndex(List<String> hand)
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
	
	
	/*****CARD IDENTIFICATION*****/
	
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
		List<String> 
			handCopy = new ArrayList<String>(hand),
			handReturn = new ArrayList<String>(hand);
		
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
	
	public void scoreGameWithInput(PokerHands pokerHands, String [] args)
	{
		int count = 1;
		StringBuffer sb = new StringBuffer();
		String 
			hand="", 
			player="";
		
		for(String s : args)
		{
			sb.append(s);
			if(count == (HAND_SIZE+1))//hand size plus label
			{
				sb.append(System.lineSeparator());
				
				player = sb.substring(0, sb.indexOf(":"));
				hand = sb.substring(sb.indexOf(":") + 1);
				
				String [] handArr = inputToArray(hand);
				
				if(pokerHands.getHand(player) == null)
				{
					pokerHands.addHand(player, handArr);
				}
				else
				{
					pokerHands.printCardHands();
					
					pokerHands.compareScores();
					
					//add after game clears hands
					pokerHands.addHand(player, handArr);
				}
				sb = new StringBuffer();
				count = 0;
				System.out.println();
			}
			count++;
		}
		//**run again b/c loop design
		pokerHands.printCardHands();
		pokerHands.compareScores();
	}
	
	/**
	 * A poker deck contains 52 cards - 
	 * each card has a suit which is one of clubs, diamonds, hearts, or spades 
	 * (denoted C, D, H, and S in the input data). 
	 * Each card also has a value which is one of 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace 
	 * (denoted 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A).
	 * @param args -> "(Player Label: ) (cards)*5"
	 */
	public static void main(String [] args)
    {
		PokerHands pokerHands = new PokerHands();
		pokerHands.scoreGameWithInput(pokerHands, args);
    }
}
