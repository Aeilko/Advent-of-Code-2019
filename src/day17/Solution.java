package day17;

import utils.Grids.Grid;
import utils.Grids.Point;
import utils.IntcodeComputer.IntcodeComputer;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class Solution {

	public static final String PROGRAM = "1,330,331,332,109,3406,1102,1182,1,15,1101,0,1481,24,1002,0,1,570,1006,570,36,1002,571,1,0,1001,570,-1,570,1001,24,1,24,1106,0,18,1008,571,0,571,1001,15,1,15,1008,15,1481,570,1006,570,14,21101,0,58,0,1105,1,786,1006,332,62,99,21101,0,333,1,21102,1,73,0,1106,0,579,1101,0,0,572,1101,0,0,573,3,574,101,1,573,573,1007,574,65,570,1005,570,151,107,67,574,570,1005,570,151,1001,574,-64,574,1002,574,-1,574,1001,572,1,572,1007,572,11,570,1006,570,165,101,1182,572,127,1001,574,0,0,3,574,101,1,573,573,1008,574,10,570,1005,570,189,1008,574,44,570,1006,570,158,1105,1,81,21101,340,0,1,1105,1,177,21101,477,0,1,1106,0,177,21102,514,1,1,21102,1,176,0,1106,0,579,99,21102,184,1,0,1105,1,579,4,574,104,10,99,1007,573,22,570,1006,570,165,1002,572,1,1182,21101,0,375,1,21101,0,211,0,1106,0,579,21101,1182,11,1,21102,222,1,0,1105,1,979,21102,388,1,1,21102,1,233,0,1106,0,579,21101,1182,22,1,21101,244,0,0,1105,1,979,21102,401,1,1,21102,1,255,0,1105,1,579,21101,1182,33,1,21101,0,266,0,1106,0,979,21101,414,0,1,21102,277,1,0,1106,0,579,3,575,1008,575,89,570,1008,575,121,575,1,575,570,575,3,574,1008,574,10,570,1006,570,291,104,10,21101,0,1182,1,21101,0,313,0,1106,0,622,1005,575,327,1101,0,1,575,21101,0,327,0,1105,1,786,4,438,99,0,1,1,6,77,97,105,110,58,10,33,10,69,120,112,101,99,116,101,100,32,102,117,110,99,116,105,111,110,32,110,97,109,101,32,98,117,116,32,103,111,116,58,32,0,12,70,117,110,99,116,105,111,110,32,65,58,10,12,70,117,110,99,116,105,111,110,32,66,58,10,12,70,117,110,99,116,105,111,110,32,67,58,10,23,67,111,110,116,105,110,117,111,117,115,32,118,105,100,101,111,32,102,101,101,100,63,10,0,37,10,69,120,112,101,99,116,101,100,32,82,44,32,76,44,32,111,114,32,100,105,115,116,97,110,99,101,32,98,117,116,32,103,111,116,58,32,36,10,69,120,112,101,99,116,101,100,32,99,111,109,109,97,32,111,114,32,110,101,119,108,105,110,101,32,98,117,116,32,103,111,116,58,32,43,10,68,101,102,105,110,105,116,105,111,110,115,32,109,97,121,32,98,101,32,97,116,32,109,111,115,116,32,50,48,32,99,104,97,114,97,99,116,101,114,115,33,10,94,62,118,60,0,1,0,-1,-1,0,1,0,0,0,0,0,0,1,26,16,0,109,4,2102,1,-3,587,20102,1,0,-1,22101,1,-3,-3,21102,0,1,-2,2208,-2,-1,570,1005,570,617,2201,-3,-2,609,4,0,21201,-2,1,-2,1105,1,597,109,-4,2105,1,0,109,5,2102,1,-4,629,21001,0,0,-2,22101,1,-4,-4,21102,1,0,-3,2208,-3,-2,570,1005,570,781,2201,-4,-3,653,20102,1,0,-1,1208,-1,-4,570,1005,570,709,1208,-1,-5,570,1005,570,734,1207,-1,0,570,1005,570,759,1206,-1,774,1001,578,562,684,1,0,576,576,1001,578,566,692,1,0,577,577,21102,1,702,0,1105,1,786,21201,-1,-1,-1,1106,0,676,1001,578,1,578,1008,578,4,570,1006,570,724,1001,578,-4,578,21102,731,1,0,1106,0,786,1105,1,774,1001,578,-1,578,1008,578,-1,570,1006,570,749,1001,578,4,578,21101,0,756,0,1105,1,786,1105,1,774,21202,-1,-11,1,22101,1182,1,1,21101,774,0,0,1106,0,622,21201,-3,1,-3,1106,0,640,109,-5,2106,0,0,109,7,1005,575,802,20102,1,576,-6,21002,577,1,-5,1105,1,814,21102,0,1,-1,21102,1,0,-5,21102,1,0,-6,20208,-6,576,-2,208,-5,577,570,22002,570,-2,-2,21202,-5,55,-3,22201,-6,-3,-3,22101,1481,-3,-3,2102,1,-3,843,1005,0,863,21202,-2,42,-4,22101,46,-4,-4,1206,-2,924,21101,0,1,-1,1106,0,924,1205,-2,873,21101,0,35,-4,1105,1,924,1202,-3,1,878,1008,0,1,570,1006,570,916,1001,374,1,374,2102,1,-3,895,1102,1,2,0,2101,0,-3,902,1001,438,0,438,2202,-6,-5,570,1,570,374,570,1,570,438,438,1001,578,558,921,21002,0,1,-4,1006,575,959,204,-4,22101,1,-6,-6,1208,-6,55,570,1006,570,814,104,10,22101,1,-5,-5,1208,-5,35,570,1006,570,810,104,10,1206,-1,974,99,1206,-1,974,1102,1,1,575,21101,973,0,0,1106,0,786,99,109,-7,2105,1,0,109,6,21102,0,1,-4,21102,1,0,-3,203,-2,22101,1,-3,-3,21208,-2,82,-1,1205,-1,1030,21208,-2,76,-1,1205,-1,1037,21207,-2,48,-1,1205,-1,1124,22107,57,-2,-1,1205,-1,1124,21201,-2,-48,-2,1106,0,1041,21101,-4,0,-2,1105,1,1041,21101,0,-5,-2,21201,-4,1,-4,21207,-4,11,-1,1206,-1,1138,2201,-5,-4,1059,1201,-2,0,0,203,-2,22101,1,-3,-3,21207,-2,48,-1,1205,-1,1107,22107,57,-2,-1,1205,-1,1107,21201,-2,-48,-2,2201,-5,-4,1090,20102,10,0,-1,22201,-2,-1,-2,2201,-5,-4,1103,2102,1,-2,0,1105,1,1060,21208,-2,10,-1,1205,-1,1162,21208,-2,44,-1,1206,-1,1131,1105,1,989,21102,1,439,1,1105,1,1150,21101,0,477,1,1106,0,1150,21102,514,1,1,21102,1,1149,0,1105,1,579,99,21102,1,1157,0,1106,0,579,204,-2,104,10,99,21207,-3,22,-1,1206,-1,1138,2101,0,-5,1176,2101,0,-4,0,109,-6,2106,0,0,40,13,42,1,11,1,10,7,25,1,11,1,10,1,5,1,25,1,11,1,10,1,5,1,1,13,11,1,11,1,10,1,5,1,1,1,11,1,11,1,11,1,10,1,5,1,1,1,11,1,11,1,11,1,10,1,5,1,1,1,11,1,11,1,11,1,10,13,7,1,11,1,11,1,16,1,1,1,3,1,7,1,11,1,11,1,16,1,1,1,1,11,5,7,11,1,16,1,1,1,1,1,1,1,13,1,17,1,16,11,9,1,7,11,18,1,1,1,1,1,3,1,9,1,7,1,28,1,1,1,1,1,3,1,9,1,7,1,28,1,1,1,1,1,3,1,9,1,7,1,28,11,7,1,7,1,30,1,1,1,3,1,9,1,7,1,22,11,3,1,9,1,7,1,22,1,7,1,5,1,9,1,7,1,22,1,7,11,5,1,7,1,22,1,13,1,3,1,5,1,7,1,18,11,7,11,7,13,6,1,3,1,5,1,11,1,25,1,6,1,3,1,5,1,11,1,25,1,6,1,3,1,5,1,11,1,25,1,6,1,3,1,5,1,11,1,25,1,6,1,3,1,5,1,11,1,25,12,5,1,11,1,25,2,5,1,9,1,11,1,26,1,5,1,9,1,11,1,26,1,5,1,9,1,11,1,26,1,5,1,9,13,26,1,5,1,48,7,48";

	public static void main(String[] args){
		long[] stack = IntcodeComputer.processInput(PROGRAM);

		IntcodeComputer ic = new IntcodeComputer(stack, 2);
		ArrayBlockingQueue<Long> inArray = new ArrayBlockingQueue<>(64);
		ArrayBlockingQueue<Long> outArray = new ArrayBlockingQueue<>(4096);
		ic.setInputArray(inArray);
		ic.setOutputArray(outArray);

		try {
			ic.run();

			// Read output
			String res = readFromOutput(outArray);

			// Find bot and calculate X and Y values
			int cols = 0;
			int c = 0;
			int rows = 0;
			int botX = 0;
			int botY = 0;
			for(int i = 0; i < res.length(); i++){
				if(res.charAt(i) == '\n'){
					rows++;
					if(cols == 0)
						cols = c;
					c = 0;
				}
				else{
					c++;
					if(res.charAt(i) == '^'){
						botX = c-1;
						// Rows -1 because it counts
						botY = rows;
					}
				}
			}
			rows--;

			// Transform to grid and then find intersections
			Grid grid = stringToGrid(res, rows, cols);
			long result = 0;
			for(int y = 0; y < rows; y++){
				for(int x = 0; x < cols; x++){
					if(grid.getPoint(x, y).getNeighbours().size() == 4){
						result += x*y;
					}
				}
			}

			System.out.println("Part 1 - Success");
			System.out.println("Answer\t" + result + "\n");

			// Find the path
			String path = findPath(grid, botX, botY);
			String pathA = findRepeatFromStart(path);
			String modPath = path.replace(pathA, "A");
			String pathB = findRepeatFromStart(modPath);
			modPath = modPath.replace(pathB, "B");
			String pathC = findRepeatFromStart(modPath);
			modPath = modPath.replace(pathC, "C");

			// Add the paths to the input
			inArray = new ArrayBlockingQueue<>(2048);
			outArray = new ArrayBlockingQueue<>(4096);
			for(long l: stringToStack(modPath))
				inArray.add(l);
			for(long l: stringToStack(pathA))
				inArray.add(l);
			for(long l: stringToStack(pathB))
				inArray.add(l);
			for(long l: stringToStack(pathC))
				inArray.add(l);
			// Answer 'no' to the feed question
			inArray.add(110L);
			inArray.add(10L);

			// Setup new intcode computer with the first value overwritten
			stack[0] = 2;
			ic = new IntcodeComputer(stack, 2);
			ic.setInputArray(inArray);
			ic.setOutputArray(outArray);
			ic.run();

			// Not used, but we have to remove them from the stack
			String preView = readFromOutput(outArray);
			String inputRequests = readFromOutput(outArray);
			String postView = readFromOutput(outArray);

			result = outArray.peek();
			System.out.println("Part 2 - Success");
			System.out.println("Answer\t" + result);
		}
		catch (InterruptedException e){ }
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Finds a repeating substring by seeing how long we can stretch the start string until it repeats
	 */
	private static String findRepeatFromStart(String input){
		// Calculate our base (dont use A, B or C in the patterns)
		String[] in = stringToArray(input);
		int base = 0;
		for(int i = 0; i < input.length(); i++){
			if(!in[i].equals("A") && !in[i].equals("B") && !in[i].equals("C")){
				base = i;
				break;
			}
		}

		// Add all indices as options
		ArrayList<Integer> ind = new ArrayList<>();
		for(int i = base+1; i < in.length; i++)
			ind.add(i);

		// Check for length i which subset matches
		int i = 0;
		while(ind.size() > 0){
			ArrayList<Integer> toRemove = new ArrayList<>();
			String pattern = in[base+i];
			if(pattern.equals("A") || pattern.equals("B") || pattern.equals("C")) {
				i++;
				break;
			}
			for(int index: ind){
				if((base+i) >= index || (base+i+index) >= in.length || in[index].equals("A") || in[index].equals("B") || in[index].equals("C") || !in[index+i].equals(pattern)) {
					toRemove.add(index);
				}
			}
			ind.removeAll(toRemove);
			i++;
		}

		// Longest pattern is i-1 long
		i--;
		String result = "";
		for(int j = 0; j < i; j++){
			result += "," + in[base+j].substring(0,1) + "," + in[base+j].substring(1);
		}

		return result.substring(1);
	}

	/**
	 * Reads from the output until it encounters a double newline
	 */
	private static String readFromOutput(ArrayBlockingQueue<Long> outArray){
		String res = "";
		try {
			boolean prevWasNewline = false;
			while (outArray.size() > 0) {
				int val = outArray.take().intValue();
				res += (char) val;
				if (val == 10) {
					if (prevWasNewline)
						break;
					prevWasNewline = true;
				}
				else {
					prevWasNewline = false;
				}
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Converts a string to an long array of ASCII values of the input
	 */
	private static long[] stringToStack(String in){
		long[] result = new long[in.length()+1];

		for(int i = 0; i < in.length(); i++){
			result[i] = (long) in.charAt(i);
		}
		result[in.length()] = 10;

		return result;
	}

	/**
	 * Converts the given movement string to an array of moves
	 */
	private static String[] stringToArray(String in){
		String[] input = in.split(",");
		ArrayList<String> res = new ArrayList<>();
		for(int i = 0; i < input.length; i ++){
			if(input[i].equals("A") || input[i].equals("B") || input[i].equals("C"))
				res.add(input[i]);
			else if(!input[i].isEmpty()){
				res.add(input[i] + input[i + 1]);
				i++;
			}
		}

		String[] result = new String[res.size()];
		res.toArray(result);

		return result;
	}

	/**
	 * Converts a string to the matching grid
	 */
	private static Grid stringToGrid(String in, int rows, int cols){
		Grid grid = new Grid(cols, rows);

		int x = 0;
		int y = 0;
		for(char c: in.toCharArray()){
			if(c == '.' || c == '#' || c == '<' || c == '>' || c == '^' || c == 'v'){
				if(c == '.')
					grid.getPoint(x, y).disconnect();
				x++;
			}
			else if(c == '\n'){
				y++;
				x = 0;
			}
		}

		return grid;
	}

	/**
	 * Finds the path to all scaffolds on the given grid from the given position
	 */
	private static String findPath(Grid grid, int startX, int startY){
		String result = "";
		short direction = 0;
		int steps = 0;
		int curX = startX;
		int curY = startY;
		while(true){
			Point p = grid.getPoint(curX, curY);
			if(direction == 0 && p.getNorth() != null){
				steps++;
				curY--;
			}
			else if(direction == 1 && p.getEast() != null){
				steps++;
				curX++;
			}
			else if(direction == 2 && p.getSouth() != null){
				steps++;
				curY++;
			}
			else if(direction == 3 && p.getWest() != null){
				steps++;
				curX--;
			}
			else{
				// We have to change direction
				if(direction == 0){
					if(p.getWest() != null) {
						result += "," + steps + "," + "L";
						direction = 3;
					}
					else if(p.getEast() != null) {
						result += "," + steps + "," + "R";
						direction = 1;
					}
					else
						break;
				}
				else if(direction == 1){
					if(p.getNorth() != null) {
						result += "," + steps + "," + "L";
						direction = 0;
					}
					else if(p.getSouth() != null) {
						result += "," + steps + "," + "R";
						direction = 2;
					}
					else
						break;
				}
				else if(direction == 2){
					if(p.getEast() != null) {
						result += "," + steps + "," + "L";
						direction = 1;
					}
					else if(p.getWest() != null) {
						result += "," + steps + "," + "R";
						direction = 3;
					}
					else
						break;
				}
				else if(direction == 3) {
					if (p.getSouth() != null) {
						result += "," + steps + "," + "L";
						direction = 2;
					} else if (p.getNorth() != null) {
						result += "," + steps + "," + "R";
						direction = 0;
					} else
						break;
				}
				steps = 0;
			}
		}

		// Remove first comma and first number of steps
		result += "," + steps;
		return result.substring(3);
	}
}
