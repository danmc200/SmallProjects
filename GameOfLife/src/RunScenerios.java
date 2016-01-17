class RunScenerios
{
    public static final int maxR = 6, maxC = 8;

    public static void main(String [] args)
    {
    	if(args.length < 1)
    	{
    		System.out.println("provide \"game of life\" text file scenerios as a parameter");
    		System.exit(0);
    	}
    	String txtFilename = args[0];
        char [][] grid = new char [maxR][maxC];
        LifeReader lReader = new LifeReader(txtFilename);
        int count = 1;
        while(lReader.skipLine())
        {
        	grid = lReader.readNextImage(maxR);
        	printGrid(grid, "" + count);
        	
            GameOfLife game = new GameOfLife(maxR, maxC, grid);
            grid = game.nextGrid();
            printGrid(grid, "" + count);
            count++;
        }
    }
    
    public static void printGrid(char [][] grid, String label)
    {
        System.out.println(label);
        for(int i = 0; i < grid.length; i++)
        {
        	String s = "";
        	for (int j = 0; j < grid[i].length; j++)
        	{
        		s += grid[i][j];
        	}
        	System.out.println(s);
        }
    }
}
