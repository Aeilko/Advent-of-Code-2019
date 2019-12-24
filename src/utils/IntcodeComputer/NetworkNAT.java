package utils.IntcodeComputer;

import java.util.concurrent.ArrayBlockingQueue;

public class NetworkNAT extends Thread {

	// Thread stopper
	private boolean stop;

	// The network this NAT is a part of
	private NetworkNode nn;

	// Our method of receiving packets.
	private ArrayBlockingQueue<Long> input;

	// The last received packet
	private long prevX;
	private long prevY;

	// Day 23, part 2 vars
	private long lastSentY = -1;

	public NetworkNAT(NetworkNode nn){
		this.nn = nn;
		this.input = new ArrayBlockingQueue<>(64);
	}

	public ArrayBlockingQueue<Long> getQueue(){
		return this.input;
	}

	public void run(){
		try {
			while (!this.stop) {
				// See if we received any packets
				while(this.input.size() > 0){
					long x = this.input.take();
					long y = this.input.take();
					this.prevX = x;
					this.prevY = y;
				}

				Thread.sleep(500);

				// Check if the network is idle
				// Idle is not really defined, so we just assume some time of inactivity is enough
				long lastActive = this.nn.getActivity();
				long idle = System.currentTimeMillis() - this.nn.getActivity();
				if(lastActive != -1 && idle > 3000){
					if(this.prevY == this.lastSentY){
						// We are about to sent the same Y value again, notify the main thread
						NetworkPacket p = new NetworkPacket(254L);
						p.addValue(this.prevY);
						this.nn.sent(p);
					}
					NetworkPacket p = new NetworkPacket(0L);
					p.addValue(this.prevX);
					p.addValue(this.prevY);
					this.nn.sent(p);

					this.lastSentY = this.prevY;
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
