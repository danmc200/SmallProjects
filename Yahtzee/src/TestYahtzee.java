import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TestYahtzee extends Assert
{
	@Test
	public void testScoring()
	{
		Yahtzee y = new Yahtzee();
		//large straight
		Integer [] d = {2,3,4,5,6};
		List<Integer> dice = Arrays.asList(d);
		assertEquals((y.score(dice)), 20);
		//small straight
		Integer [] d2 = {1,2,3,4,5};
		dice = Arrays.asList(d2);
		assertEquals((y.score(dice)), 15);
		//3ofkind
		Integer [] d3 = {1,3,3,3,6};
		dice = Arrays.asList(d3);
		assertEquals((y.score(dice)), 9);
		//pair
		Integer [] d4 = {1,3,3,2,6};
		dice = Arrays.asList(d4);
		assertEquals((y.score(dice)), 6);
		//2pair
		Integer [] d5 = {1,3,3,2,2};
		dice = Arrays.asList(d5);
		assertEquals((y.score(dice)), 10);
		//fullhouse
		Integer [] d6 = {3,3,3,2,2};
		dice = Arrays.asList(d6);
		assertEquals((y.score(dice)), 13);
		//4ofkind
		Integer [] d7 = {3,3,1,3,3};
		dice = Arrays.asList(d7);
		assertEquals((y.score(dice)), 12);
	}
}
