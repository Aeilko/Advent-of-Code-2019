package day10;

import utils.Grids.Coordinate;
import utils.Math.Euclidean;

import java.util.*;

public class Solution {

	public static final String INPUT = ".###.###.###.#####.#\n" +
			"#####.##.###..###..#\n" +
			".#...####.###.######\n" +
			"######.###.####.####\n" +
			"#####..###..########\n" +
			"#.##.###########.#.#\n" +
			"##.###.######..#.#.#\n" +
			".#.##.###.#.####.###\n" +
			"##..#.#.##.#########\n" +
			"###.#######.###..##.\n" +
			"###.###.##.##..####.\n" +
			".##.####.##########.\n" +
			"#######.##.###.#####\n" +
			"#####.##..####.#####\n" +
			"##.#.#####.##.#.#..#\n" +
			"###########.#######.\n" +
			"#.##..#####.#####..#\n" +
			"#####..#####.###.###\n" +
			"####.#.############.\n" +
			"####.#.#.##########.";

	public static void main(String[] args) {
		System.out.print("Input number of lines or 'def': ");
		Scanner in = new Scanner(System.in);
		String input = in.nextLine();

		int rows = 20;
		int cols = 20;

		if (input.equals("def"))
			input = INPUT;
		else {
			System.out.println("Input map");
			rows = Integer.parseInt(input);
			input = in.nextLine();
			cols = input.length();
			for (int i = 1; i < rows; i++) {
				input += "\n" + in.nextLine();
			}
		}

		// Process input
		boolean[][] map = new boolean[cols][rows];
		int z = 0;
		for (char c : input.toCharArray()) {
			if (c == '\n')
				continue;
			if (c == '#')
				map[z % cols][z / rows] = true;

			z++;
		}

		// Find the asteroid with max vision
		int maxDetected = 0;
		int stationX = 0;
		int stationY = 0;
		for (int j = 0; j < map[0].length; j++) {
			for (int i = 0; i < map.length; i++) {
				if (map[i][j]) {
					int tmp = getNumDetections(i, j, map);
					if (tmp > maxDetected) {
						maxDetected = tmp;
						stationX = i;
						stationY = j;
					}
				}
			}
		}

		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + maxDetected + "\n");

		//displayMap(stationX, stationY, map, new boolean[map.length][map[0].length]);

		// Calculate the angle for each asteroid
		TreeMap<Double, TreeSet<Coordinate>> angles = new TreeMap<>();
		for (int j = 0; j < map[0].length; j++) {
			for (int i = 0; i < map.length; i++) {
				if (map[i][j]) {
					double a = calcAngle(stationX, stationY, i, j);
					if (angles.containsKey(a))
						angles.get(a).add(new Coordinate(i, j));
					else {
						TreeSet<Coordinate> tmp = new TreeSet<>();
						tmp.add(new Coordinate(i, j));
						angles.put(a, tmp);
					}
				}
			}
		}

		// Order distinct angle values
		ArrayList<Double> ogAngles = new ArrayList<>(angles.keySet());
		Collections.sort(ogAngles, Collections.reverseOrder());

		// Start destroying androids
		int lastX = 0;
		int lastY = 0;
		int destroyed = 0;
		outer: while (ogAngles.size() > 0){
			// Keep looping over de angles until we have destroyed the 200th asteroid
			for (double a : ogAngles) {
				TreeSet<Coordinate> coords = angles.get(a);
				if(coords.size() > 0) {
					int distance = Integer.MAX_VALUE;
					int smallestX = 0;
					int smallestY = 0;
					for (Coordinate c : coords) {
						int d = Math.abs(c.getX() - stationX) + Math.abs(c.getY() - stationY);
						if (d < distance) {
							smallestX = c.getX();
							smallestY = c.getY();
							distance = d;
						}
					}

					destroyed++;
					coords.remove(new Coordinate(smallestX, smallestY));

					// Stop once we destroyed the 200th asteroid
					if (destroyed == 200) {
						lastX = smallestX;
						lastY = smallestY;
						break outer;
					}
				}
			}
		}

		System.out.println("Part 2 - Success");
		System.out.println("Answer\t" + ((lastX*100) + lastY));
	}

	public static int getNumDetections(int x, int y, boolean[][] map){
		int result = 0;

		// Create a grid of all the positions of which the view is blocked
		boolean[][] blocked = new boolean[map.length][map[0].length];
		for (int j = 0; j < map[0].length; j++) {
			for (int i = 0; i < map.length; i++) {
				// Check if there is an asteroid on this position and if it is not blocked already
				if(map[i][j] && !blocked[i][j] && !(x == i && y == j)){
					// Check which coordinates this asteroid blocks.
					int xStep = i-x;
					int yStep = j-y;
					// Change the step to 1 or -1 if the other step is 0 (inline with station)
					if(xStep == 0)
						yStep = yStep/Math.abs(yStep);
					else if(yStep == 0)
						xStep = xStep/Math.abs(xStep);
					// If the steps have a gcd we should decrease the step size
					int gcd = (int) Math.abs(Euclidean.gcd((long) xStep, (long) yStep));
					if(gcd != 1){
						xStep = xStep/gcd;
						yStep = yStep/gcd;
					}

					int xCur = i+xStep;
					int yCur = j+yStep;

					while(xCur < map.length && xCur >= 0 && yCur < map[0].length && yCur >= 0){
						blocked[xCur][yCur] = true;
						xCur += xStep;
						yCur += yStep;
					}
				}
			}
		}

		// Check how many unblocked asteroids there are
		for(int j = 0; j < map[0].length; j++){
			for(int i = 0; i < map.length; i++){
				if(map[i][j] && !blocked[i][j] && !(i == x && j == y))
					result++;
			}
		}

		return result;
	}

	public static void displayMap(int x, int y, boolean[][] map, boolean[][] blocked){
		for (int j = 0; j < map[0].length; j++) {
			for (int i = 0; i < map.length; i++) {
				if (i == x && j == y)
					System.out.print(" X");
				else if (blocked[i][j])
					System.out.print(" O");
				else if (map[i][j])
					System.out.print(" #");
				else
					System.out.print(" .");
			}
			System.out.println();
		}
	}

	public static double calcAngle(int originX, int originY, int x, int y){
		return Math.atan2(x-originX,  y-originY);
	}
}
