package com.socket.L2;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerJ {
	public static void main(String[] args) throws IOException {
		
		ServerSocket server = new ServerSocket(5678);
		ExecutorService pool = Executors.newFixedThreadPool(3);
		
		while(true){
			Socket client = server.accept();			
			ServerThread thread = new ServerThread(client);
			pool.execute(thread);
		}
	}
}
