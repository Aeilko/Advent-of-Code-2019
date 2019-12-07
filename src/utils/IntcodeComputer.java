package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class IntcodeComputer {

	// The stack used by this Intcode computer
	private int[] stack;

	// Scanner for input
	private Scanner in;

	// Number of parameters per intcode
	private int[] params;

	// Whether the computer should show anything but output
	private boolean silent = false;

	public IntcodeComputer(int[] s){
		this.stack = s.clone();

		// Make sure we include intcode 99, although it has not parameters
		int[] p = new int[100];
		p[1] = p[2] = 3;
		p[3] = p[4] = 1;
		p[5] = p[6] = 2;
		p[7] = p[8] = 3;

		this.params = p;
	}

	public void run() throws Exception{
		// Prepare scanner
		this.in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

		int i = 0;
		run: while(i < this.stack.length){
			// Split instruction into digits
			int tmp = stack[i];
			int length = Integer.toString(stack[i]).length();
			int[] digits = new int[length];
			for(int j = 0; j < length; j++){
				digits[j] = tmp%10;
				tmp = tmp/10;
			}

			// Extract intcode
			int intcode = digits[0] + (digits.length > 1 ? digits[1]*10 : 0);

			// Extract parameter modes
			int[] modes = new int[this.params[intcode]];
			for(int j = 2; j < digits.length; j++){
				modes[j-2] = digits[j];
			}

			boolean pointerModified = false;
			switch(intcode){
				case 1:
					stack[stack[i+3]] = resolveParamValue(modes[0], stack[i+1]) + resolveParamValue(modes[1], stack[i+2]);
					break;
				case 2:
					stack[stack[i+3]] = resolveParamValue(modes[0], stack[i+1]) * resolveParamValue(modes[1], stack[i+2]);
					break;
				case 3:
					if(!this.silent)
						System.out.print("Input integer: ");
					stack[stack[i+1]] = this.in.nextInt();
					break;
				case 4:
					System.out.println(resolveParamValue(modes[0], stack[i+1]));
					break;
				case 5:
					if(resolveParamValue(modes[0], stack[i+1]) != 0){
						i = resolveParamValue(modes[1], stack[i+2]);
						pointerModified = true;
					}
					break;
				case 6:
					if(resolveParamValue(modes[0], stack[i+1]) == 0){
						i = resolveParamValue(modes[1], stack[i+2]);
						pointerModified = true;
					}
					break;
				case 7:
					if(resolveParamValue(modes[0], stack[i+1]) < resolveParamValue(modes[1], stack[i+2]))
						stack[stack[i+3]] = 1;
					else
						stack[stack[i+3]] = 0;
					break;
				case 8:
					if(resolveParamValue(modes[0], stack[i+1]) == resolveParamValue(modes[1], stack[i+2]))
						stack[stack[i+3]] = 1;
					else
						stack[stack[i+3]] = 0;
					break;
				case 99:
					break run;
				default:
					throw new Exception("Unknown Intcode at ["+i+"]: " + intcode);
			}

			// Increase pointer
			if(!pointerModified)
				i += this.params[intcode]+1;
		}
	}

	private int resolveParamValue(int mode, int val){
		//System.out.print("Resolve \t" + mode +"\t" + val);
		int result = 0;

		if(mode == 0)
			result = this.stack[val];
		else if(mode == 1)
			result = val;

		//System.out.println("\t" + result);

		return result;
	}

	public void setStack(int[]s){
		this.stack = s.clone();
	}

	public int[] getStack(){
		return this.stack.clone();
	}

	public void setSilent(){
		this.silent = true;
	}
}
