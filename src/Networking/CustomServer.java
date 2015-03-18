package Networking;

import java.net.*;
import java.io.*;

import application.Main;

public class CustomServer extends Thread{
	private DatagramSocket socket;
	private ServerSocket textSocket;
	private Main main;
	private String host = "Bilbo";
	private int port = 8888;
	
	public CustomServer(int port, Main main) throws IOException {
		this.main = main;
	}
	
	public void listen() throws IOException {
		for (;;) {
			try{
			Socket s = new Socket(host, port);
			System.out.println("Attempting to Start Server");
			System.out.println("Server IP address: " + textSocket.getLocalPort());
			System.out.println("Inet address: " + textSocket.getInetAddress());
			
			System.out.println("Got To Socket Echo part");
            BufferedReader responses =  new BufferedReader (new InputStreamReader(s.getInputStream()));
            PrintWriter writer = new PrintWriter(s.getOutputStream());
            writer.println("Connection open.");
            writer.println("I will echo a single message, then close.");
            System.out.println("Attempting to build a string");
            StringBuilder sb = new StringBuilder();
            if (!responses.ready()){
            	//System.out.println("Not Ready?");
            	responses.close();
            	//responses =  new BufferedReader (new InputStreamReader(s.getInputStream()));
            }
            else{
            	while (responses.ready()) {
                    sb.append(responses.readLine() + '\n');
                }
            }
            System.out.println("From: " + textSocket.getInetAddress() + ": " + sb);
        
            writer.print(sb.toString());
            writer.flush();
            s.close();
            System.out.println("Done");
			}
			catch (ConnectException e){
				listen();
			}
			
			/*DatagramSocketEchoerThread packetEchoer = new DatagramSocketEchoerThread(socket);
			System.out.println("Accepted DatagramSocket Connection From: " + socket.getInetAddress());
			packetEchoer.run();*/
		}
	}
	
	public void run() {
		try {
			textSocket = new ServerSocket();
			textSocket.bind(new InetSocketAddress(8888));
			System.out.println("Running");
			listen();
		}catch (IOException e) {
			System.out.println("Caught exception");
			e.printStackTrace();
		}
		try {
			textSocket.setReuseAddress(true);
			listen();
			System.out.println("Re-using?");
		} catch (IOException e) {
			System.out.println("Caught exception 2222");
			e.printStackTrace();
		}
	}
}
