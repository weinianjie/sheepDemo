package com.tmp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;


public class MainCenter2 {
	public static void main(String[] args){
		Socket sk = new Socket();
		SocketAddress addr =  new InetSocketAddress("127.0.0.1", 998);
		OutputStream out = null;
		BufferedReader br = null;
		String str = "";
		try {			
			sk.connect(addr, 1000);
			out = sk.getOutputStream();
			br = new BufferedReader(new InputStreamReader(System.in));
			while(!str.equals("bye")) {
				str = br.readLine();
				System.out.println(str);
				out.write(str.getBytes("GBK"));
				out.flush();
			}
			
		} catch (IOException e) {
			try {
				br.close();
				out.close();
				sk.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
	}
}
