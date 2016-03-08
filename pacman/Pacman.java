import java.awt.Point;

public class Pacman {
	
	public char [][] grid = {{'.','.','.'},{'.','V','.'},{'.','.','.'}};
	public Point pacmanLocation = new Point(1, 1);
	public PacmanDirection pacmanDirection = PacmanDirection.PACMAN_UP; 
	
	public void move(PacmanDirection direction){
		grid[pacmanLocation.y][pacmanLocation.x] = '.';
		if((pacmanLocation.y + direction.offsetY) >= grid.length) {
			pacmanLocation.y = 0;
		}
		else if((pacmanLocation.y + direction.offsetY) < 0){
			pacmanLocation.y = grid.length -1;
		}
		else if ((pacmanLocation.x + direction.offsetX) >= grid[0].length){
			pacmanLocation.x = 0;
		}
		else if((pacmanLocation.x + direction.offsetX) < 0){
			pacmanLocation.x = grid[0].length -1;
		}
		else {
			pacmanLocation.y += direction.offsetY;
			pacmanLocation.x += direction.offsetX;
		}
		grid[pacmanLocation.y][pacmanLocation.x] = direction.representation;
	}

	public void tick(){
		move(pacmanDirection);
	}
	
	public void print()
	{
		for (int i = 0; i < grid.length; i++){
			for (int j = 0; j < grid[0].length; j++){
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
	
	public void changeDirection(PacmanDirection pacmanDirection){
		this.pacmanDirection = pacmanDirection;
		grid[pacmanLocation.y][pacmanLocation.x] = pacmanDirection.representation;
	}

public enum PacmanDirection
{
	PACMAN_UP(0,-1, 'V'),
	PACMAN_DOWN(0,1, '^'),
	PACMAN_LEFT(-1,0, '>'),
	PACMAN_RIGHT(1,0, '<');

	public final int offsetX;
	public final int offsetY;
	public final char representation;
	private PacmanDirection(int offsetX, int offsetY, char representation){
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.representation = representation;
	}
}
	
}
