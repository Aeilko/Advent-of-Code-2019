package utils;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class IntcodeComputer {

	// The stack used by this Intcode computer
	private int[] stack;

	// Number of parameters per intcode
	private int[] params;

	// Whether the computer should show anything but output
	private boolean silent = false;

	// Which input & output mode tho use. 1 = System, 2 = BlockingQueue
	private int inputMode;

	// Potential input scanner
	Scanner in;

	// Potential input and output arrays
	ArrayBlockingQueue<Integer> inArray;
	ArrayBlockingQueue<Integer> outArray;

	public IntcodeComputer(int[] s){
		this(s, 1);
	}

	public IntcodeComputer(int[] s, int mode){
		this.stack = s.clone();
		this.inputMode = mode;

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
		if(this.inputMode == 1)
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
					stack[stack[i+1]] = this.getInput();
					break;
				case 4:
					this.setOutput(resolveParamValue(modes[0], stack[i+1]));
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

	private int getInput() throws InterruptedException {
		int result = 0;

		if(this.inputMode == 2){
			result = this.inArray.take();
		}
		else{
			// inputMode == 1
			if(!this.silent)
				System.out.print("Input integer: ");
			result = in.nextInt();
		}

		return result;
	}

	private void setOutput(int out) throws InterruptedException {
		if(this.inputMode == 2){
			this.outArray.put(out);
		}
		else{
			// inputMode == 1
			System.out.println(out);
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

	public void setInputArray(ArrayBlockingQueue in){
		this.inArray = in;
	}

	public void setOutputArray(ArrayBlockingQueue out){
		this.outArray = out;
	}
}
