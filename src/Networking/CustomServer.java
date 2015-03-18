package Networking;

import java.net.*;
import java.io.*;

import application.Main;

public class CustomServer extends Thread{
	private DatagramSocket packetSocket;
	private ServerSocket textSocket;
	private Main main;
	private int port;
	
	public CustomServer(int port, Main main) throws IOException {
		this.main = main;
		this.port = port;
		textSocket = new ServerSocket(port);
	}
	
	public void listen() throws IOException {
		for (;;) {
			System.out.println("Attempting to Start Server");
			System.out.println("Server IP address: " + textSocket.getLocalPort());
			
			Socket s = textSocket.accept();
			SocketEchoerThread textEchoer = new SocketEchoerThread(s, main);
			
			main.sendSocket(textEchoer);
			System.out.println("Accepted Socket Connection From: " + s.getInetAddress());
			textEchoer.start();
			main.sendSocket(textEchoer);
			
			DatagramSocketEchoerThread packetEchoer = new DatagramSocketEchoerThread(packetSocket);
			System.out.println("Accepted DatagramSocket Connection From: " + packetSocket.getInetAddress());
			packetEchoer.run();
		}
	}
	
	public void run() {
		try {
			textSocket = new ServerSocket();
			textSocket.bind(new InetSocketAddress(port));
			listen();
		}catch (IOException e) {
			System.out.println("Something happened");
			e.printStackTrace();
		}
		try {
			System.out.println("refused");
			textSocket.setReuseAddress(true);
		} catch (SocketException e) {
			System.out.println("Socket exception");
			e.printStackTrace();
		}
	}
}
