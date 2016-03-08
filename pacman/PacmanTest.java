import static org.junit.Assert.*;

import org.junit.Test;

public class PacmanTest {

	@Test
	public void testMovementUp() {
		Pacman pacman = new Pacman();
		assertEquals(Pacman.PacmanDirection.PACMAN_UP, pacman.pacmanDirection);
		assertEquals(pacman.grid[1][1], 'V');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[0][1], 'V');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[2][1], 'V');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[1][1], 'V');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[0][1], 'V');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[2][1], 'V');
		pacman.print();
		System.out.println();

	}

	@Test
	public void testMovementDown() {
		Pacman pacman = new Pacman();
		pacman.changeDirection(Pacman.PacmanDirection.PACMAN_DOWN);
		assertEquals(Pacman.PacmanDirection.PACMAN_DOWN, pacman.pacmanDirection);
		assertEquals(pacman.grid[1][1], '^');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[2][1], '^');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[0][1], '^');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[1][1], '^');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[2][1], '^');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[0][1], '^');
		pacman.print();
		System.out.println();

	}
	@Test
	public void testMovementLeft() {
		Pacman pacman = new Pacman();
		pacman.changeDirection(Pacman.PacmanDirection.PACMAN_LEFT);
		assertEquals(Pacman.PacmanDirection.PACMAN_LEFT, pacman.pacmanDirection);
		assertEquals(pacman.grid[1][1], '>');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[1][0], '>');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[1][2], '>');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[1][1], '>');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[1][0], '>');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[1][2], '>');
		pacman.print();
		System.out.println();

	}
	public void testMovementRight() {
		Pacman pacman = new Pacman();
		pacman.changeDirection(Pacman.PacmanDirection.PACMAN_RIGHT);
		assertEquals(Pacman.PacmanDirection.PACMAN_RIGHT, pacman.pacmanDirection);
		assertEquals(pacman.grid[1][1], '<');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[1][2], '<');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[1][0], '<');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[1][1], '<');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[1][2], '<');
		pacman.print();
		System.out.println();

		pacman.tick();
		assertEquals(pacman.grid[1][0], '<');
		pacman.print();
		System.out.println();

	}
}
