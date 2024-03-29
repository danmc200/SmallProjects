package PokerHands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Poker Hands 
 * Holds the card hands of multiple players 
 * Provides scoring of players' hands
 * @author danie
 */
public class PokerHands implements HandIdentification
{
	private static final String TIE_RESULT_DISPLAY = "Tie.";
	private static final int TIE_RESULT = 0;
	
	private Map<String, Map<Score, String[]>> handResults = new HashMap<String, Map<Score, String[]>>();
	private Map<String, List<String>> hands = new HashMap<String, List<String>>();
	
	private CardReaderBase cardReader = null;
	
	public PokerHands(CardReaderBase cardReader)
	{
		setCardReader(cardReader);
	}
	
	
	/**
	 * A poker deck contains 52 cards - 
	 * each card has a suit which is one of clubs, diamonds, hearts, or spades 
	 * (denoted C, D, H, and S in the input data). 
	 * Each card also has a value which is one of 2, 3, 4, 5, 6, 7, 8, 9, 10, jack, queen, king, ace 
	 * (denoted 2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A).
	 * @param args -> "(Player Label: ) (cards)*5"
	 */
	public void scoreGameWithInput(String [] args)
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
				
				String [] handArr = getCardReader().inputToArray(hand);
				
				if(this.getHand(player) == null)
				{
					this.addHand(player, handArr);
				}
				else
				{
					System.out.println(getCardReader().getCardHandsPrintable(getHands()));
					
					this.compareScores();
					
					//add after game clears hands
					this.addHand(player, handArr);
				}
				sb = new StringBuffer();
				count = 0;
				System.out.println();
			}
			count++;
		}
		//**run again b/c loop design
		System.out.println(getCardReader().getCardHandsPrintable(getHands()));
		this.compareScores();
	}
	
	/******SCORING*****/
	
	/**
	 * print the winner
	 */
	public void compareScores()
	{
		String 
			winner = null,
			card = null,
			displayCard = null,
			winner2 = null,
			card2 = null,
			displayCard2 = null;
		Set<String> tmpHndLbls = collectHandResults().keySet();
		String [] emptyArr = new String [tmpHndLbls.size()];
		List<String> playerLabels = Arrays.asList(tmpHndLbls.toArray(emptyArr));
		Collections.sort(playerLabels);
		
		for(int i = Score.values().length - 1; i >= 0; i--)
		{
			Score currentScoreType = Score.values()[i];
			winner = compareScores(currentScoreType);
			
			if(winner != null)
			{
				if(winner.equals(TIE_RESULT_DISPLAY))
				{
					//just print "Tie."
					System.out.println(winner);
				}
				else
				{
					//collect 1st result / winner display values
					card = getHandResults().get(winner).get(currentScoreType)[0];
					displayCard = getCardReader().getCardDisplay(card);
					
					//collect 2nd result / runner up display values
					winner2 = compareScores(currentScoreType, winner);
					if(winner2 != null)
					{
						card2 = getHandResults().get(winner2).get(currentScoreType)[0];
						displayCard2 = getCardReader().getCardDisplay(card2);
					}
					String suffix = displayCard2 == null ? "" : " over " + displayCard2;
					
					System.out.println(winner + " wins. - with " + currentScoreType.getDescriptor() + 
							" " + displayCard + suffix);
				}
				
				break;
			}
		}
		if(winner == null)
		{
			System.out.println("Tie. no compares completed.");
		}
		clearHands();
	}
	
	/**
	 * 
	 * @param currentScoreType -> Score type to compare
	 * @param playerLabel -> pass player label to not include / remove from comparison. runner up?
	 * @param winner
	 * @return
	 */
	public String compareScores(Score currentScoreType, String ... playerLabel)
	{
		String winner = null;
		int result = TIE_RESULT;
		
		//remove those not to compare
		Map<String, Map<Score, String[]>> handResultsCopy = new HashMap<String, Map<Score, String[]>> (getHandResults());;
		if(playerLabel != null)
			for(String label : playerLabel)
				handResultsCopy = new HashMap<String, Map<Score, String[]>> (HandIdentification.removeHandResultsMatch(getHandResults(), label));
		
		Set<String> tmpHndLbls = handResultsCopy.keySet();
		String [] emptyArr = new String [tmpHndLbls.size()];
		List<String> playerLabels = Arrays.asList(tmpHndLbls.toArray(emptyArr));
		Collections.sort(playerLabels);
		
		String[][] scrs = new String [playerLabels.size()][];
		
		for(int j = 0; j < playerLabels.size(); j++)
		{
			scrs[j] = getHandResults().get(playerLabels.get(j)).get(currentScoreType);
		}
		result = currentScoreType.compare(scrs);
		
		if(result > TIE_RESULT)
		{
			//collect 1st result / winner display values
			winner = playerLabels.get(result - 1);
		}
		else if(result == TIE_RESULT)
		{
			winner = TIE_RESULT_DISPLAY;
		}
		
		return winner;
		
	}
	/**
	 * 
	 * @param currentScoreType -> Score type to compare
	 * @param playerLabel -> pass player label to not include / remove from comparison
	 * @param winner
	 * @return
	 */
	public String compareScores(Score currentScoreType)
	{
		return compareScores(currentScoreType, (String []) null);
	}
	
	/*****Getters and Setters*****/
	
	protected CardReaderBase getCardReader()
	{
		if(cardReader == null)
		{
			cardReader = new CardReaderConsole();
		}
		
		return cardReader;
	}
	protected void setCardReader(CardReaderBase cardReaderChoice)
	{
		if(cardReaderChoice != null)
		{
			cardReader = cardReaderChoice;
		}
	}
	
	/**
	 * collects and returns the score results of each hand
	 * @return the result of each hand dealt.
	 */
	protected Map<String, Map<Score, String[]>> collectHandResults()
	{
		for(String s : hands.keySet())
		{
			List<String> hnd = hands.get(s);
			getHandResults().put(s, HandIdentification.getScores(hnd));
		}
		return getHandResults();
	}
	
	/**
	 * @param key -> the key to match to gain result of hand dealt.
	 * @return the result of hand dealt.
	 */
	protected Map<Score, String []> getHandResult(String key)
	{
		for(String s : getHandResults().keySet())
		{
			if(s.equals(key))
			{
				return getHandResults().get(s);
			}
		}
		return null;
	}
	
	/**
	 * @return the current calculated results of all hands dealt.
	 */
	protected Map<String, Map<Score, String[]>> getHandResults()
	{
		return handResults;
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
		handResults = new HashMap<String, Map<Score, String[]>>();
	}
	
}
