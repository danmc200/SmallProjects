
public class GameOfLife
{
    public static final char 
        ALIVE = '0',
        DEAD = '.';

    private int 
    	maxR, 
    	maxC,
        aliveCount,
        deadCount;
    private char [][] grid;

    public GameOfLife(int maxR, int maxC, char [][] grid)
    {
        this.maxR = maxR;
        this.maxC = maxC;
        this.grid = grid;
    }

    public char [][] nextGrid()
    {
        aliveCount = deadCount = 0;
        char [][] nextGrid = new char [grid.length][grid[0].length];
        for(int i = 0; i < maxR; i++)
        {
            for(int j = 0; j < maxC; j++)
            {
                int nCount = getNeighborCount(i, j, maxR, maxC, grid);
                char next = getNextChar(grid[i][j] == ALIVE, nCount);
                nextGrid[i][j] = next;
                if(next == ALIVE)
                    aliveCount++;
                else
                    deadCount++;
            }
        }
        grid = nextGrid;
        return grid;
    }

    public int getAliveCount()
    {
        return aliveCount;
    }

    public int getDeadCount()
    {
        return deadCount;
    }
    
    public char [][] getGrid()
    {
    	return grid;
    }
    
    public void setGrid(char [][] grid)
    {
    	this.grid = grid;
    }

    private char getNextChar(boolean alive, int nCount)
    {
        return isNextAlive(alive, nCount) ? ALIVE : DEAD;
    }

    private boolean isNextAlive(boolean alive, int nCount)
    {
        return alive
            ? (nCount == 2 || nCount == 3)
            : (nCount == 3);
    }

    private int getNeighborCount(int row, int col, int maxR, int maxC, char [][] grid)
    {
        int count = 0;
        for (int r = -1; r < 1; r++)
        {
            for(int c = -1; c <1; c++)
            {
                if(r == 0 && c == 0)
                    continue;
                else if(row+r < 0 || row+r >= maxR || col+c < 0 || col+c >= maxC)
                    continue;
                else if(grid[row+r][col+c] == ALIVE)
                    count++;
            }
        }
        return count;
    }
}
