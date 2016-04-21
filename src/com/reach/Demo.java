package com.reach;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Demo {	
	//获取用户列表（10102接口）实例
    public static void main(String[] args) { 
    	
    	try {
    		//准备发送的xml字符串
    		StringBuilder sbo = new StringBuilder();
    		sbo.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sbo.append("<RequestMsg>");
				sbo.append("<MsgHead>");
					sbo.append("<MsgCode>10102</MsgCode>");
				sbo.append("</MsgHead>");
				sbo.append("<MsgBody>");
					sbo.append("<PageNum>2</PageNum>");
					sbo.append("<PageSize>3</PageSize>");
				sbo.append("</MsgBody>");
			sbo.append("</RequestMsg>");
				
			//往媒体中心发送xml数据
			URL url = new URL("http://192.168.13.200/XmlRpcService.action");
			HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
			urlcon.setReadTimeout(5000);
			urlcon.setDoOutput(true);
			urlcon.setDoInput(true);
			urlcon.setRequestMethod("POST");
			urlcon.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
//			urlcon.setRequestProperty("Cookie",  "JSESSIONID=AFFE6AB17E730FDD7FFF01FBE1445331");
			OutputStream out = urlcon.getOutputStream();
			urlcon.connect();
		    out.write(sbo.toString().getBytes("UTF-8"));
			out.flush();
			
			//接收返回
			StringBuffer sbi = new StringBuffer();
			BufferedReader rs = new BufferedReader(new InputStreamReader(urlcon.getInputStream(),"UTF-8"));
			String line = rs.readLine();
			while(null!=line){
				sbi.append(line);
				line = rs.readLine();
			}
			rs.close();
			out.close();
			
			//打印获取的xml
			System.out.println(sbi.toString());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
