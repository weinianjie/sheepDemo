package com.reach;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ExtendInterface {
	
	public final static String Msg_UserMgr 					= "10101";		// 用户管理---
	public final static String Msg_GetUserList 				= "10102";		// 用户列表---
	public final static String Msg_CourseMgr 				= "10103";		// 课表管理---
	public final static String Msg_GetCourseList 			= "10104";		// 课表列表---
	public final static String Msg_GetCourseDetail 			= "10105";		// 课表详细---
	public final static String Msg_CourseTimeSet 			= "10106";		// 节次设置---
	public final static String Msg_Deletefiles 				= "10107";		// 删除课件---
	public final static String Msg_GetFileList 				= "10108";		// 课件列表---
	public final static String Msg_GetFileDetail 			= "10109";		// 课件详细---
	public final static String Msg_GetRoomLive 				= "10110";		// 教室直播---
	public final static String Msg_GetCourseLive 			= "10111";		// 课表直播---
	public final static String Msg_RoomMgr 					= "10112";		// 教室管理---
	public final static String Msg_GetRoomList 				= "10113";		// 教室列表---
	public final static String Msg_GetRoomDetail 			= "10114";		// 教室详细---
	
    public static void main(String[] args) {
    	
    	String fn = "";    	
    	
    	fn = "userMgr";
    	fn = "getUserList";
    	fn = "getFileList";
//    	fn = "getFileDetail";
//    	fn = "roomMgr";
//    	fn = "getRoomList";
//    	fn = "courseTimeSet";
//    	fn = "getCourseLive";
//    	fn = "getRoomLive";
//    	fn = "courseMgr";
//    	fn = "getCourseList";
//    	fn = "getCourseDetail";
    	
    	try {

			load(fn);	
	    	ExtendInterface sender = new ExtendInterface();
//			String s = sender.sendHttpXML(msg, "http://127.0.0.1/XmlRpcService.action");
			String s = sender.sendHttpXML(msg, "http://192.168.13.200/XmlRpcService.action");
			write(fn, s);
			System.out.println(s);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	
    public static String msg = "";
    
    public static void load(String xmlFile) throws IOException{
    	msg = "";
    	BufferedReader br = null;
    	try{
    		br=new BufferedReader(new InputStreamReader(ExtendInterface.class.getResourceAsStream("/xml/" + xmlFile + ".xml"),"UTF-8"));
	        for(String line = br.readLine();line != null;line = br.readLine()){
	        	msg += line.trim();
	        }
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		br.close();
    	}
    }
    
    public static void write(String xmlFile, String s) throws IOException{
    	File f = new File("E:\\workspace\\study\\xml\\" + xmlFile + "_out.xml");
    	if(f.exists()){
    		f.delete();
    	}
    	FileOutputStream out = null;
    	try{
    		f.createNewFile();
	    	out = new FileOutputStream(f);
	    	out.write(new String(s).getBytes("UTF-8"));
	    	out.flush();
    	}catch(Exception e) {
    		e.printStackTrace();
    	}finally{
    		out.close();
    	}
    }

	public String sendHttpXML(String xmlmsg, String ServerUrl) {
		
		try {
			//ServerUrl ="http://192.168.14.200/HttpService.Action";
			URL url = new URL(ServerUrl);
			HttpURLConnection urlcon = (HttpURLConnection) url.openConnection();
			urlcon.setReadTimeout(5000);
			urlcon.setDoOutput(true);
			urlcon.setDoInput(true);
			urlcon.setRequestMethod("POST");
			urlcon.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
			urlcon.setRequestProperty("Cookie",  "JSESSIONID=AFFE6AB17E730FDD7FFF01FBE1445331");
			OutputStream out = urlcon.getOutputStream();
			//发送消息
			urlcon.connect();
//			Log.info("==================sendHttpXML==================");
//			Log.info(xmlmsg);
//			Log.info("==================sendHttpXML==================");
		    out.write(xmlmsg.getBytes("UTF-8"));
			out.flush();
			
//			System.out.println("---getResponseMessage:"+urlcon.getResponseMessage());
			//int code = urlcon.getResponseCode();
			//System.out.println(code + "   " + urlcon.getResponseMessage());
			
			//接收返回消息
//			int length = urlcon.getContentLength();				
//			System.out.println("length:"+length);
//			InputStream in = urlcon.getInputStream();			
//			byte[] b = this.readContent(in, length);
//			
//			in.close();
//			out.close();
//			
//			String ret = new String(b,"utf-8");
//			System.out.println("==================return xml==================");
//			System.out.println(ret);
//			System.out.println("==============================================");
			BufferedReader rs = new BufferedReader(new InputStreamReader(urlcon.getInputStream(),"UTF-8"));
			String line = rs.readLine();
			StringBuffer str = new StringBuffer();
			while(null!=line)
			{
				str.append(line);
				line = rs.readLine();
			}
			
			Map<String, List<String>> map = urlcon.getHeaderFields();
			Iterator<String> ite = map.keySet().iterator();
			while (ite.hasNext()) {
				String key = ite.next();
				List<String> list = map.get(key);
				System.out.print("key : " + key + " ---> value : ");
				for (String info : list) {
					System.out.print(info + "\t");
				}
				System.out.println();
			}
			
			System.out.println(urlcon.getHeaderField("Set-Cookie"));
//			Log.info("==================return from xml==================");
//			Log.info(str.toString());
//			Log.info("==================return from xml==================");
			
			//in.close();
			rs.close();
			out.close();
			return  str.toString();

		} catch (IOException e) {
//			Log.infoStackTrace(e);
			e.printStackTrace();
			return null;
		} catch (Exception e) {
//			Log.infoStackTrace(e);
			e.printStackTrace();
			return null;
		} finally{
			
		}
	}
		
	@SuppressWarnings("unused")
	private byte[] readContent(final InputStream in, int length) throws IOException {
		byte dataBytes[] = new byte[length];		
		int bytesRead = 0;
		int n = 0;
		int leftbytes = length;
		while (leftbytes > 0
				&& (n = in.read(dataBytes, bytesRead, leftbytes)) != -1) {
			leftbytes = length - bytesRead;
			bytesRead = bytesRead + n;
		}
		return dataBytes;
	}
}
