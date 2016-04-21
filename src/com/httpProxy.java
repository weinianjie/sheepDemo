package com;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class httpProxy
{
	static int port = 1027;
	
	public static void main(String[] args) {
		
		// 指定其他端口
		if(args.length > 0) {
			try {
				port = Integer.parseInt(args[0]);
			}catch(Exception e) {
				e.printStackTrace();
				return;
			}
		}
		
		// 开始启动监听线程
		new Thread(){
			@SuppressWarnings("deprecation")
			public void run() {
				System.out.println("server start in port " + port);
				
				ServerSocket server = null;
				try {
					server = new ServerSocket(port);
					int iMark = 0;
					while (true) {
						Socket conn = server.accept();
						iMark++;
						System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + "begin(" + iMark + "):" + System.currentTimeMillis() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
						try {
							Writer out = new OutputStreamWriter(conn.getOutputStream());
							DataInputStream in = new DataInputStream(conn.getInputStream());
							StringBuilder sb = new StringBuilder();

							// 开始读取
							// header
							String line = in.readLine();
							int contentLength = 0;
							while( (line = in.readLine()) != null) {
								if(line.startsWith("Content-Length")) {
									try {
										contentLength = Integer.parseInt(line.substring(line.indexOf(':') + 1).trim());
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
								if(line.equals("")) break;// 读到空行，代表header结束
								sb.append(line).append("\r\n");
							}
							String reqHeader = sb.toString();
							System.out.println(reqHeader);
							
							// body
							int bytesRead = 0;
							int n = 0;
							int leftBytes = contentLength;
							byte[] in_b = new byte[contentLength];
							while (leftBytes > 0 && (n = in.read(in_b, bytesRead, leftBytes)) != -1) {
								bytesRead = bytesRead + n;
								leftBytes = contentLength - bytesRead;
							}
							String reqBody = new String(in_b, "UTF-8");
							System.out.println(reqBody);
							
							System.out.println("=================================" + "middle(" + iMark + "):" + System.currentTimeMillis() + "=================================");
							
							String respBody = "{\"respCode\": \"00000\"}";
							sb.setLength(0);
							sb.append("HTTP/1.0 200 OK\r\n");
							sb.append("Date: " + new Date() + "\r\n");
							sb.append("Server: smsServer 1.0\r\n");
							sb.append("Content-type: application/json;charset=utf-8;\r\n");
							sb.append("Content-length: " + respBody.getBytes().length + "\r\n\r\n");
							sb.append(respBody);
							String response = sb.toString();
							System.out.println("server response body:" + respBody);

							out.write(response);
							out.flush();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (conn != null)
								try {
									conn.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
						}
						System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<" + "end(" + iMark + "):" + System.currentTimeMillis() + "<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if(server != null && !server.isClosed()) {
							server.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
					
				}
				System.out.println("server stop in port " + port);
			}
		}.start();
	}
}
