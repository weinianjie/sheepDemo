package com.HTTPServer.L2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Redirector implements Runnable {

	private int port;
	private String siteUrl;

	public Redirector(int port, String siteUrl) {
		this.port = port;
		this.siteUrl = siteUrl;
	}

	public void run() {
		try {
			ServerSocket server = new ServerSocket(port);
			while (true) {
				Socket conn = server.accept();
				Thread t = new RedirectThread(conn);
				t.start();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private class RedirectThread extends Thread {

		private Socket conn;

		public RedirectThread(Socket conn) {
			this.conn = conn;
		}

		public void run() {
			try {
				Writer out = new OutputStreamWriter(conn.getOutputStream());
				Reader in = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				StringBuilder sb = new StringBuilder();
				int temp;
				while (true) {
					temp = in.read();
					if (temp == '\r' || temp == '\n' || temp == -1)
						break;
					sb.append((char) temp);
				}
				String request = sb.toString();
				int firstSpace = request.indexOf(' ');
				int secondSpace = request.indexOf(' ', firstSpace + 1);
				String theFile = request.substring(firstSpace + 1, secondSpace);
				if (request.indexOf("HTTP") != -1) {
					// 这是一个HTTP响应码，告知客户端要被重定向
					out.write("HTTP/1.0 302 FOUND\r\n");
					// 服务器当前时间
					out.write("Date: " + new Date() + "\r\n");
					// 服务器的名称和版本【可选的】
					out.write("Server: Redirector 1.0\r\n");
					// 告知要重定向的位置，浏览器会自动跳转
					out.write("Location: " + siteUrl + theFile + "\r\n");
					// 指示客户端看到的是HTML，发送一个空行表明结束
					out.write("Content-type: text/html\r\n\r\n");
					out.flush();
				}
				// 有些老浏览器，不支持redirection，我们需要生成HTML说明
				out.write("<HTML><HEAD><TITLE>Document moved</TITLE></HEAD>\r\n");
				out.write("<BODY><H1>Document moved</H1>\r\n");
				out.write("The document " + theFile
						+ " has moved to\r\n<A HREF=\"" + siteUrl + theFile
						+ "\">" + siteUrl + theFile
						+ "</A>.\r\n Please update your bookmarks<P>");
				out.write("</BODY></HTML>\r\n");
				out.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
			}
		}
	}

	public static void main(String[] args) {
		int port = 8891;
		String siteUrl = "http://www.sina.com.cn";
		Thread server = new Thread(new Redirector(port, siteUrl));
		server.start();
	}
}
