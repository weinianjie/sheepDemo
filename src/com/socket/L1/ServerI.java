package com.socket.L1;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerI {
	static String strA;
	static String strB;
	static String nick = "tom";
	static ServerSocket server;
	static Socket client;
	static BufferedReader in;
	static PrintWriter out;
	static BufferedReader wt;
	static Thread t1,t2;
	public static void main(String[] args) throws IOException {
		
		server = new ServerSocket(5678);
		client = server.accept();
		System.out.println("msg:client connect");
		in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		out = new PrintWriter(client.getOutputStream());
		wt = new BufferedReader(new InputStreamReader(System.in));

		t1 = new Thread(){
			public void run(){
				try{
					while (true) {						
						strA = in.readLine();
						System.out.println(strA);
						if (strA.equals("end"))
							break;
					}
					wt.close();
					out.close();
					in.close();					
					client.close();
				}catch(IOException e){
					e.printStackTrace();
				}				
			}
		};
		t1.start();
		
		t2 = new Thread(){
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
					wt.close();
					out.close();
					in.close();					
					client.close();
				}catch(IOException e){
					e.printStackTrace();
				}				
			}
		};
		t2.start();
	}
}
