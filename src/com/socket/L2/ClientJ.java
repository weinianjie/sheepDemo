package com.socket.L2;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientJ {
	public static void main(String[] args) throws Exception {
		
		Socket server = new Socket(InetAddress.getLocalHost(), 5678);
		BufferedReader in = new BufferedReader(new InputStreamReader(server.getInputStream()));
		PrintWriter out = new PrintWriter(server.getOutputStream());
		BufferedReader wt = new BufferedReader(new InputStreamReader(System.in));
		
		while (true) {						
			String str = wt.readLine();
			out.println(str);
			out.flush();
			if(str.equals("bye"))
				break;			
			System.out.println(in.readLine());
		}
		out.close();
		in.close();
		server.close();				
	}
}
