package day07;

import utils.IntcodeComputer;
import utils.IntcodeComputerThread;
import utils.Permutations;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

public class Solution {

	public static final String PROGRAM = "3,8,1001,8,10,8,105,1,0,0,21,46,59,84,93,110,191,272,353,434,99999,3,9,101,2,9,9,102,3,9,9,1001,9,5,9,102,4,9,9,1001,9,4,9,4,9,99,3,9,101,3,9,9,102,5,9,9,4,9,99,3,9,1001,9,4,9,1002,9,2,9,101,2,9,9,102,2,9,9,1001,9,3,9,4,9,99,3,9,1002,9,2,9,4,9,99,3,9,102,2,9,9,1001,9,5,9,1002,9,3,9,4,9,99,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,2,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,102,2,9,9,4,9,99,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,99";

	public static final int[] SETTINGS_PART1 = {0,1,2,3,4};
	public static final int[] SETTINGS_PART2 = {5,6,7,8,9};

	public static void main(String[] args){
		long[] stack = IntcodeComputer.processInput(PROGRAM);

		long result = runAllPermutations(stack, SETTINGS_PART1);
		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + result + "\n");

		result = runAllPermutations(stack, SETTINGS_PART2);
		System.out.println("Part 2 - Success");
		System.out.println("Answer\t" + result);
	}

	public static long runAllPermutations(long[] program, int[] setting){
		ArrayList<int[]> perms = Permutations.getAll(setting);

		long max = 0;
		for(int i = 0; i < perms.size(); i++) {
			long result = runThreadedIntcodeSetting(perms.get(i), program);
			if(result > max)
				max = result;
		}

		return max;
	}

	public static long runThreadedIntcodeSetting(int[] setting, long[] program){
		IntcodeComputerThread[] ics = new IntcodeComputerThread[setting.length];
		ArrayBlockingQueue<Long>[] inputs = new ArrayBlockingQueue[setting.length];

		// Initialize all machines and link their inputs and outputs
		for(int i = 0; i < ics.length; i++){
			ics[i] = new IntcodeComputerThread(program);
			inputs[i] = new ArrayBlockingQueue<>(64);
			try {
				inputs[i].put((long) setting[i]);
				if(i == 0)
					inputs[i].put(0L);
			}
			catch (Exception e){ }
			ics[i].setInputArray(inputs[i]);
			if(i > 0)
				ics[i-1].setOutputArray(inputs[i]);
		}
		ics[setting.length-1].setOutputArray(inputs[0]);

		// Run all machines
		for(int i = 0; i < ics.length; i++){
			ics[i].start();
		}

		// Wait for the last machine to finish
		try { ics[ics.length-1].join();	}
		catch (Exception e) {
			System.err.println("Error");
			System.out.println(e.getMessage());
		}

		// Get last value of the last machine (assuming the last output is the only output left)
		ArrayBlockingQueue<Long> resultArray = ics[ics.length-1].getOutputArray();
		long result = resultArray.peek();

		return result;
	}
}
