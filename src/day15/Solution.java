package day15;

import utils.Grids.Grid;
import utils.Grids.ManhattanPointComparator;
import utils.Grids.Point;
import utils.IntcodeComputer.IntcodeComputer;
import utils.IntcodeComputer.IntcodeComputerThread;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.ArrayBlockingQueue;

public class Solution {

	public static final String PROGRAM = "3,1033,1008,1033,1,1032,1005,1032,31,1008,1033,2,1032,1005,1032,58,1008,1033,3,1032,1005,1032,81,1008,1033,4,1032,1005,1032,104,99,101,0,1034,1039,101,0,1036,1041,1001,1035,-1,1040,1008,1038,0,1043,102,-1,1043,1032,1,1037,1032,1042,1105,1,124,101,0,1034,1039,102,1,1036,1041,1001,1035,1,1040,1008,1038,0,1043,1,1037,1038,1042,1106,0,124,1001,1034,-1,1039,1008,1036,0,1041,102,1,1035,1040,1001,1038,0,1043,1001,1037,0,1042,1106,0,124,1001,1034,1,1039,1008,1036,0,1041,1001,1035,0,1040,1001,1038,0,1043,1002,1037,1,1042,1006,1039,217,1006,1040,217,1008,1039,40,1032,1005,1032,217,1008,1040,40,1032,1005,1032,217,1008,1039,7,1032,1006,1032,165,1008,1040,5,1032,1006,1032,165,1102,1,2,1044,1105,1,224,2,1041,1043,1032,1006,1032,179,1101,0,1,1044,1105,1,224,1,1041,1043,1032,1006,1032,217,1,1042,1043,1032,1001,1032,-1,1032,1002,1032,39,1032,1,1032,1039,1032,101,-1,1032,1032,101,252,1032,211,1007,0,27,1044,1106,0,224,1102,1,0,1044,1106,0,224,1006,1044,247,101,0,1039,1034,101,0,1040,1035,102,1,1041,1036,1001,1043,0,1038,102,1,1042,1037,4,1044,1106,0,0,13,3,18,86,2,10,5,16,95,16,54,4,23,63,70,10,21,20,26,99,85,9,96,3,83,5,9,91,14,1,4,78,11,15,53,10,35,13,7,17,30,90,23,65,65,67,16,4,65,39,11,57,13,36,22,95,53,63,22,47,12,47,2,12,3,71,92,17,55,16,51,79,6,3,92,15,17,15,18,63,8,12,3,49,6,69,32,1,25,83,17,12,1,76,23,95,17,13,92,13,56,16,69,94,11,20,31,83,30,21,88,22,61,45,6,70,12,3,30,23,86,6,93,4,24,9,73,72,7,72,83,9,30,6,24,86,99,11,11,96,16,68,10,35,19,23,6,79,51,8,3,8,75,2,32,26,73,23,80,30,86,25,64,46,24,81,20,18,85,7,94,28,37,93,18,12,77,99,14,22,19,50,2,18,45,63,8,2,89,79,79,7,33,77,18,20,22,12,58,61,20,4,58,20,51,79,14,32,19,87,21,19,76,8,81,7,13,72,75,22,28,22,14,92,30,18,90,10,6,97,25,34,9,20,26,52,45,6,4,97,4,46,26,86,61,20,25,28,26,22,54,69,16,51,3,58,5,23,75,92,18,98,12,11,55,38,22,87,14,20,17,52,73,9,91,30,14,26,12,56,81,54,9,72,18,12,47,93,22,54,21,59,73,7,78,12,87,26,5,39,45,4,55,16,21,86,62,20,98,61,14,20,70,14,25,92,32,44,2,3,15,32,23,23,97,76,78,15,23,95,21,11,69,34,12,89,3,95,24,15,59,38,39,72,14,15,55,48,18,2,43,26,13,58,68,11,22,89,33,79,22,43,40,14,26,5,50,11,28,9,36,33,2,22,43,21,90,15,92,14,14,49,9,80,14,85,99,70,8,16,14,15,70,1,39,32,45,5,57,12,12,4,99,75,28,14,2,28,71,5,69,61,4,28,98,97,87,10,80,2,65,93,6,21,81,7,95,22,35,18,38,23,11,53,14,5,2,84,3,70,33,19,8,52,10,99,14,58,36,1,3,30,53,4,7,47,10,93,2,32,17,40,68,43,20,41,4,16,21,29,23,82,2,18,37,37,15,19,26,41,28,9,95,17,17,52,25,13,49,28,47,22,5,52,14,21,72,83,7,17,86,20,3,18,58,14,19,25,56,65,65,26,53,8,20,75,31,21,40,17,6,33,20,95,47,24,75,26,17,96,24,48,65,97,4,52,20,78,47,14,23,77,32,8,18,98,43,7,61,25,84,40,6,36,24,87,24,71,77,13,20,49,16,60,35,9,64,48,21,2,74,25,1,2,57,11,58,7,45,35,26,13,74,92,2,9,82,9,20,23,15,33,94,7,10,48,78,16,24,94,33,11,21,5,89,47,15,52,12,51,51,81,9,18,39,14,2,97,79,33,23,12,99,3,16,11,79,83,45,18,23,78,86,69,10,25,98,62,62,18,7,44,47,1,3,92,8,22,81,9,3,29,8,81,21,13,95,6,5,99,5,29,16,3,53,72,26,14,44,97,7,43,12,42,65,17,8,12,88,55,18,20,34,13,39,10,72,58,15,11,69,17,94,20,22,52,28,13,30,65,8,2,63,18,4,36,17,8,71,16,71,15,64,14,31,51,75,1,12,92,14,35,23,40,45,1,5,87,28,18,83,43,9,90,2,3,50,18,61,68,5,89,16,44,7,34,82,74,15,83,15,70,13,80,20,43,8,35,14,58,50,75,20,50,9,68,46,52,2,73,11,60,32,61,25,40,9,31,21,73,0,0,21,21,1,10,1,0,0,0,0,0,0";

	public static void main(String[] args){
		long[] prog = IntcodeComputer.processInput(PROGRAM);
		RepairDroid rd = new RepairDroid(prog);
		rd.explore();
		int result = rd.lengthToOxygen();

		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + result + "\n");

		result = rd.timeTillOxygenFill();
		System.out.println("Part 2 - Success");
		System.out.println("Answer\t" + result);

		// Save a nice visualisation of the grid
		rd.renderGrid();

		// Stop intcode computer
		rd.stop();
	}
}

class RepairDroid{

	// The IntcodeComputer used by the droid
	IntcodeComputerThread ic;
	ArrayBlockingQueue<Long> icInput;
	ArrayBlockingQueue<Long> icOutput;

	// The grid the droid operates on
	private Grid grid;

	// The location of the droid
	private int x;
	private int y;

	// Start location of the droid
	private int startX;
	private int startY;

	// The location of the oxygen
	private int oxygenX = -1;
	private int oxygenY = -1;

	// The moves the droid can make
	public static final long NORTH = 1;
	public static final long SOUTH = 2;
	public static final long WEST = 3;
	public static final long EAST = 4;

	// The types of environment
	public static final long WALL = 0;
	public static final long EMPTY = 1;
	public static final long OXYGEN = 2;

	// General settings
	public static final int GRIDSIZE = 43;

	public RepairDroid(long[] program){
		this.grid = new Grid(GRIDSIZE, GRIDSIZE);
		this.x = this.startX = GRIDSIZE/2;
		this.y = this.startY = GRIDSIZE/2;

		this.icInput = new ArrayBlockingQueue<>(64);
		this.icOutput = new ArrayBlockingQueue<>(64);
		this.ic = new IntcodeComputerThread(program);
		this.ic.setInputArray(this.icInput);
		this.ic.setOutputArray(this.icOutput);
		this.ic.start();
	}

	public void stop(){
		this.ic.interrupt();
	}

	/**
	 * Explores all points of the grid it can reach
	 */
	public void explore(){
		// Create a set which is sorted by the lowest manhattan distance to the current location
		ManhattanPointComparator mpc = new ManhattanPointComparator(this.x, this.y);
		TreeSet<Point> unvisited = new TreeSet<>(mpc);
		boolean[][] hasVisited = new boolean[GRIDSIZE][GRIDSIZE];
		hasVisited[this.x][this.y] = true;

		// Add the positons around the base
		Point cur = this.grid.getPoint(this.x, this.y);
		unvisited.add(cur.getNorth());
		unvisited.add(cur.getEast());
		unvisited.add(cur.getSouth());
		unvisited.add(cur.getWest());

		while(unvisited.size() > 0){
			// Check our next target point and fight a route to it
			Point goal = unvisited.first();
			ArrayList<Point> route = this.grid.getPath(cur, goal);

			// Move to each point on the route
			for(Point p: route){
				long direction = 0;
				if(cur.x < p.x)
					direction = EAST;
				else if(cur.x > p.x)
					direction = WEST;
				else if(cur.y < p.y)
					direction = NORTH;
				else if(cur.y > p.y)
					direction = SOUTH;

				try {
					// Sent command to the Intcode Computer
					this.icInput.put(direction);
					long status = this.icOutput.take();

					// Mark the new point as visited
					unvisited.remove(p);
					hasVisited[p.x][p.y] = true;

					// Check what the new point is
					if(status == WALL){
						// Point p is a wall, so we can disconnect it from the grid and stop our current route
						p.disconnect();
						break;
					}
					else if(status == EMPTY || status == OXYGEN){
						// Update our current location
						this.x = p.x;
						this.y = p.y;
						cur = p;
						// Add neighbours to our unvisited list
						for(Point p2: p.getNeighbours()){
							if(!hasVisited[p2.x][p2.y])
								unvisited.add(p2);
						}

						// Check whether we found oxygen
						if(status == OXYGEN){
							this.oxygenX = p.x;
							this.oxygenY = p.y;
							break;
						}
					}
				}
				catch (InterruptedException e) {
					System.err.println("RepairDroid.explore() error - while writing/readding to IC");
					e.printStackTrace();
				}
			}

			if(goal.x == this.x && this.y == goal.y){
				// We reached the goal of the route (AKA: did not run into any walls)
				unvisited.remove(goal);
			}
		}
	}

	/**
	 * Calculates the length of the route to the oxygen point
	 */
	public int lengthToOxygen(){
		if(oxygenX == -1 || oxygenY == -1)
			return -1;
		else{
			ArrayList<Point> path = this.grid.getPath(this.grid.getPoint(startX, startY), this.grid.getPoint(oxygenX, oxygenY));
			return path.size();
		}
	}

	/**
	 * Calculates the time it takes for the oxygen to reach all points (AKA: Greatest distance from the oxygen point)
	 */
	public int timeTillOxygenFill(){
		return this.grid.longestDistanceFrom(oxygenX, oxygenY);
	}

	/**
	 * Creates a visualisation of the grid
	 */
	public void renderGrid(){
		int blackRGB = Color.BLACK.getRGB();
		int whiteRGB = Color.WHITE.getRGB();
		int blueRGB = Color.BLUE.getRGB();
		int redRGB = Color.RED.getRGB();

		int x = GRIDSIZE;
		int y = GRIDSIZE;

		// Use *10 scaling to create a better readable image
		BufferedImage image = new BufferedImage(x*10, y*10, BufferedImage.TYPE_INT_ARGB);

		for(int i = 0; i < y*10; i++){
			for(int j = 0; j < x*10; j++){
				Point p = grid.getPoint(j/10, i/10);
				if(j/10 == startX && i/10 == startY)
					image.setRGB(j, i, redRGB);
				else if(j/10 == oxygenX && i/10 == oxygenY)
					image.setRGB(j, i, blueRGB);
				else if(p.getNeighbours().size() > 0)
					image.setRGB(j, i, whiteRGB);
				else
					image.setRGB(j, i, blackRGB);
			}
		}

		// Save image
		File out = new File("src/day15/visual.png");
		try {
			ImageIO.write(image, "png", out);
		}
		catch(IOException e){
			System.err.println("Error saving image");
			System.out.println(e.getMessage());
		}
	}
}