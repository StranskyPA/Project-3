package Networking;

import java.net.*;
import java.io.*;

import application.Controller;
import packet.Depacketizer;
import packet.Packet;

public class DatagramSocketEchoerThread {
	private DatagramSocket socket;
	private DatagramPacket packet;
	private Depacketizer depacketer;
	private Controller c;
	private final static int PACKETSIZE = 512;
	
	public DatagramSocketEchoerThread(DatagramSocket newSocket) {
		socket = newSocket;
		packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);;
		depacketer = new Depacketizer(c);
	}
	
	public void run() {
		getNewPacket();
		System.out.println("Accepted Packet From: " + socket.getInetAddress());
		Packet newPacket = new Packet(packet);
		depacketer.depacketize(newPacket);
	}
	
	public void getNewPacket() {
		try {
			socket.receive(packet);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
