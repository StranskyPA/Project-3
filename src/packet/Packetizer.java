package packet;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.PriorityQueue;

public class Packetizer {
	private PriorityQueue<Packet> packetsOut;
	private int packetSize;
	
	public Packetizer(int packetSize) {
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
		int numParts = (int)(f.length()/(packetSize-16));
		try {
			reader = new FileReader(f);
			char[] data = new char[packetSize];
			
			String name = f.getName();
			data = name.toCharArray();
			packetsOut.add(new Packet(f.hashCode(), 0, numParts, name.length(), data));
			
			for(int i=1; i <= (int)(f.length()/(packetSize-16)); i++) {
				int size = reader.read(data);
				
				if(size != -1)
					packetsOut.add(new Packet(f.hashCode() , i, numParts, size, data));
				else
					packetsOut.add(new Packet(f.hashCode() , i, numParts, (int)(f.length()%(packetSize-16)), data));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
