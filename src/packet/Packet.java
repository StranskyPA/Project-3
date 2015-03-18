package packet;

import java.net.DatagramPacket;

public class Packet{
	private byte[] bytes;

	private static final byte POSITIVE_BITS = 127;
	private static final byte LAST_BITS = 15;
	
	private static final int ID_IDX = 0;
	private static final int PART_IDX = 5;
	private static final int NUM_PARTS_IDX = 10;
	private static final int DATA_SIZE_IDX = 15;
	private static final int DATA_START_IDX = 20;
	
	public static final int HEADER_SIZE = DATA_START_IDX;

	public Packet(int id, int part, int totalNumParts, int dataSize, byte[] data) {
		bytes = new byte[dataSize + Packet.HEADER_SIZE];
		
		bytes[ID_IDX] = (byte)((id >>> 25) & POSITIVE_BITS);
		bytes[ID_IDX+1] = (byte)((id >>> 18) & POSITIVE_BITS);
		bytes[ID_IDX+2] = (byte)((id >>> 11) & POSITIVE_BITS);
		bytes[ID_IDX+3] = (byte)((id >>> 4) & POSITIVE_BITS);
		bytes[ID_IDX+4] = (byte)(id);
		
		bytes[PART_IDX] = (byte)((part >>> 25) & POSITIVE_BITS);
		bytes[PART_IDX+1] = (byte)((part >>> 18) & POSITIVE_BITS);
		bytes[PART_IDX+2] = (byte)((part >>> 11) & POSITIVE_BITS);
		bytes[PART_IDX+3] = (byte)((part >>> 4) & POSITIVE_BITS);
		bytes[PART_IDX+4] = (byte)(part);
		
		bytes[NUM_PARTS_IDX] = (byte)((totalNumParts >>> 25) & POSITIVE_BITS);
		bytes[NUM_PARTS_IDX+1] = (byte)((totalNumParts >>> 18) & POSITIVE_BITS);
		bytes[NUM_PARTS_IDX+2] = (byte)((totalNumParts >>> 11) & POSITIVE_BITS);
		bytes[NUM_PARTS_IDX+3] = (byte)((totalNumParts >>> 4) & POSITIVE_BITS);
		bytes[NUM_PARTS_IDX+4] = (byte)(totalNumParts);
		
		bytes[DATA_SIZE_IDX] = (byte)((dataSize >>> 25) & POSITIVE_BITS);
		bytes[DATA_SIZE_IDX+1] = (byte)((dataSize >>> 18) & POSITIVE_BITS);
		bytes[DATA_SIZE_IDX+2] = (byte)((dataSize >>> 11) & POSITIVE_BITS);
		bytes[DATA_SIZE_IDX+3] = (byte)((dataSize >>> 4) & POSITIVE_BITS);
		bytes[DATA_SIZE_IDX+4] = (byte)(dataSize);
		
		for(int i=DATA_START_IDX; i < dataSize + DATA_START_IDX; i++) {
			bytes[i] = data[i - DATA_START_IDX];
		}
	}
	
	public Packet(DatagramPacket p) {
		bytes = p.getData();
	}
	
	public int length() {
		return bytes.length;
	}
	
	public int getID() {
		int id = bytes[ID_IDX];
		id = (id << 7) + bytes[ID_IDX+1];
		id = (id << 7) + bytes[ID_IDX+2];
		id = (id << 7) + bytes[ID_IDX+3];
		id = (id << 4) | (bytes[ID_IDX+4] & LAST_BITS);
		return id;
	}
	
	public int getPart() {
		int part = bytes[PART_IDX];
		part = (part << 7) + bytes[PART_IDX+1];
		part = (part << 7) + bytes[PART_IDX+2];
		part = (part << 7) + bytes[PART_IDX+3];
		part = (part << 4) | (bytes[PART_IDX+4] & LAST_BITS);
		return part;
	}
	
	public int getNumParts() {
		int numParts = bytes[NUM_PARTS_IDX];
		numParts = (numParts << 7) + bytes[NUM_PARTS_IDX+1];
		numParts = (numParts << 7) + bytes[NUM_PARTS_IDX+2];
		numParts = (numParts << 7) + bytes[NUM_PARTS_IDX+3];
		numParts = (numParts << 4) | (bytes[NUM_PARTS_IDX+4] & LAST_BITS);
		return numParts;
	}
	
	public int getSize() {
		int size = bytes[DATA_SIZE_IDX];
		size = (size << 8) + bytes[DATA_SIZE_IDX+1];
		size = (size << 8) + bytes[DATA_SIZE_IDX+2];
		size = (size << 8) + bytes[DATA_SIZE_IDX+3];
		size = (size << 4) | (bytes[DATA_SIZE_IDX+4] & LAST_BITS);
		return size;
	}
	
	public byte[] getAllData() {
		byte[] data = new byte[getSize()];
		
		for(int i=0; i<getSize(); i++) {
			data[i] = bytes[i+DATA_START_IDX];
		}
		
		return data;
	}
	
	public DatagramPacket makeDatagramPacket() {
		return new DatagramPacket(bytes, bytes.length);
	}
}
