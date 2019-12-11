package day11;

import utils.IntcodeComputer;
import utils.IntcodeComputerThread;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;

public class Solution {

	public static final int GRIDSIZE = 2024;

	public static final String INPUT = "3,8,1005,8,328,1106,0,11,0,0,0,104,1,104,0,3,8,1002,8,-1,10,1001,10,1,10,4,10,1008,8,0,10,4,10,1001,8,0,29,1,104,7,10,3,8,1002,8,-1,10,101,1,10,10,4,10,1008,8,0,10,4,10,1001,8,0,55,1,2,7,10,1006,0,23,3,8,102,-1,8,10,1001,10,1,10,4,10,1008,8,0,10,4,10,1001,8,0,84,1006,0,40,1,1103,14,10,1,1006,16,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,1,8,10,4,10,1002,8,1,116,1006,0,53,1,1104,16,10,3,8,102,-1,8,10,101,1,10,10,4,10,1008,8,1,10,4,10,102,1,8,146,2,1104,9,10,3,8,102,-1,8,10,101,1,10,10,4,10,1008,8,1,10,4,10,1001,8,0,172,1006,0,65,1,1005,8,10,1,1002,16,10,3,8,102,-1,8,10,1001,10,1,10,4,10,108,0,8,10,4,10,102,1,8,204,2,1104,9,10,1006,0,30,3,8,102,-1,8,10,101,1,10,10,4,10,108,0,8,10,4,10,102,1,8,233,2,1109,6,10,1006,0,17,1,2,6,10,3,8,102,-1,8,10,101,1,10,10,4,10,108,1,8,10,4,10,102,1,8,266,1,106,7,10,2,109,2,10,2,9,8,10,3,8,102,-1,8,10,101,1,10,10,4,10,1008,8,1,10,4,10,1001,8,0,301,1,109,9,10,1006,0,14,101,1,9,9,1007,9,1083,10,1005,10,15,99,109,650,104,0,104,1,21102,1,837548789788,1,21101,0,345,0,1106,0,449,21101,0,846801511180,1,21101,0,356,0,1106,0,449,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,3,10,104,0,104,1,3,10,104,0,104,0,3,10,104,0,104,1,21101,235244981271,0,1,21101,403,0,0,1105,1,449,21102,1,206182744295,1,21101,0,414,0,1105,1,449,3,10,104,0,104,0,3,10,104,0,104,0,21102,837896937832,1,1,21101,0,437,0,1106,0,449,21101,867965862668,0,1,21102,448,1,0,1106,0,449,99,109,2,22102,1,-1,1,21101,40,0,2,21102,1,480,3,21101,0,470,0,1106,0,513,109,-2,2106,0,0,0,1,0,0,1,109,2,3,10,204,-1,1001,475,476,491,4,0,1001,475,1,475,108,4,475,10,1006,10,507,1101,0,0,475,109,-2,2106,0,0,0,109,4,1201,-1,0,512,1207,-3,0,10,1006,10,530,21102,1,0,-3,22102,1,-3,1,21201,-2,0,2,21102,1,1,3,21102,549,1,0,1106,0,554,109,-4,2105,1,0,109,5,1207,-3,1,10,1006,10,577,2207,-4,-2,10,1006,10,577,21202,-4,1,-4,1106,0,645,21202,-4,1,1,21201,-3,-1,2,21202,-2,2,3,21101,596,0,0,1106,0,554,21201,1,0,-4,21102,1,1,-1,2207,-4,-2,10,1006,10,615,21101,0,0,-1,22202,-2,-1,-2,2107,0,-3,10,1006,10,637,22102,1,-1,1,21101,637,0,0,105,1,512,21202,-2,-1,-2,22201,-4,-2,-4,109,-5,2106,0,0";

	public static void main(String[] args){
		long[] stack = IntcodeComputer.processInput(INPUT);

		// Setup the IC
		ArrayBlockingQueue<Long> in = new ArrayBlockingQueue<>(64);
		ArrayBlockingQueue<Long> out = new ArrayBlockingQueue<>(64);
		IntcodeComputerThread ic = new IntcodeComputerThread(stack);
		ic.setOutputArray(out);
		ic.setInputArray(in);
		// Add the first input, the current panel is black (0)
		try{ in.put(0L); } catch (InterruptedException e) { }
		ic.start();

		// Our grid overview
		boolean[][] grid = new boolean[GRIDSIZE][GRIDSIZE];
		boolean[][] painted = new boolean[GRIDSIZE][GRIDSIZE];
		int x = GRIDSIZE/2;
		int y = GRIDSIZE/2;

		int result = goRobot(x, y, grid, painted, in, out);

		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + result + "\n");

		// Setup the IC
		in = new ArrayBlockingQueue<>(64);
		out = new ArrayBlockingQueue<>(64);
		ic = new IntcodeComputerThread(stack);
		ic.setOutputArray(out);
		ic.setInputArray(in);
		// Add the first input, the current panel is white
		try{ in.put(1L); } catch (InterruptedException e) { }
		ic.start();

		// Overview
		grid = new boolean[GRIDSIZE][GRIDSIZE];
		x = GRIDSIZE/2;
		y = GRIDSIZE/2;

		goRobot(x, y, grid, painted, in, out);
		System.out.println("Rendering image");
		renderImage(grid);
		System.out.println("Part 2 - Success");
	}

	public static int goRobot(int x, int y, boolean[][] grid, boolean[][] painted, ArrayBlockingQueue<Long> in, ArrayBlockingQueue<Long> out){
		int panelsPainted = 0;
		int facing = 0;

		while(true){
			try {
				long color = out.take();
				// Check if the IC is done
				if(color == Long.MIN_VALUE)
					break;
				long turn = out.take();

				// Paint the panel
				grid[x][y] = (color == 1L);
				if(!painted[x][y]){
					painted[x][y] = true;
					panelsPainted++;
				}

				// Move the robot
				if(turn == 0L)
					facing = (facing+3)%4;	// Using +3 instead of -1 prevents negative numbers
				else
					facing = (facing+1)%4;
				switch (facing){
					case 0:
						y++;
						break;
					case 1:
						x++;
						break;
					case 2:
						y--;
						break;
					case 3:
						x--;
						break;
					default:
						System.out.println("Error" + facing);
						break;
				}

				// Set robot input
				in.put(grid[x][y] ? 1L : 0L);
			}
			catch (InterruptedException e) {
				System.err.println("Error");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}

		return panelsPainted;
	}

	public static void renderImage(boolean[][] pixels){
		int blackRGB = Color.BLACK.getRGB();
		int whiteRGB = Color.WHITE.getRGB();

		int x = pixels.length;
		int y = pixels[0].length;

		// Use *10 scaling to create a better readable image
		BufferedImage image = new BufferedImage(x*10, y*10, BufferedImage.TYPE_INT_ARGB);

		for(int i = 0; i < y*10; i++){
			for(int j = 0; j < x*10; j++){
				if(pixels[j/10][i/10])
					image.setRGB(j, i, whiteRGB);
				else
					image.setRGB(j, i, blackRGB);
			}
		}

		// Save image
		File out = new File("src/day11/output.png");
		try {
			ImageIO.write(image, "png", out);
		}
		catch(IOException e){
			System.err.println("Error saving image");
			System.out.println(e.getMessage());
		}
	}
}
