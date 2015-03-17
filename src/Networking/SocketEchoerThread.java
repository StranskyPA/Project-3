package Networking;

import java.net.*;
import java.io.*;

public class SocketEchoerThread {
	private Socket textSocket;
	
	public SocketEchoerThread(Socket socket) {
		this.textSocket = socket;
	}
	
	public void run() {
		try {
            BufferedReader responses =  new BufferedReader (new InputStreamReader(textSocket.getInputStream()));
            PrintWriter writer = new PrintWriter(textSocket.getOutputStream());
            writer.println("Connection open.");
            writer.println("I will echo a single message, then close.");

            StringBuilder sb = new StringBuilder();
            while (!responses.ready()){}
            while (responses.ready()) {
                sb.append(responses.readLine() + '\n');
            }
            System.out.println("From: " + textSocket.getInetAddress() + ": " + sb);
        
            writer.print(sb);
            writer.flush();
            textSocket.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } 
	}
}
