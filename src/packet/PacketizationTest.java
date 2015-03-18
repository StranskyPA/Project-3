package packet;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import org.junit.Test;
import application.Controller;

public class PacketizationTest {

	@Test
	public void test() {
		Packetizer packer = new Packetizer(512);
		Depacketizer unpacker = new Depacketizer(new Controller());
		
		packer.packetize(new File("/export/home/s14/clementsjr/workspace/Project 3/src/packet/Test Files/TestImageFile.png"));
		
		ArrayList<Packet> packets = new ArrayList<Packet>();
		while(packer.hasNextPacket()) {
			packets.add(packer.getNextPacket());
		}
		
		System.out.println(packets.size());
		System.out.println(packets.get(0).getNumParts());
		for(int i=0; i<packets.size(); i++){
			System.out.println(new String(packets.get(i).getAllData()));
			unpacker.depacketize(packets.get(i));
		}
	}
}
