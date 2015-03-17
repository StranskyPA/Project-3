package packet;

public class Packet {
	private byte[] bytes;

	public Packet(byte id, int part, int totalNumParts, char type, char[] cbuf) {
		bytes = new byte[cbuf.length + 10];
		bytes[0] = id;
		
		bytes[1] = (byte)(part >> 24);
		bytes[2] = (byte)(part >> 16);
		bytes[3] = (byte)(part >> 8);
		bytes[4] = (byte)(part);
		
		bytes[5] = (byte)(totalNumParts >> 24);
		bytes[6] = (byte)(totalNumParts >> 16);
		bytes[7] = (byte)(totalNumParts >> 8);
		bytes[8] = (byte)(totalNumParts);
		
		bytes[9] = (byte)(type);
		
		for(int i=10; i < bytes.length; i++) {
			bytes[i] = (byte)(cbuf[i-10]);
		}
	}
	
	public byte get(int index) {
		return bytes[index];
	}
	
	public int length() {
		return bytes.length;
	}
}
