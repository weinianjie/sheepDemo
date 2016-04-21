package com;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MainCenter3
{
	public static void main(String[] args)
	{
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(now);

		calendar.add(Calendar.MINUTE, -3);

		Date lastTime =  calendar.getTime();	
		
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			System.out.println("lastTime=" + sdf.format(lastTime));
			
			String url = "http://121.15.167.240:48080/coremailNotifyThree/qmySmsStatusQuery";
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd HH:mm");
			String RequestTime = sdf2.format(lastTime).replace(" ", "%20"); // 替换空格
			url += "?RequestTime=" + RequestTime + "&SpNumber=1065813911111";
			
			System.out.println("url=" + url);
			String result = get(url);
			System.out.println("result=" + result);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private static String get(String url) throws Exception
	{
		String result = "";
		BufferedReader in = null;
		URL realUrl = new URL(url);
		URLConnection conn = realUrl.openConnection();
		conn.setRequestProperty("accept", "*/*");
		conn.setConnectTimeout(5000);
		conn.setReadTimeout(20000);
		in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF8"));
		String line = "";
		while ((line = in.readLine()) != null)
		{
			result += line;
			result += "\n";
		}
		// 删除最后多余的换行符
		if (!result.equals(""))
			result = result.substring(0, result.lastIndexOf("\n"));
		return result;
	}
}
