import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Yahtzee 
{
	//pair
	private List<Integer> pair(List<Integer> dice)
	{
		Collections.sort(dice);
		Collections.reverse(dice);
		List<Integer> pair = new ArrayList<Integer>();
		int last = 0;
		for (int d : dice)
		{
			if(d == last)
			{
				pair.add(d);
				pair.add(last);
				break;
			}
			last = d;
		}
		return pair;
	}
	//two pairs
	private List<Integer> twoPair(List<Integer> dice)
	{
		Collections.sort(dice);
		Collections.reverse(dice);
		List<Integer> pairs = new ArrayList<Integer>();
		int last = 0;
		int count = 0;
		for (int d : dice)
		{
			if(d == last)
			{
				pairs.add(d);
				pairs.add(last);
				last = 0;
				count++;
				if (count == 2)
				{
					return pairs;
				}
			}
			else 
			{
				last = d;
			}
		}
		return new ArrayList<Integer>();
	}
	//3 of a kind
	private List<Integer> threeOfAKind(List<Integer> dice)
	{
		Collections.sort(dice);
		Collections.reverse(dice);
		for (int i = 0; i < dice.size() - 3; i++)
		{
			int first = dice.get(i);
			int second = dice.get(i+1);
			int third = dice.get(i+2);
			if (first == second && second == third)
			{
				Integer [] d = new Integer [] {first, second, third};
				return Arrays.asList(d);
			}
		}
		return new ArrayList<Integer>();
	}

	//4 of a kind
	private List<Integer> fourOfAKind(List<Integer> dice)
	{
		Collections.sort(dice);
		Collections.reverse(dice);
		for (int i = 0; i < dice.size() - 4; i++)
		{
			int first = dice.get(i);
			int second = dice.get(i+1);
			int third = dice.get(i+2);
			int fourth = dice.get(i+3);
			if (first == second && second == third && third == fourth)
			{
				Integer [] d = new Integer [] {first, second, third, fourth};
				return Arrays.asList(d);
			}
		}
		return new ArrayList<Integer>();
	}
	//small straight
	private List<Integer> smallStraight(List<Integer> dice)
	{
		Collections.sort(dice);
		Integer [] col = new Integer [] {1,2,3,4,5};
		List<Integer> sStraight = Arrays.asList(col);
		return dice.containsAll(sStraight) ? dice : new ArrayList<Integer>();
	}
	//large straight
	private List<Integer> largeStraight(List<Integer> dice)
	{
		Collections.sort(dice);
		Integer [] col = new Integer [] {2,3,4,5,6};
		List<Integer> sStraight = Arrays.asList(col);
		return dice.containsAll(sStraight) ? dice : new ArrayList<Integer>();
	}
	//full house
	private List<Integer> fullHouse(List<Integer> dice)
	{
		List<Integer> dice2 = new ArrayList<Integer>(dice);
		List<Integer> dice3 = new ArrayList<Integer>(dice);
		Collections.copy(dice2, dice);
		Collections.copy(dice3, dice);
		List<Integer> twoPair = twoPair(dice2);
		List<Integer> threeOfAKind = threeOfAKind(dice3);
		return (!twoPair.isEmpty() && !threeOfAKind.isEmpty() && 
				(twoPair.get(0) != threeOfAKind.get(0) || twoPair.get(2) != threeOfAKind.get(0)))
			? dice : new ArrayList<Integer>();
	}

	//yahtzee
	private List<Integer> yahtzee(List<Integer> dice)
	{
		int first = dice.get(0);
		for (int d : dice)
		{
			if(d != first)
			{
				return new ArrayList<Integer>();
			}
		}
		return dice;
	}
	//chance??
	
	private int sum(List<Integer> dice)
	{
		int sum = 0;
		for (int d : dice)
		{
			sum += d;
		}
		return sum;
	}
	
	public int score(List<Integer> dice)
	{
		int score = 0;
		List<Integer> d = new ArrayList<Integer>();
		d = (pair(dice));
		if(!d.isEmpty())
		{
			score = sum(d);
		}
		d = (twoPair(dice));
		if(!d.isEmpty())
		{
			score = sum(d);
		}
		d = (threeOfAKind(dice));
		if(!d.isEmpty())
		{
			score = sum(d);
		}
		d = (fourOfAKind(dice));
		if(!d.isEmpty())
		{
			score = sum(d);
		}
		d = (smallStraight(dice));
		if(!d.isEmpty())
		{
			score = 15;
		}
		d = (largeStraight(dice));
		if(!d.isEmpty())
		{
			score = 20;
		}
		d = (fullHouse(dice));
		if(!d.isEmpty())
		{
			score = sum(d);
		}
		d = (yahtzee(dice));
		if(!d.isEmpty())
		{
			score = 50;
		}
		return score;
	}
}