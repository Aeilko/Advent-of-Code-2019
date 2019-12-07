package day7;

import utils.ArrayUtils;
import utils.IntcodeComputer;
import utils.Permutations;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class Solution {

	public static final String PROGRAM = "3,8,1001,8,10,8,105,1,0,0,21,46,59,84,93,110,191,272,353,434,99999,3,9,101,2,9,9,102,3,9,9,1001,9,5,9,102,4,9,9,1001,9,4,9,4,9,99,3,9,101,3,9,9,102,5,9,9,4,9,99,3,9,1001,9,4,9,1002,9,2,9,101,2,9,9,102,2,9,9,1001,9,3,9,4,9,99,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,1001,9,5,9,1002,9,3,9,4,9,99,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,99";

	public static final int[] SETTINGS = {0,1,2,3,4};

	public static void main(String[] args){
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

		String input = in.nextLine();
		if(input.equals("def"))
			input = PROGRAM;

		// Process input to stack
		String[] sep = input.split(",");
		int[] ogStack = new int[sep.length];
		for(int i = 0; i < sep.length; i++){
			ogStack[i] = Integer.parseInt(sep[i]);
		}

		int result = part1(ogStack);
		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + result);
	}

	public static int part1(int[] program){
		ArrayList<int[]> perms = Permutations.getAll(SETTINGS);

		int max = 0;
		for(int i = 0; i < perms.size(); i++) {
			int result = runIntcodeSettings(perms.get(i), program);
			if(result > max)
				max = result;
		}

		return max;
	}

	public static int runIntcodeSettings(int[] setting, int[] program){
		IntcodeComputer[] ics = new IntcodeComputer[setting.length];
		for(int i = 0; i < ics.length; i++){
			ics[i] = new IntcodeComputer(program);
			ics[i].setSilent();
		}

		int set;
		int val = 0;
		for(int i = 0; i < ics.length; i++){
			set = setting[i];

			// Overwrite System.in settings
			InputStream normalIn = System.in;
			//System.out.println(set + "\t" + val);
			String input = set + " " + val;
			ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
			System.setIn(inputStream);

			// Overwrite System.out to catch output
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PrintStream ps = new PrintStream(baos);
			PrintStream normalOut = System.out;
			System.setOut(ps);

			String res = "";

			try {
				ics[i].run();
				val = Integer.parseInt(baos.toString().strip());
			}
			catch (Exception e){
				System.err.println("Error");
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
			// Reset System.in and System.out
			System.setIn(normalIn);
			System.setOut(normalOut);
		}

		return val;
	}
}
