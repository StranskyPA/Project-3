package Networking;

import java.net.*;
import java.io.*;

public class DatagramSocketThread {
	private DatagramSocket socket;
	private DatagramPacket packet;
	
	public DatagramSocketThread(DatagramSocket newSocket, DatagramPacket newPacket) {
		socket = newSocket;
		packet = newPacket;
	}
	
	public void run() {
		try {
			System.out.println("Accepted Packet From: " + socket.getInetAddress());
			System.out.println("Printing Packet: " + new String(packet.getData()));
			socket.send(packet);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}	
}
