package Networking;

import java.net.*;
import java.io.*;

import application.Main;

public class SocketEchoerThread extends Thread{
	private Socket textSocket;
	StringBuilder sb = new StringBuilder();
	private boolean going;
	
	public SocketEchoerThread(Socket socket) {
		this.textSocket = socket;
		going = true;
	}
	
	public void run() {
			System.out.println("DONT GO HERE");
	}
	
	public synchronized void halt() {
		going = false;
	}
	
	public String getString() {
		return sb.toString();
	}
}
