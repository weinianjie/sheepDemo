package com.socket.L3;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerK {
	static String strA;
	static String strB;
	static String nick = "tom";
	static ServerSocket server;
	static Socket client;
	static BufferedReader in;
	static PrintWriter out;
	static BufferedReader wt;
	public static void main(String[] args) throws IOException {
		
		server = new ServerSocket(5678);
		client = server.accept();
		System.out.println("msg:client connect");
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream());
		wt = new BufferedReader(new InputStreamReader(System.in));

		new Thread(){
			public void run(){
				try{
					while (true) {						
						strA = in.readLine();
						System.out.println(strA);
						if (strA.equals("end"))
							break;
					}
					client.close();
				}catch(IOException e){
					e.printStackTrace();
				}				
			}
		}.start();
		
		new Thread(){
			public void run(){
				try{
					while (true) {						
						strB = wt.readLine();
						System.out.println(nick + ":" + strB);
						out.println(nick + ":" + strB);
						out.flush();
						if (strB.equals("end"))
							break;
					}
					client.close();
				}catch(IOException e){
					e.printStackTrace();
				}				
			}
		}.start();		
	}
}
