package day2;

import utils.IntcodeComputer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

// TODO: Rewrite solution to use the IntcodeComputer class
public class Solution {

	public static final int PART1_X = 12;
	public static final int PART1_Y = 2;

	public static final int PART2 = 19690720;

	public static final String INPUT = "1,0,0,3,1,1,2,3,1,3,4,3,1,5,0,3,2,1,6,19,1,19,6,23,2,23,6,27,2,6,27,31,2,13,31,35,1,9,35,39,2,10,39,43,1,6,43,47,1,13,47,51,2,6,51,55,2,55,6,59,1,59,5,63,2,9,63,67,1,5,67,71,2,10,71,75,1,6,75,79,1,79,5,83,2,83,10,87,1,9,87,91,1,5,91,95,1,95,6,99,2,10,99,103,1,5,103,107,1,107,6,111,1,5,111,115,2,115,6,119,1,119,6,123,1,123,10,127,1,127,13,131,1,131,2,135,1,135,5,0,99,2,14,0,0";

	public static int[] part2(int[] stack){

		outer: for(int i = 0; i < 100; i++){
			for(int j = 0; j < 100; j++){
				int[] result = stack.clone();
				result[1] = i;
				result[2] = j;
				try {
					IntcodeComputer ic = new IntcodeComputer(result);
					ic.run();
					result = ic.getStack();

					if(result[0] == PART2)
						return result;
				}
				catch(Exception e){	}
			}
		}

		// Not found
		return null;
	}

	public static void main(String[] args){
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

		// Read the and process input line
		String input = in.nextLine();
		// Use default input
		if(input.equals("def"))
			input = INPUT;

		// Process input to stack
		String[] sep = input.split(",");
		int[] ogStack = new int[sep.length];
		try {
			// Parse input to numbers
			for(int i = 0; i < sep.length; i++){
				ogStack[i] = Integer.parseInt(sep[i]);
			}

			// Perform part 1 (running the input)
			int[] stack = ogStack.clone();
			stack[1] = PART1_X;
			stack[2] = PART1_Y;
			IntcodeComputer ic = new IntcodeComputer(stack);
			ic.run();
			stack = ic.getStack();
			System.out.println("Part 1 - Success");
			System.out.println("Answer\t" + stack[0] + "\n");

			// Perform part 2 (brute force values)
			stack = part2(ogStack.clone());
			if(stack == null){
				System.out.println("Could not find noun and verb");
			}
			else{
				System.out.println("Part 2 - Success");
				System.out.println("Noun\t" + stack[1]);
				System.out.println("Verb\t" + stack[2]);
				System.out.println("Answer\t" + ((100*stack[1]) + stack[2]));
			}
		}
		catch(NumberFormatException e){
			System.err.println("Some part of the input is not an integer");
			System.out.println(e.getMessage());
		}
		catch(Exception e){
			System.err.println("Wrong intcode");
			System.out.println(e.getMessage());
		}

	}
}
