package Networking;

import java.net.*;
import java.io.*;

import application.Main;

public class SocketEchoerThread extends Thread{
	private Socket textSocket;
	StringBuilder sb = new StringBuilder();
	private boolean going;
	
	public SocketEchoerThread(Socket socket, Main main) {
		System.out.println("test1");
		this.textSocket = socket;
		going = true;
	}
	
	public void run() {
		try {
			System.out.println("running");
            BufferedReader responses =  new BufferedReader (new InputStreamReader(textSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(textSocket.getOutputStream());
            writer.println("Connection open.");
            writer.println("I will echo a single message, then close.");

            //StringBuilder sb = new StringBuilder();
            while (!responses.ready()){}
            while (responses.ready()) {
                sb.append(responses.readLine() + '\n');
            }
            System.out.println("From: " + textSocket.getInetAddress() + ": " + sb);
            getString();
        
            writer.print(sb);
            writer.flush();
            textSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 
	}
	
	public synchronized void halt() {
		going = false;
	}
	
	public String getString() {
		return sb.toString();
	}
}
