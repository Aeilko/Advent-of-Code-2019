package day16;

public class Solution {

	public static final String INPUT = "59762770781817719190459920638916297932099919336473880209100837309955133944776196290131062991588533604012789279722697427213158651963842941000227675363260513283349562674004015593737518754413236241876959840076372395821627451178924619604778486903040621916904575053141824939525904676911285446889682089563075562644813747239285344522507666595561570229575009121663303510763018855038153974091471626380638098740818102085542924937522595303462725145620673366476987473519905565346502431123825798174326899538349747404781623195253709212209882530131864820645274994127388201990754296051264021264496618531890752446146462088574426473998601145665542134964041254919435635";
	public static final short[] pattern = {0,1,0,-1};

	public static void main(String[] args){
		String res = INPUT;
		for(int i = 0; i < 100; i++) {
			res = applyRound(res);
		}

		System.out.println("Part 1 - Success");
		System.out.println("Answer\t" + res.substring(0, 8) + "\n");

		// In the second case he value is to long to calculate normally, so we have to find some shortcuts.
		// - We can disregard everything before our index location, because in the pattern these are multiplied with 0.
		// - If our offset is larger than half of the total length (which it is) we know the pattern will always result in 1 (after the discarded part)
		// Combining these two we can now simplify the transformation by simply summing every number with the number to its right to get its new value
		// Also, the last index is never updated, since it only uses its own value multiplied with 1

		// Create our input
		res = INPUT;
		int offset = Integer.parseInt(res.substring(0, 7));
		int ogLength = res.length();
		short[] result = new short[(ogLength*10000)-offset];
		for(int i = 0; i < result.length; i++){
			result[i] = Short.parseShort(""+res.charAt((offset+i)%ogLength));
		}

		// Applying our new method
		for(int i = 0; i < 100; i++){
			for(int j = result.length-2; j >= 0; j--){
				result[j] = (short) ((result[j]+result[j+1]) % 10);
			}
		}

		System.out.println("Part 2 - Success");
		System.out.print("Answer\t");
		for(int i = 0; i < 8; i++){
			System.out.print(result[i]);
		}
	}

	public static String applyRound(String val){
		String result = "";

		int cycle = pattern.length;
		int sum = 0;
		for(int n = 1; n <= val.length(); n++) {
			sum = 0;
			for (int i = 0; i < val.length(); i++) {
				short s = Short.parseShort("" + val.charAt(i));
				int index = (((i+1)/n) % cycle);
				sum += pattern[index] * s;
			}
			String res = ""+sum;
			result += res.charAt(res.length()-1);
		}

		return result;
	}
}
