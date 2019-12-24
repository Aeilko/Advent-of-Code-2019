package utils.IntcodeComputer;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.concurrent.ArrayBlockingQueue;

public class NetworkNode extends Thread {

	// The packet queue
	private ArrayBlockingQueue<NetworkPacket> packets;

	// Waiting packets
	private TreeMap<Long, ArrayList<NetworkPacket>> waiting;

	// The input arrays for the ICs
	private TreeMap<Long, ArrayBlockingQueue<Long>> network;

	// Thread stopper
	private boolean stop;

	public NetworkNode(){
		this.network = new TreeMap<>();
	}

	public void registerIC(long addr, IntcodeComputer ic){
		this.network.put(addr, ic.getInputArray());

		// See if we have any packets waiting for this address
		if(this.waiting != null && this.waiting.containsKey(addr)){
			ArrayList<NetworkPacket> tmp = this.waiting.get(addr);
			for(NetworkPacket p: tmp){
				this.packets.add(p);
			}
			this.waiting.remove(addr);
		}
	}

	public void sent(NetworkPacket p) throws InterruptedException {
		//System.out.println("Sending packet: (" + p.getTo() + ", " + p.getValues().get(0) + ", " + p.getValues().get(1) + ")");
		this.packets.put(p);
	}

	public void run(){
		this.packets = new ArrayBlockingQueue<>(256);
		this.waiting = new TreeMap<>();

		try {
			while(!this.stop){
				NetworkPacket p = this.packets.take();
				long addr = p.getTo();

				if(network.containsKey(addr)){
					ArrayBlockingQueue<Long> in = network.get(addr);
					for(long l: p.getValues())
						in.put(l);
				}
				else{
					// Address currently unavailable, add it to the waiting list
					if(!this.waiting.containsKey(addr))
						this.waiting.put(addr, new ArrayList<>());
					this.waiting.get(addr).add(p);
				}
			}
		}
		catch (InterruptedException e) {
			// This is fine
		}
	}

	public void stopThread(){
		this.stop = true;
		this.interrupt();
	}
}
