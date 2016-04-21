package com.HTTPServer.L1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleFileHttpServer extends Thread {

	private byte[] content;
	private byte[] header;
	private int port;

	public SingleFileHttpServer(byte[] data, String encoding, String MIMEType)
			throws UnsupportedEncodingException {
		this.content = data;
		this.port = 8890;
		String header = "HTTP/1.0 200 OK\r\n" + "Server: OneFile 1.0 \r\n"
				+ "Content-length: " + this.content.length + "\r\n"
				+ "Content-type: " + MIMEType + "\r\n";
		this.header = header.getBytes(encoding);
	}

	public void run() {
		ServerSocket server;
		try {
			server = new ServerSocket(port);
			System.out.println("Accepting connections on port "
					+ server.getLocalPort());
			System.out.println("Data to be sent:");
			System.out.write(this.content);
			while (true) {
				Socket conn = null;
				try {
					conn = server.accept();
					OutputStream out = conn.getOutputStream();
					InputStream in = conn.getInputStream();
					int temp;
					StringBuilder request = new StringBuilder();
					while((temp = in.read()) != -1){
						request.append((char)temp);
					}
//					while (true) {
//						temp = in.read();
//						if (temp == '\r' || temp == '\n' || temp == -1)
//							break;
//						request.append((char) temp);
//					}
					if (request.toString().indexOf("HTTP/") != -1)
						out.write(header);
					out.write(content);
					out.flush();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				} finally {
					conn.close();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		try {
			String encoding = "ASCII";
			String fileName = "D:/index.html";
			String contentType = "text/plain";
			if (fileName.endsWith("html") || fileName.endsWith("htm"))
				contentType = "text/html";
			InputStream in = new FileInputStream(new File(fileName));
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int temp;
			while ((temp = in.read()) != -1)
				baos.write(temp);
			byte[] data = baos.toByteArray();
			SingleFileHttpServer server = new SingleFileHttpServer(data,
					encoding, contentType);
			server.start();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
}
