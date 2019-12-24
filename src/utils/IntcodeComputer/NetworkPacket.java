package utils.IntcodeComputer;

import java.util.ArrayList;

public class NetworkPacket {

	private long to;
	private ArrayList<Long> data;

	public NetworkPacket(long to){
		this.to = to;
		this.data = new ArrayList<>();
	}

	public void addValue(long val){
		this.data.add(val);
	}

	public long getTo(){
		return this.to;
	}

	public ArrayList<Long> getValues(){
		return this.data;
	}
}
