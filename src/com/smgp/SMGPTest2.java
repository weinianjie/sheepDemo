package com.smgp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.smgp.def.SMGPMsg;
import com.smgp.def.base.Constant;
import com.smgp.def.command.Exit;
import com.smgp.def.command.ExitResp;
import com.smgp.def.command.Login;
import com.smgp.def.command.LoginResp;
import com.smgp.def.command.SMGPCommand;
import com.smgp.def.command.Submit;
import com.smgp.def.command.SubmitResp;

/**
 * 电信SMGP3.0客户端测试
 * @author weinianjie
 * @date   2016年4月1日
 */
public class SMGPTest2
{
	public static void main(String[] args)
	{
		Socket socket = null;
		InputStream is = null;
		OutputStream os = null;
		try
		{
			socket = new Socket(Constant.SMG_IP, Constant.SMG_PORT);
			System.out.println("builded socket=" + socket);
			socket.setKeepAlive(true);
			os = socket.getOutputStream();
			is = socket.getInputStream();
			
			SMGPMsg msg = null;
			SMGPCommand cmd = null;
			
			// SMGP3.0协议SP（服务提供商）给SMG（短信网关）部分，第一步SP给SMG发送login消息
			System.out.println("send login start");
			cmd = new Login();
			msg = new SMGPMsg(cmd);
			System.out.println(msg.getHead().getSequenceId());
			os.write(msg.getByteData());
			os.flush();
			System.out.println("send login over");
			
			// 第二步，SP接收SMG响应的login_resp消息，判断是否鉴权成功
			System.out.println("recevie login_resp start");
			byte[] loginRespBytes = getAvailableBytes(is);
			msg = new SMGPMsg(loginRespBytes);
			System.out.println(msg.getHead().getSequenceId());
			LoginResp loginResp = (LoginResp)msg.getBody().getCommand();
			System.out.println("loginResp cmd.status=" + loginResp.getStatus());
			System.out.println("recevie login_resp over");
			
			// 如果bind通过
			if(loginResp.getStatus() == 0)
			{
				// 第三步，SP给SMG发送submit消息，也就是真正的短信消息，连接未断开的情况下，本步骤和第四步组成的通讯可以重复
				System.out.println("send submit start");
				String content = "【轻码云】SMGP测试短信";
				List<String> phones = new ArrayList<String>();
				phones.add("18923455561");
//				phones.add("15338871144");
				cmd = new Submit(phones, content);
				msg = new SMGPMsg(cmd);
//				System.out.println(msg.getHead().getSequenceId());
				
				byte[] bs = msg.getByteData();
				for(byte b:bs)
				{
					System.out.print(b + ",");
				}
				System.out.println();
				os.write(bs);
//				os.write(msg.getByteData());
				os.flush();
				System.out.println("send submit start");
				
				// 第四部，SP接收SMG响应的submit_resp消息，判断提交是否成功
				System.out.println("recevie submit_resp start");
				byte[] submitRespBytes = getAvailableBytes(is);
				
				for(byte b:submitRespBytes)
				{
					System.out.print(b + ",");
				}
				System.out.println();
				
				msg = new SMGPMsg(submitRespBytes);
				SubmitResp submitResp = (SubmitResp)msg.getBody().getCommand();
//				System.out.println(msg.getHead().getSequenceId());
				System.out.println("submitResp cmd.msgId=" + submitResp.getMsgId() + ",cmd.status=" + submitResp.getStatus());
				System.out.println("recevie submit_resp over");

				Thread.sleep(30000);
				
//				// 第五步，SP给SMG发送exit消息，提醒SMG连接即将结束，如果不执行本步骤直接断开也行，怕SMG有意见把你拉黑了，最好严格遵循协议
//				System.out.println("send exit start");
//				cmd = new Exit();
//				msg = new SMGPMsg(cmd);
//				os.write(msg.getByteData());
//				os.flush();
//				System.out.println("send exit over");
//	
//				// 第六步，SP接收SMG响应的exit_resp消息，表明SMG已知晓（本消息无任何内容，可以忽略）
//				System.out.println("recevie exit_resp start");
//				byte[] exitRespBytes = getAvailableBytes(is);
//				msg = new SMGPMsg(exitRespBytes);
//				ExitResp exitResp = (ExitResp)msg.getBody().getCommand();
//				System.out.println("exitResp exitResp is null?" + (exitResp == null));
//				System.out.println("recevie exit_resp over");
			}
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			// 第七步，连接断开
			if(os != null)
			{
				try
				{
					os.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if(is != null)
			{
				try
				{
					is.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if(null != socket)
			{
				System.out.println("release socket=" + Constant.SMG_IP + ":" + Constant.SMG_PORT);
				try
				{
					socket.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	public static byte[] getAvailableBytes(InputStream inputStream) throws Exception
	{
		Calendar cal = Calendar.getInstance();
		long startMillis = cal.getTimeInMillis();
		long endMillis = startMillis;
		int timeout = Constant.RESPONSE_TIMEOUT;
		
		int count = 0;
		while (count == 0 && (endMillis - startMillis) <= timeout*1000)
		{
			endMillis = Calendar.getInstance().getTimeInMillis();
			count = inputStream.available();
		}
		if(count == 0 && (endMillis - startMillis) > timeout*1000)
		{
			throw new Exception("read server response time out !");
		}
		
		System.out.println("recevie bytes length=" + count);
		
		byte[] bytes = new byte[count];
		int readCount = 0; // 已经成功读取的字节的个数
		while (readCount < count)
		{
			readCount += inputStream.read(bytes, readCount, count - readCount);
		}
		return bytes;
	}
}
