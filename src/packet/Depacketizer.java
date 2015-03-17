package packet;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.PriorityQueue;

public class Depacketizer {
	private PriorityQueue<Object> objectsOut;
	private Hashtable<Byte, File> partialFiles;
	private Hashtable<Byte, String> partialStrings;
	
	public Depacketizer() {
		objectsOut = new PriorityQueue<Object>();
		partialFiles = new Hashtable<Byte, File>();
		partialStrings = new Hashtable<Byte, String>();
	}
	
	public boolean hasNextFile() {
		return !objectsOut.isEmpty();
	}
	
	public Object getNextFile() {
		return objectsOut.remove();
	}
	
	private byte getID(Packet p) {
		return p.get(0);
	}
	
	private int getPart(Packet p) {
		int part = p.get(1);
		part = (part << 8) + p.get(2);
		part = (part << 8) + p.get(3);
		part = (part << 8) + p.get(4);
		
		return part;
	}
	
	private int getNumParts(Packet p) {
		int totalNumParts = p.get(5);
		totalNumParts = (totalNumParts << 8) + p.get(6);
		totalNumParts = (totalNumParts << 8) + p.get(7);
		totalNumParts = (totalNumParts << 8) + p.get(8);
		
		return totalNumParts;
	}
	
	private char getType(Packet p) {
		return (char)(p.get(9));
	}
	
	private char[] get_cbuf(Packet p) {
		char[] cbuf = new char[p.length() -10];
		for(int i=0; i<cbuf.length; i++) {
			cbuf[i] = (char)(p.get(i+10));
		}
		return cbuf;
	}
	
	public void depacketize(Packet p) {
		if(getType(p) == 'f')
			makeFile(p);
		else
			makeString(p);
	}
	
	
	private void makeString(Packet p) {
		char[] cbuf = get_cbuf(p);
		String s;
		
		if(getPart(p) > 0) {
			s = partialStrings.get(getID(p));
		} else {
			s = "";
			partialStrings.put(getID(p), s);
		}
		
		for(int i=0; i<cbuf.length; i++) {
			s += cbuf[i];
		}
		
		if(getPart(p) == getNumParts(p)) {
			objectsOut.add(s);
			partialStrings.remove(getID(p));
		}
	}
	
	private void makeFile(Packet p) {
		char[] cbuf = get_cbuf(p);
		File f;
		
		if(getPart(p) > 0) {
			f = partialFiles.get(getID(p));
		} else {
			f = new File("" + getID(p));
			partialFiles.put(getID(p), f);
		}
		
		try {
			FileWriter writer = new FileWriter(f);
			writer.write(cbuf);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(getPart(p) == getNumParts(p)) {
			objectsOut.add(f);
			partialFiles.remove(getID(p));
		}
	}
}
