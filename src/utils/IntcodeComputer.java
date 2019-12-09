package utils;

import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class IntcodeComputer {

	// Maximum size of the stack
	private int memorySize = 2048;

	// The stack used by this Intcode computer
	private long[] stack;

	// Number of parameters per intcode
	private int[] params;

	// Relative base number
	private int relativeBase = 0;

	// Whether the computer should hide anything but output
	private boolean silent = false;

	// Which input & output mode tho use. 1 = System, 2 = BlockingQueue
	private int inputMode;

	// Potential input scanner
	Scanner in;

	// Potential input and output arrays
	ArrayBlockingQueue<Long> inArray;
	ArrayBlockingQueue<Long> outArray;

	public IntcodeComputer(long[] s){
		this(s, 1);
	}

	public IntcodeComputer(long[] s, int mode){
		this.stack = new long[this.memorySize];
		System.arraycopy(s, 0, this.stack, 0, s.length);
		this.inputMode = mode;

		// Make sure we include intcode 99, although it has not parameters
		int[] p = new int[100];
		p[1] = p[2] = 3;
		p[3] = p[4] = 1;
		p[5] = p[6] = 2;
		p[7] = p[8] = 3;
		p[9] = 1;

		this.params = p;
	}

	public void run() throws Exception{
		// Prepare scanner
		if(this.inputMode == 1)
			this.in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));

		int i = 0;
		run: while(i < this.stack.length){
			// Split instruction into digits
			long tmp = stack[i];
			//System.out.println("Intcode\t" + tmp);
			//System.out.println("RelativeBase\t" + this.relativeBase);
			int length = Long.toString(stack[i]).length();
			int[] digits = new int[length];
			for(int j = 0; j < length; j++){
				digits[j] = (int) tmp%10;
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
					// Addition
					stack[(int) (modes[2] == 2 ? this.relativeBase+stack[i+3] : stack[i+3])] = resolveParamValue(modes[0], stack[i+1]) + resolveParamValue(modes[1], stack[i+2]);
					break;
				case 2:
					// Multiplication
					stack[(int) (modes[2] == 2 ? this.relativeBase+stack[i+3] : stack[i+3])] = resolveParamValue(modes[0], stack[i+1]) * resolveParamValue(modes[1], stack[i+2]);
					break;
				case 3:
					// Read
					stack[(int) (modes[0] == 2 ? this.relativeBase+stack[i+1] : stack[i+1])] = this.getInput();
					break;
				case 4:
					// Write
					this.setOutput(resolveParamValue(modes[0], stack[i+1]));
					break;
				case 5:
					// If true jump
					if(resolveParamValue(modes[0], stack[i+1]) != 0){
						i = (int) resolveParamValue(modes[1], stack[i+2]);
						pointerModified = true;
					}
					break;
				case 6:
					// If false jump
					if(resolveParamValue(modes[0], stack[i+1]) == 0){
						i = (int) resolveParamValue(modes[1], stack[i+2]);
						pointerModified = true;
					}
					break;
				case 7:
					// Smaller than
					if(resolveParamValue(modes[0], stack[i+1]) < resolveParamValue(modes[1], stack[i+2]))
						stack[(int) (modes[2] == 2 ? this.relativeBase+stack[i+3] : stack[i+3])] = 1;
					else
						stack[(int) (modes[2] == 2 ? this.relativeBase+stack[i+3] : stack[i+3])] = 0;
					break;
				case 8:
					// Equal to
					if(resolveParamValue(modes[0], stack[i+1]) == resolveParamValue(modes[1], stack[i+2]))
						stack[(int) (modes[2] == 2 ? this.relativeBase+stack[i+3] : stack[i+3])] = 1;
					else
						stack[(int) (modes[2] == 2 ? this.relativeBase+stack[i+3] : stack[i+3])] = 0;
					break;
				case 9:
					// Update relative pointer
					this.relativeBase += resolveParamValue(modes[0], stack[i+1]);
					break;
				case 99:
					// End
					break run;
				default:
					throw new Exception("Unknown Intcode at ["+i+"]: " + intcode);
			}

			// Increase pointer
			if(!pointerModified)
				i += this.params[intcode]+1;
		}
	}

	private long getInput() throws InterruptedException {
		long result = 0;

		if(this.inputMode == 2){
			result = this.inArray.take();
		}
		else{
			// inputMode == 1
			if(!this.silent)
				System.out.print("Input integer: ");
			result = in.nextLong();
		}

		return result;
	}

	private void setOutput(long out) throws InterruptedException {
		if(this.inputMode == 2){
			this.outArray.put(out);
		}
		else{
			// inputMode == 1
			System.out.println(out);
		}
	}

	private long resolveParamValue(int mode, long val){
		//System.out.print("Resolve \t" + mode +"\t" + val);
		long result = 0;

		if(mode == 0)
			result = this.stack[(int) val];
		else if(mode == 1)
			result = val;
		else if(mode == 2)
			result = this.stack[this.relativeBase + (int) val];

		//System.out.println("\t" + result);

		return result;
	}

	public void setStack(long[]s){
		this.stack = s.clone();
	}

	public long[] getStack(){
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

	public static long[] proccessInput(){
		return processInput("");
	}

	public static long[] processInput(String def){
		Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
		System.out.print("Input Program " + (!def.isEmpty() ? "(or def)" : "") + ": ");
		String input = in.nextLine();
		if(!def.isEmpty() && input.equals("def"))
			input = def;

		// Process input to stack
		String[] sep = input.split(",");
		long[] stack = new long[sep.length];
		for(int i = 0; i < sep.length; i++){
			stack[i] = Long.parseLong(sep[i]);
		}

		System.out.println();

		return stack;
	}
}
