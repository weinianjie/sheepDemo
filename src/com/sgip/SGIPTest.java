package com.sgip;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sgip.def.SGIPMsg;
import com.sgip.def.base.Constant;
import com.sgip.def.command.Bind;
import com.sgip.def.command.BindResp;
import com.sgip.def.command.SGIPCommand;
import com.sgip.def.command.Submit;
import com.sgip.def.command.SubmitResp;
import com.sgip.def.command.UnBind;
import com.sgip.def.command.UnBindResp;

/**
 * 联通SGIP1.2客户端测试
 * @author weinianjie
 * @date   2016年4月1日
 */
public class SGIPTest
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
			
			SGIPMsg msg = null;
			SGIPCommand cmd = null;
			
			// SGIP1.2协议SP（服务提供商）给SMG（短信网关）部分，第一步SP给SMG发送bind消息
			System.out.println("send bind start");
			cmd = new Bind();
			msg = new SGIPMsg(cmd);
			os.write(msg.getByteData());
			os.flush();
			System.out.println("send bind over");
			
			// 第二步，SP接收SMG响应的bind_resp消息，判断是否鉴权成功
			System.out.println("recevie bind_resp start");
			byte[] bindRespBytes = getAvailableBytes(is);
			msg = new SGIPMsg(bindRespBytes);
			BindResp bindResp = (BindResp)msg.getBody().getCommand();
			System.out.println("bindResp cmd.result=" + bindResp.getResult());
			System.out.println("recevie bind_resp over");
			
			// 如果bind通过
			if(bindResp.getResult() == 0)
			{
				// 第三步，SP给SMG发送submit消息，也就是真正的短信消息，连接未断开的情况下，本步骤和第四步组成的通讯可以重复
				System.out.println("send submit start");
				String content = "【轻码云】SGIP测试短信";
				List<String> phones = new ArrayList<String>();
				phones.add("8618665955410");
				cmd = new Submit(phones, content);
				msg = new SGIPMsg(cmd);
				os.write(msg.getByteData());
				os.flush();
				System.out.println("send submit start");
				
				// 第四部，SP接收SMG响应的submit_resp消息，判断提交是否成功
				System.out.println("recevie submit_resp start");
				byte[] submitRespBytes = getAvailableBytes(is);
				msg = new SGIPMsg(submitRespBytes);
				SubmitResp submitResp = (SubmitResp)msg.getBody().getCommand();
				System.out.println("submitResp cmd.result=" + submitResp.getResult());
				System.out.println("recevie submit_resp over");
				
				// 第五步，SP给SMG发送unbind消息，提醒SMG连接即将结束，如果不执行本步骤直接断开也行，怕SMG有意见把你拉黑了，最好严格遵循协议
				System.out.println("send unbind start");
				cmd = new UnBind();
				msg = new SGIPMsg(cmd);
				os.write(msg.getByteData());
				os.flush();
				System.out.println("send unbind over");
	
				// 第六步，SP接收SMG响应的unbind_resp消息，表明SMG已知晓（本消息无任何内容，可以忽略）
				System.out.println("recevie unbind_resp start");
				byte[] unbindRespBytes = getAvailableBytes(is);
				msg = new SGIPMsg(unbindRespBytes);
				UnBindResp unbindResp = (UnBindResp)msg.getBody().getCommand();
				System.out.println("unbindResp unbindResp is null?" + (unbindResp == null));
				System.out.println("recevie unbind_resp over");
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
