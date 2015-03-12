package packet;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class Packetizer {
	private byte IDCount;
	private PriorityQueue<Packet> /*filesIn,*/ packetsOut;
	private int packetSize;
	
	public Packetizer(int packetSize) {
		IDCount = 0;
		//filesIn = new PriorityQueue<Packet>();
		packetsOut = new PriorityQueue<Packet>();
		this.packetSize = packetSize;
	}
	
	public boolean hasNextPacket() {
		return !packetsOut.isEmpty();
	}
	
	public Packet getNextPacket() {
		return packetsOut.remove();
	}
	
	public void packetize(File f) {
		FileReader reader;
		try {
			reader = new FileReader(f);
			char[] cbuf = new char[packetSize];
			for(int i=0; i <= f.length()/packetSize; i++) {
				reader.read(cbuf);
				packetsOut.add(new Packet((byte)(f.hashCode() + IDCount),i, (int)(f.length()/packetSize), 'f', cbuf));
			}
			IDCount++;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void packetize(String s) {
		char[] cbuf = new char[packetSize];
		for(int i=0; i<=s.length()/packetSize; i++) {
			if((i+1)*packetSize <= s.length()) {
				s.getChars(i*packetSize, (i+1)*packetSize, cbuf, 0);
			} else if(i*packetSize < s.length()) {
				s.getChars(i*packetSize, s.length(), cbuf, 0);
			}
			packetsOut.add(new Packet((byte)(s.hashCode() + IDCount), i, (int)(s.length()/packetSize), 's', cbuf));
		}
		IDCount++;
	}
}
