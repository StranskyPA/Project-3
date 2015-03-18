package packet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.PriorityQueue;

import application.Controller;

public class Depacketizer {
	//private PriorityQueue<Object> objectsOut;
	private Controller c;
	private Hashtable<Integer, File> partialFiles;
	
	public Depacketizer(Controller c) {
		//objectsOut = new PriorityQueue<Object>();
		this.c = c;
		partialFiles = new Hashtable<Integer, File>();
	}
	
	/*public boolean hasNextFile() {
		return !objectsOut.isEmpty();
	}
	
	public Object getNextFile() {
		return objectsOut.remove();
	}*/
	
	public void depacketize(Packet p) {
		char[] data = p.getAllData();
		File f;
		
		if(p.getPart() > 0) {
			f = partialFiles.get(p.getID());
		} else {
			f = new File(new String(data));
			partialFiles.put(p.getID(), f);
		}
		
		try {
			FileWriter writer = new FileWriter(f);
			writer.write(data);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(p.getPart() == p.getNumParts()) {
			//objectsOut.add(f);
			c.receiveMessage(f);
			partialFiles.remove(p.getID());
		}
	}
}
