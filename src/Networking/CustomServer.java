package Networking;

import java.net.*;
import java.io.*;

public class CustomServer {
	private DatagramSocket socket;
	private DatagramPacket packet;
	private final static int PACKETSIZE = 512;
	
	public CustomServer(int port) throws IOException {
		socket = new DatagramSocket(port);
		packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
		System.out.println("Server IP address: " + socket.getLocalSocketAddress());
	}
	
	public void listen() throws IOException {
		for (;;) {
			System.out.println("Attempting to Start Server");
			socket.receive(packet);
			DatagramSocketThread echoer = new DatagramSocketThread(socket, packet);
			System.out.println("Accepted Connection From: " + socket.getInetAddress());
			echoer.run();
		}
	}
	
	public static void main(String[] args) throws IOException {
		CustomServer s = new CustomServer(8888);
		s.listen();
	}
}
