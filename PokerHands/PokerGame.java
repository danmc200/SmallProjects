package PokerHands;

/**
 * Poker Game 
 * A result generator for a Poker Game.
 * @author danie
 */
public class PokerGame 
{
	/**
	 * @param args
	 * example:
	 * Black: 2H 3D 5S 9C KD Red: 2D 3S 5D 4D 6D White: 2C 3H 4S 8C AH Grey: 2H 4S 4C 2A 5H
	 * Black: 2H 3D 5S 9C KD White: 2C 3H 4S 8C AH
	 * Black: 2H 4S 4C 2D 4H White: 2S 8S AS QS 3S
	 * Black: 2H 3D 5S 9C KD White: 2C 3H 4S 8C KH Grey: 2H 2S 4H 2D 5H
	 * Black: 2H 3D 5S 9C KD White: 2D 3H 5C 9S KH
	 */
	public static void main(String [] args)
    {
		PokerHands pokerHands = new PokerHands(new CardReaderConsole());
		pokerHands.scoreGameWithInput(pokerHands, args);
    }
}
