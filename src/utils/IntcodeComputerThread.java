package utils;

import java.util.concurrent.ArrayBlockingQueue;

public class IntcodeComputerThread extends Thread {

	// The Intcode Computer
	private IntcodeComputer ic;

	// The stack for the Intcode Computer
	private long[] stack;

	private ArrayBlockingQueue inArray;
	private ArrayBlockingQueue outArray;


	private boolean silent;

	public IntcodeComputerThread(long[] stack){
		this.stack = stack;
	}

	public void run(){
		this.ic = new IntcodeComputer(this.stack, 2);
		this.ic.setSilent();
		this.ic.setInputArray(this.inArray);
		this.ic.setOutputArray(this.outArray);

		try {
			this.ic.run();
		}
		catch(Exception e){
			System.err.println("Error in IntcodeComputerThread");
			System.out.println(e.getMessage());
		}
	}

	public void setInputArray(ArrayBlockingQueue in){
		this.inArray = in;
	}

	public void setOutputArray(ArrayBlockingQueue out){
		this.outArray = out;
	}

	public ArrayBlockingQueue<Integer> getOutputArray(){
		return this.outArray;
	}
}
