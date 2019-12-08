package day7;

import utils.IntcodeComputerThread;
import utils.Permutations;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class Solution {

	public static final String PROGRAM = "3,8,1001,8,10,8,105,1,0,0,21,46,59,84,93,110,191,272,353,434,99999,3,9,101,2,9,9,102,3,9,9,1001,9,5,9,102,4,9,9,1001,9,4,9,4,9,99,3,9,101,3,9,9,102,5,9,9,4,9,99,3,9,1001,9,4,9,1002,9,2,9,101,2,9,9,102,2,9,9,1001,9,3,9,4,9,99,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,1001,9,5,9,1002,9,3,9,4,9,99,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,99";

	public static final int[] SETTINGS_PART1 = {0,1,2,3,4};
	public static final int[] SETTINGS_PART2 = {5,6,7,8,9};

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

		int result = runAllPermutations(ogStack, SETTINGS_PART1);
		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + result + "\n");

		result = runAllPermutations(ogStack, SETTINGS_PART2);
		System.out.println("Part 2 - Success");
		System.out.println("Answer\t" + result);
	}

	public static int runAllPermutations(int[] program, int[] setting){
		ArrayList<int[]> perms = Permutations.getAll(setting);

		int max = 0;
		for(int i = 0; i < perms.size(); i++) {
			int result = runThreadedIntcodeSetting(perms.get(i), program);
			if(result > max)
				max = result;
		}

		return max;
	}

	public static int runThreadedIntcodeSetting(int[] setting, int[] program){
		IntcodeComputerThread[] ics = new IntcodeComputerThread[setting.length];
		ArrayBlockingQueue<Integer>[] inputs = new ArrayBlockingQueue[setting.length];

		for(int i = 0; i < ics.length; i++){
			ics[i] = new IntcodeComputerThread(program);
			inputs[i] = new ArrayBlockingQueue<>(64);
			try {
				inputs[i].put(setting[i]);
				if(i == 0)
					inputs[i].put(0);
			}
			catch (Exception e){ }
			ics[i].setInputArray(inputs[i]);
			if(i > 0)
				ics[i-1].setOutputArray(inputs[i]);
		}
		ics[setting.length-1].setOutputArray(inputs[0]);

		for(int i = 0; i < ics.length; i++){
			ics[i].start();
		}

		int result = 0;
		try {
			// Wait for the last machine to finish
			ics[ics.length-1].join();
			// Get last value of the last machine (assuming the last output is the only output left)
			ArrayBlockingQueue<Integer> resultArray = ics[ics.length-1].getOutputArray();
			result = resultArray.take();
		}
		catch (Exception e) {
			System.err.println("Error");
			System.out.println(e.getMessage());
		}

		return result;
	}
}
