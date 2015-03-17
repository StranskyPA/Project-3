package Networking;

import java.net.*;
import java.io.*;

import packet.Depacketizer;

public class DatagramSocketEchoerThread {
	private DatagramSocket socket;
	private DatagramPacket packet;
	private Depacketizer depacketer;
	private final static int PACKETSIZE = 512;
	
	public DatagramSocketEchoerThread(DatagramSocket newSocket) {
		socket = newSocket;
		packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);;
		depacketer = new Depacketizer();
	}
	
	public void run() {
		try {
			getNewPacket();
			System.out.println("Accepted Packet From: " + socket.getInetAddress());
			depacketer.depacketize(packet);
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
	
	public void getNewPacket() {
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
