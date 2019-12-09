package utils;

import java.util.ArrayList;

public class Permutations {

	/**
	 * Borrowed from: https://www.baeldung.com/java-array-permutations
	 */
	public static ArrayList<int[]> getAll(int[] input){
		int n = input.length;
		int[] elements = input.clone();
		int[] indexes = new int[n];

		ArrayList<int[]> permutations = new ArrayList<>();
		permutations.add(elements.clone());

		for (int i = 0; i < n; i++) {
			indexes[i] = 0;
		}

		int i = 0;
		while (i < n) {
			if (indexes[i] < i) {
				swap(elements, i % 2 == 0 ?  0: indexes[i], i);
				permutations.add(elements.clone());
				indexes[i]++;
				i = 0;
			}
			else {
				indexes[i] = 0;
				i++;
			}
		}

		return permutations;
	}

	private static void swap(int[] input, int a, int b) {
		int tmp = input[a];
		input[a] = input[b];
		input[b] = tmp;
	}
}
