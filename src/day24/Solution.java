package day24;

import java.util.TreeSet;

public class Solution {

	public static final String INPUT = "#..###.#..#...###..##..##";
	public static final String TEST = "....##..#.#..##..#..#....";

	public static void main(String[] args){
		SimpleEris se = new SimpleEris();
		se.readFromString(INPUT);

		TreeSet<String> states = new TreeSet<>();
		String prevState = se.gridToString();
		while(!states.contains(prevState)){
			states.add(prevState);
			se.doStep();
			prevState = se.gridToString();
		}

		int result = se.getScore();
		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + result + "\n");


		Eris e = new Eris();
		e.readFromString(INPUT);
		for(int i = 0; i < 200; i++)
			e.doStep();
		result = e.getScore();
		System.out.println("Part 2 - Success");
		System.out.println("Answer\t" + result);
	}
}

class Eris{

	boolean[][] grid;

	private Eris above;
	private Eris below;

	private int level;

	public Eris(){
		this(0, null);
	}

	public Eris(int level, Eris p){
		this.grid = new boolean[5][5];
		this.level = level;
		if(this.level < 0)
			this.above = p;
		else if(this.level > 0)
			this.below = p;
	}

	public void doStep(){
		this.doStep(0);
	}

	public void doStep(int calledFrom){
		if(this.above == null || this.below == null) {
			// Check if we have to create another level
			int outside = 0;
			int inside = 0;
			for (int x = 0; x < 5; x++) {
				for (int y = 0; y < 5; y++) {
					if((x == 0 || x == 4 || y == 0 || y == 4) && this.grid[x][y])
						outside++;
					if(((x == 2 && (y == 1 || y == 3)) || (y == 2 && (x == 1 || x == 3))) && this.grid[x][y])
						inside++;
				}
			}

			if(this.above == null && outside > 0)
				this.above = new Eris(this.level+1, this);
			if(this.below == null && inside > 0)
				this.below = new Eris(this.level-1, this);
		}

		boolean[][] newGrid = new boolean[5][5];
		for(int x = 0; x < 5; x++){
			for(int y = 0; y < 5; y++){
				if(x == 2 && y == 2)
					continue;

				int bugs = 0;
				// Check bugs on our local grid
				if(x > 0 && !(x == 3 && y == 2) && grid[x-1][y])
					bugs++;
				if(y > 0 && !(x == 2 && y == 3) && grid[x][y-1])
					bugs++;
				if(x < 4 && !(x == 1 && y == 2) && grid[x+1][y])
					bugs++;
				if(y < 4 && !(x == 2 && y == 1) && grid[x][y+1])
					bugs++;

				// Check bugs on the above grid
				if(this.above != null){
					if(y == 0)
						bugs += this.above.getOutside(0);
					if(x == 4)
						bugs += this.above.getOutside(1);
					if(y == 4)
						bugs  += this.above.getOutside(2);
					if(x == 0)
						bugs += this.above.getOutside(3);
				}
				// Check bugs on the below grid
				if(this.below != null){
					if(x == 2 && y == 1)
						bugs += this.below.getInside(0);
					else if(x == 3 && y == 2)
						bugs += this.below.getInside(1);
					else if(x == 2 && y == 3)
						bugs += this.below.getInside(2);
					else if(x == 1 && y == 2)
						bugs += this.below.getInside(3);
				}

				if(grid[x][y] && bugs != 1)
					newGrid[x][y] = false;
				else if(!grid[x][y] && (bugs == 1 || bugs == 2))
					newGrid[x][y] = true;
				else
					newGrid[x][y] = grid[x][y];
			}
		}

		// Update other grids before we update ours
		if(this.below != null && calledFrom != -1)
			this.below.doStep(1);
		if(this.above != null && calledFrom != 1)
			this.above.doStep(-1);

		this.grid = newGrid;
	}

	public int getScore(){
		return this.getScore(0);
	}

	public int getScore(int calledFrom){
		int score = 0;

		for(int x = 0; x < 5; x++){
			for(int y = 0; y < 5; y++){
				if(grid[x][y])
					score++;
			}
		}

		if(this.below != null && calledFrom != -1)
			score += this.below.getScore(1);
		if(this.above != null && calledFrom != 1)
			score += this.above.getScore(-1);

		return score;
	}

	public int getOutside(int direction){
		int result = 0;

		if(direction == 0 && grid[2][1])
			result++;
		else if(direction == 1 && grid[3][2])
			result++;
		else if(direction == 2 && grid[2][3])
			result++;
		else if(direction == 3 && grid[1][2])
			result++;

		return result;
	}

	public int getInside(int direction){
		int result = 0;

		if(direction == 0){
			for(int x = 0; x < 5; x++){
				if(grid[x][0])
					result++;
			}
		}
		else if(direction == 1){
			for(int y = 0; y < 5; y++){
				if(grid[4][y])
					result++;
			}
		}
		else if(direction == 2){
			for(int x = 0; x < 5; x++){
				if(grid[x][4])
					result++;
			}
		}
		else if(direction == 3){
			for(int y = 0; y < 5; y++){
				if(grid[0][y])
					result++;
			}
		}

		return result;
	}

	public void readFromString(String in){
		for(int i = 0; i < in.length(); i++){
			if(in.charAt(i) == '#')
				this.grid[i%5][i/5] = true;
		}
	}
}

class SimpleEris{

	boolean[][] grid;

	public SimpleEris(){
		this.grid = new boolean[5][5];
	}

	public void doStep(){
		boolean[][] newGrid = new boolean[5][5];
		for(int x = 0; x < 5; x++){
			for(int y = 0; y < 5; y++){
				int bugs = 0;
				if(x > 0 && grid[x-1][y])
					bugs++;
				if(y > 0 && grid[x][y-1])
					bugs++;
				if(x < 4 && grid[x+1][y])
					bugs++;
				if(y < 4 && grid[x][y+1])
					bugs++;

				if(grid[x][y] && bugs != 1)
					newGrid[x][y] = false;
				else if(!grid[x][y] && (bugs == 1 || bugs == 2))
					newGrid[x][y] = true;
				else
					newGrid[x][y] = grid[x][y];
			}
		}

		this.grid = newGrid;
	}

	public int getScore(){
		int score = 0;
		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 5; x++){
				if(grid[x][y]){
					score += Math.pow(2, x+y*5);
				}
			}
		}
		return score;
	}

	public void readFromString(String in){
		for(int i = 0; i < in.length(); i++){
			if(in.charAt(i) == '#')
				this.grid[i%5][i/5] = true;
		}
	}

	public String gridToString(){
		String result = "";

		for(int y = 0; y < 5; y++){
			for(int x = 0; x < 5; x++){
				if(grid[x][y])
					result += "#";
				else
					result += ".";
			}
		}

		return result;
	}
}
