package com.socket2;

import java.net.Socket;

public class OClient {
	public static void main(String[] args) throws Exception
	{
		Socket sk = new Socket("127.0.0.1", 8888);
		int times = 0;
		while(true)
		{
			if(isServerClose(sk))
			{
				System.out.println("服务器已经断线，即将重连");
				if(!sk.isClosed())
				{
					sk.close();
				}
				sk = new Socket("127.0.0.1", 8888);
				times = 0;
			}else
			{
				System.out.println("连接正常" + times++);
			}
			Thread.sleep(1000);
		}
	}
	
	public static Boolean isServerClose(Socket socket){  
		   try{  
		    socket.sendUrgentData(0xff);//发送1个字节的紧急数据，默认情况下，服务器端没有开启紧急数据处理，不影响正常通信  
		    return false;  
		   }catch(Exception se){ 
			   se.printStackTrace();
		    return true;  
		   }  
		}
}	
