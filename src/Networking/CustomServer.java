package Networking;

import java.net.*;
import java.io.*;

public class CustomServer {
	private DatagramSocket socket;
	//private DatagramPacket packet;
	private ServerSocket textSocket;
	//private final static int PACKETSIZE = 512;
	
	public CustomServer(int port) throws IOException {
		textSocket = new ServerSocket(port);
		socket = new DatagramSocket(port);
		//packet = new DatagramPacket(new byte[PACKETSIZE], PACKETSIZE);
		System.out.println("Server IP address: " + socket.getLocalSocketAddress());
	}
	
	public void listen() throws IOException {
		for (;;) {
			System.out.println("Attempting to Start Server");
			
			Socket s = textSocket.accept();
			SocketEchoerThread textEchoer = new SocketEchoerThread(s);
			System.out.println("Accepted Socket Connection From: " + s.getInetAddress());
			textEchoer.run();
			
			DatagramSocketEchoerThread packetEchoer = new DatagramSocketEchoerThread(socket);
			System.out.println("Accepted DatagramSocket Connection From: " + socket.getInetAddress());
			packetEchoer.run();
		}
	}
	
	public static void main(String[] args) throws IOException {
		CustomServer s = new CustomServer(8080);
		s.listen();
	}
}
