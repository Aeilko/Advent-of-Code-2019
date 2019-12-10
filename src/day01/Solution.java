package day01;

public class Solution {

	public static final int[] INPUT = {137654,50175,66976,57860,83790,54910,54619,116199,143411,51671,115872,71948,148635,92858,90939,127238,127895,124557,78529,55785,61550,138304,128465,74497,136583,66010,64320,91234,51418,90761,136134,66312,135172,126256,54124,53174,72252,145546,80950,52271,118231,79687,106127,108233,69510,124533,105202,54861,103087,81646,107634,105240,137434,60812,90066,149325,117535,107526,50814,51850,90707,110908,51739,96373,91987,144389,75682,142954,83555,93320,101914,117640,109401,106950,54521,130826,109942,55360,105653,92168,149572,79571,88590,104400,109598,51623,86649,143689,88350,143809,50119,75616,86361,76884,62339,83118,76847,137105,130460,54479};

	public static int part1(int[] mass){
		int fuel = 0;

		for(int i: mass){
			// Java integer division rounds down by default
			fuel += ((i/3)-2);
		}

		return fuel;
	}

	public static int part2(int[] mass){
		int fuel = 0;

		for(int i: mass){
			// Add the default amount of fuel
			int addedFuel = ((i/3)-2);
			fuel += addedFuel;

			// Add the fuel required to transport the fuel
			while(addedFuel > 8){
				addedFuel = ((addedFuel/3)-2);
				fuel += addedFuel;
			}
		}

		return fuel;
	}

	public static void main(String[] args){
		// Part 1 - Calculate fuel
		int fuel = part1(INPUT);
		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + fuel);
		System.out.println();

		// Part 2
		fuel = part2(INPUT);
		System.out.println("Part 2 - Success");
		System.out.println("Answer\t" + fuel);
	}
}
