package com.socket2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class OServer {
	
	public static void main(String[] args) throws Exception
	{
		ServerSocket server = new ServerSocket(8888);
		try {
			while(true)
			{
				Socket sk = server.accept();
				new Thread(new subThread(sk),"sheep-server").start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			if(!server.isClosed())
			{
				server.close();
			}
		}

	}
}
class subThread implements Runnable
{
	Socket sk;
	public subThread(Socket sk)
	{
		this.sk = sk;
	}
	@Override
	public void run() {
		try
		{
			int times = 0;
			while(times < 10)
			{
				System.out.println("times=" + times++ + ",socket=" + sk);
				Thread.sleep(5000);
			}
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			if(!sk.isClosed())
			{
				try {
					sk.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
