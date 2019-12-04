package day4;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Solution {

	public static final String INPUT = "138307-654504";

	public static void main(String[] args) {
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

		String input = in.nextLine();
		if(input.equals("def"))
			input = INPUT;

		String[] values = input.split("-");
		int start = Integer.parseInt(values[0]);
		int end = Integer.parseInt(values[1]);

		int result = 0;
		// Way better methods to do this using some logic, but this is easier
		outer: for(int i = start; i <= end; i++){
			String val = Integer.toString(i);
			boolean hasDouble = false;
			int prev = 0;
			for(char c: val.toCharArray()){
				int cur = Character.getNumericValue(c);
				if(cur < prev)
					continue outer;
				if(cur == prev)
					hasDouble = true;
				prev = cur;
			}

			if(hasDouble)
				result++;
		}

		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + result);


	}
}
