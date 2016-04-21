package com.socket.L3;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientK {
	static Socket server;
	static String strA;
	static String strB;
	static String nick = "jerry";
	static BufferedReader in;
	static PrintWriter out;
	static BufferedReader wt;
	public static void main(String[] args) throws Exception {
		server = new Socket(InetAddress.getLocalHost(), 5678);
		System.out.println("msg:connect server success");
		in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		out = new PrintWriter(server.getOutputStream());
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
					server.close();
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
					server.close();
				}catch(IOException e){
					e.printStackTrace();
				}				
			}
		}.start();
	}
}
