package com.socket.L2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{

	private Socket sk;

	public ServerThread(Socket sk) {
		this.sk = sk;
	}

	public void run() {
		if (sk != null) {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(
						sk.getInputStream()));
				PrintWriter out = new PrintWriter(sk.getOutputStream());				
				while (true) {
					String line = in.readLine();// 阻塞状态，等待客服端传过来内容
					if (line.equals("bye")) {// 如果接收到bye就结束线程
						break;
					}

					System.out.println(Thread.currentThread().getName() + ":"
							+ line);// 控制台输出接收到的内容

					out.println("[has receive]");// 告知客户端已经接收到
					out.flush();
				}
				out.close();
				in.close();				
				sk.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
