package utils.IntcodeComputer;

import java.util.concurrent.ArrayBlockingQueue;

public class IntcodeComputerThread extends Thread {

	// The Intcode Computer
	private IntcodeComputer ic;

	// The stack for the Intcode Computer
	private long[] stack;

	// The mode of the Intcode computer
	private int mode;

	// The in and output arrays
	private ArrayBlockingQueue<Long> inArray;
	private ArrayBlockingQueue<Long> outArray;

	// The network
	private long addr;
	private NetworkNode nn;

	public IntcodeComputerThread(long[] stack){
		this(stack, 2);
	}

	public IntcodeComputerThread(long[] stack, int mode){
		this.stack = stack;
		this.mode = mode;
	}

	public void run(){
		//System.out.println("IntcodeComputerThread.run()");
		this.ic = new IntcodeComputer(this.stack, this.mode);
		this.ic.setSilent();
		if(this.mode == 2) {
			this.ic.writeNullByte();
			this.ic.setInputArray(this.inArray);
			this.ic.setOutputArray(this.outArray);
		}
		else if(this.mode == 3){
			this.ic.setInputArray(this.inArray);
			this.ic.setNetwork(this.nn);
			this.nn.registerIC(this.addr, this.ic);
		}

		try {
			this.ic.run();
		}
		catch(InterruptedException e){
			// This is fine
		}
		catch(Exception e){
			System.err.println("Error while running IntcodeComputerThread");
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public void stopThread(){
		this.ic.stopThread();
		this.interrupt();
	}

	public void setInputArray(ArrayBlockingQueue<Long> in){
		this.inArray = in;
	}

	public ArrayBlockingQueue<Long> getInputArray(){
		return this.inArray;
	}

	public void setOutputArray(ArrayBlockingQueue<Long> out){
		this.outArray = out;
	}

	public ArrayBlockingQueue<Long> getOutputArray(){
		return this.outArray;
	}

	public void setNetwork(long addr, NetworkNode nn){
		this.addr = addr;
		this.nn = nn;
		this.inArray = new ArrayBlockingQueue<>(512);
	}
}
