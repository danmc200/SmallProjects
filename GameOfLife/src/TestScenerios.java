import static org.junit.Assert.*;

import org.junit.Test;

public class TestScenerios
{
	private static final int maxR = 6, maxC = 8;
	private GameOfLife game;
	private LifeReader lReader;
	
	public TestScenerios()
	{
		lReader = new LifeReader("life_scenerios.txt");
		lReader.skipLine();
		char [][] grid = lReader.readNextImage(maxR);
		game = new GameOfLife(maxR, maxC, grid);
	}
	
	@Test
	public void testScenerios() 
	{
		char [][] grid;
		
		game.nextGrid();
		RunScenerios.printGrid(game.getGrid(), "test1");
		assertEquals(10, game.getAliveCount());
		assertEquals(38, game.getDeadCount());
		
		lReader.skipLine();
		grid = lReader.readNextImage(maxR);
		game.setGrid(grid);
		game.nextGrid();
		RunScenerios.printGrid(game.getGrid(), "test2");
		assertEquals(4, game.getAliveCount());
		assertEquals(44, game.getDeadCount());
		
		lReader.skipLine();
		grid = lReader.readNextImage(maxR);
		game.setGrid(grid);
		game.nextGrid();
		RunScenerios.printGrid(game.getGrid(), "test3");
		assertEquals(4, game.getAliveCount());
		assertEquals(44, game.getDeadCount());
	}

}
