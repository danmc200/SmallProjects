package PokerHands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Poker Game 
 * A result generator for a Poker Game.
 * @author danie
 */
public class PokerHands implements HandIdentification
{
	private Map<String, Map<Score, String[]>> handResults = new HashMap<String, Map<Score, String[]>>();
	private Map<String, List<String>> hands = new HashMap<String, List<String>>();
	
	
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
	
	/**
	 * A poker deck contains 52 cards - 
	 * each card has a suit which is one of clubs, diamonds, hearts, or spades 
	 * (denoted C, D, H, and S in the input data). 
	 * Each card also has a value which is one of 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace 
	 * (denoted 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A).
	 * @param args -> "(Player Label: ) (cards)*5"
	 */
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
			if(count == (HandIdentification.HAND_SIZE+1))//hand size plus label
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
		
		Set<String> tmpHndLbls = collectHandResults().keySet();
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
			if(result > 0)
			{
				winner = playerLabels.get(result - 1);
				String card = handResults.get(winner).get(currentScoreType)[0];
				String displayCard = HandIdentification.getCardDisplay(card);
				
				int result2 = result == 1 ? 2 : 1;
				String loser = playerLabels.get(result2 - 1);
				String card2 = handResults.get(loser).get(currentScoreType)[0];
				String displayCard2 = HandIdentification.getCardDisplay(card2);
				String suffix = displayCard2 == null ? "" : " over " + displayCard2;
				
				System.out.println(winner + " wins. - with " + currentScoreType.getDescriptor() + 
						" " + displayCard + suffix);
				break;
			}
			else if(result == 0)
			{
				winner = "Tie.";
				System.out.println(winner);
				break;
			}
		}
		if(winner == null)
		{
			System.out.println("Tie." + " no compares completed.");
		}
		clearHands();
	}
	
	/*****Getters and Setters*****/
	
	/**
	 * collects and returns the score results of each hand
	 * @return the result of each hand dealt.
	 */
	protected Map<String, Map<Score, String[]>> collectHandResults()
	{
		for(String s : hands.keySet())
		{
			List<String> hnd = hands.get(s);
			handResults.put(s, HandIdentification.getScores(hnd));
		}
		return handResults;
	}
	
	/**
	 * @param key -> the key to match to gain result of hand dealt.
	 * @return the result of hand dealt.
	 */
	protected Map<Score, String []> getHandResult(String key)
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
	
	/**
	 * @param playerLabel -> player label to match to give hand dealt.
	 * @return hand dealt.
	 */
	protected List<String> getHand(String playerLabel)
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
	
	protected Map<String, List<String>> getHands()
	{
		return hands;
	}
	
	protected void addHand(String playerLabel, List<String> hand)
	{
		hands.put(playerLabel, hand);
	}
	
	protected void addHand(String playerLabel, String [] hand)
	{
		addHand(playerLabel, new ArrayList<String>(Arrays.asList(hand)));
	}
	
	protected void clearHands()
	{
		hands = new HashMap<String, List<String>>();
	}
	
	/*****HELPERS*******/

	protected static String[] inputToArray(String input)
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
	
}
