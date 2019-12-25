package utils.IntcodeComputer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

public class IntcodeComputer {

	// Maximum size of the stack
	private int memorySize = 6000;

	// The stack used by this Intcode computer
	private long[] stack;

	// Number of parameters per intcode
	private int[] params;

	// Relative base number
	private int relativeBase = 0;

	// Whether the computer should hide anything but output
	private boolean silent = false;

	// Whether or not to write a null byte to output array on finish
	private boolean nullByte = false;

	// Which input & output mode tho use. 1 = System, 2 = BlockingQueue, 3 = Network
	private int inputMode;

	// Potential input scanner
	private Scanner in;

	// Potential input and output arrays
	private ArrayBlockingQueue<Long> inArray;
	private ArrayBlockingQueue<Long> outArray;

	// Potential network node
	private NetworkNode network;
	private ArrayList<Long> values;
	private int valuesPerPacket = 2;

	// Thread stopper
	private boolean stop;


	public IntcodeComputer(long[] s){
		this(s, 1);
	}

	public IntcodeComputer(long[] s, int mode){
		this.stack = new long[Math.max(s.length, this.memorySize)];
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
		run: while(i < this.stack.length && !this.stop){
			// Split instruction into digits
			long tmp = stack[i];
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
					stack[(int) resolveParamKey(modes[2], stack[i+3])] = resolveParamValue(modes[0], stack[i+1]) + resolveParamValue(modes[1], stack[i+2]);
					break;
				case 2:
					// Multiplication
					stack[(int) resolveParamKey(modes[2], stack[i+3])] = resolveParamValue(modes[0], stack[i+1]) * resolveParamValue(modes[1], stack[i+2]);
					break;
				case 3:
					// Read
					stack[(int) resolveParamKey(modes[0], stack[i+1])] = this.getInput();
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
						stack[(int) resolveParamKey(modes[2], stack[i+3])] = 1;
					else
						stack[(int) resolveParamKey(modes[2], stack[i+3])] = 0;
					break;
				case 8:
					// Equal to
					if(resolveParamValue(modes[0], stack[i+1]) == resolveParamValue(modes[1], stack[i+2]))
						stack[(int) resolveParamKey(modes[2], stack[i+3])] = 1;
					else
						stack[(int) resolveParamKey(modes[2], stack[i+3])] = 0;
					break;
				case 9:
					// Update relative pointer
					this.relativeBase += resolveParamValue(modes[0], stack[i+1]);
					break;
				case 99:
					// End
					if(this.nullByte && inputMode == 2)
						setOutput(Long.MIN_VALUE);
					break run;
				default:
					throw new Exception("Unknown Intcode at ["+i+"]: " + stack[i] + ", " + intcode);
			}

			// Increase pointer
			if(!pointerModified)
				i += this.params[intcode]+1;
		}
	}

	public void stopThread(){
		this.stop = true;
	}

	private long getInput() throws InterruptedException {
		long result;

		if(this.inputMode == 3){
			// Network
			if(this.inArray.peek() == null)
				result = -1L;
			else
				result = this.inArray.take();
		}
		else if(this.inputMode == 2){
			// Array
			result = this.inArray.take();
		}
		else{
			// Assume inputMode == 1, System
			if(!this.silent)
				System.out.print("Input integer: ");
			result = in.nextLong();
		}

		return result;
	}

	private void setOutput(long out) throws InterruptedException {
		if(this.inputMode == 3){
			this.values.add(out);
			if(this.values.size() == this.valuesPerPacket+1){
				long address = this.values.get(0);
				NetworkPacket p = new NetworkPacket(address);
				for(int i = 1; i < this.values.size(); i++)
					p.addValue(this.values.get(i));

				this.values = new ArrayList<>();

				this.network.sent(p);
			}
		}
		else if(this.inputMode == 2){
			this.outArray.put(out);
		}
		else{
			// Assume inputMode == 1
			System.out.println(out);
		}
	}

	private long resolveParamKey(int mode, long val){
		long result;

		if(mode == 0)
			result = val;
		else
			result = this.relativeBase+val;

		return result;
	}

	private long resolveParamValue(int mode, long val){
		long result = 0;

		if(mode == 0)
			result = this.stack[(int) val];
		else if(mode == 1)
			result = val;
		else if(mode == 2)
			result = this.stack[this.relativeBase + (int) val];

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

	public void writeNullByte(){ this.nullByte = true; }

	public void setInputArray(ArrayBlockingQueue<Long> in){
		this.inArray = in;
	}

	public ArrayBlockingQueue<Long> getInputArray(){
		return this.inArray;
	}

	public void setOutputArray(ArrayBlockingQueue<Long> out){
		this.outArray = out;
	}

	public void setNetwork(NetworkNode network) {
		this.network = network;
		this.values = new ArrayList<>();
	}

	public static long[] proccessInput(){
		return processInput("");
	}

	public static long[] processInput(String def){
		// Read input and check if the default should be used
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
