package day12;

import utils.ArrayUtils;
import utils.Euclidean;
import utils.Triplet;

import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

	public static final String[] INPUT = {"<x=4, y=12, z=13>","<x=-9, y=14, z=-3>","<x=-7, y=-1, z=2>","<x=-11, y=17, z=-1>"};

	public static void main(String[] args){
		Pattern pat = Pattern.compile("^<x=(-?[0-9]+), y=(-?[0-9]+), z=(-?[0-9]+)>");

		// Parse input
		Triplet[] positions = new Triplet[INPUT.length];
		Triplet[] velocities = new Triplet[INPUT.length];
		for(int i = 0; i < INPUT.length; i++){
			Matcher m = pat.matcher(INPUT[i]);
			m.find();
			int x = Integer.parseInt(m.group(1));
			int y = Integer.parseInt(m.group(2));
			int z = Integer.parseInt(m.group(3));
			positions[i] = new Triplet(x, y ,z);
			velocities[i] = new Triplet();
		}

		// Create copies for part 2
		Triplet[] ogPositions = ArrayUtils.deepCopy(positions);
		Triplet[] ogVelocities = ArrayUtils.deepCopy(velocities);

		// Perform steps
		int steps = 1000;
		for(int i = 0; i < steps; i++){
			doStep(positions, velocities);
		}

		// Calculate energy
		int energy = 0;
		for(int i = 0; i < positions.length; i++) {
			Triplet p = positions[i];
			Triplet v = velocities[i];

			energy += (Math.abs(p.getX()) + Math.abs(p.getY()) + Math.abs(p.getZ())) * (Math.abs(v.getX()) + Math.abs(v.getY()) + Math.abs(v.getZ()));
		}

		// Finished part 1
		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + energy + "\n");

		// Prepare our trackers of previous states
		positions = ArrayUtils.deepCopy(ogPositions);
		velocities = ArrayUtils.deepCopy(ogVelocities);
		TreeSet<String> prevPositionsX = new TreeSet<>();
		prevPositionsX.add(getAllX(positions, velocities));
		TreeSet<String> prevPositionsY = new TreeSet<>();
		prevPositionsY.add(getAllY(positions, velocities));
		TreeSet<String> prevPositionsZ = new TreeSet<>();
		prevPositionsZ.add(getAllZ(positions, velocities));

		// Find the repeats cycles for each axis
		int[] rep = new int[3];
		for(int i = 1; i < Integer.MAX_VALUE; i++){
			doStep(positions, velocities);

			// Check for each axis if it has been in this state before
			String x = getAllX(positions, velocities);
			if(rep[0] == 0 && prevPositionsX.contains(x))
				rep[0] = i;
			else
				prevPositionsX.add(x);

			String y = getAllY(positions, velocities);
			if(rep[1] == 0 && prevPositionsY.contains(y))
				rep[1] = i;
			else
				prevPositionsY.add(y);

			String z = getAllZ(positions, velocities);
			if(rep[2] == 0 && prevPositionsZ.contains(z))
				rep[2] = i;
			else
				prevPositionsZ.add(z);

			// Break if we found all values
			if(rep[0] != 0 && rep[1] != 0 && rep[2] != 0)
				break;
		}

		System.out.println("Part 2 - Success");
		System.out.println("Answer\t" + Euclidean.lcm(rep));
	}

	private static void doStep(Triplet[] pos, Triplet[] vel){
		// Update velocities
		for(int x = 0; x < pos.length; x++){
			for(int y = x+1; y < pos.length; y++){
				if(pos[x].getX() < pos[y].getX()){
					vel[x].incX();
					vel[y].decX();
				}
				else if(pos[y].getX() < pos[x].getX()){
					vel[x].decX();
					vel[y].incX();
				}

				if(pos[x].getY() < pos[y].getY()){
					vel[x].incY();
					vel[y].decY();
				}
				else if(pos[y].getY() < pos[x].getY()){
					vel[x].decY();
					vel[y].incY();
				}

				if(pos[x].getZ() < pos[y].getZ()){
					vel[x].incZ();
					vel[y].decZ();
				}
				else if(pos[y].getZ() < pos[x].getZ()){
					vel[x].decZ();
					vel[y].incZ();
				}
			}
		}

		// Update positions
		for(int i = 0; i < pos.length; i++){
			pos[i].setX(pos[i].getX() + vel[i].getX());
			pos[i].setY(pos[i].getY() + vel[i].getY());
			pos[i].setZ(pos[i].getZ() + vel[i].getZ());
		}
	}

	private static String getAllX(Triplet[] pos, Triplet[] vel) {
		String result = "";

		for(int i = 0; i < pos.length; i++){
			result += pos[i].getX() + ";" + vel[i].getX() + ";";
		}

		return result;
	}

	private static String getAllY(Triplet[] pos, Triplet[] vel) {
		String result = "";

		for(int i = 0; i < pos.length; i++){
			result += pos[i].getY() + ";" + vel[i].getY() + ";";
		}

		return result;
	}

	private static String getAllZ(Triplet[] pos, Triplet[] vel) {
		String result = "";

		for(int i = 0; i < pos.length; i++){
			result += pos[i].getZ() + ";" + vel[i].getZ() + ";";
		}

		return result;
	}

}
