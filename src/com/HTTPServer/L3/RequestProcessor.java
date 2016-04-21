package com.HTTPServer.L3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 服务线程池
 */
public class RequestProcessor implements Runnable {

	private static List<Socket> pool = new LinkedList<Socket>();
	private File docRootDir;
	private String indexFileName;

	public RequestProcessor(File docRootDir, String indexFileName) {
		if (docRootDir.isFile())
			throw new IllegalArgumentException(
					"documentRootDirectory must be a directory, not a file");
		this.docRootDir = docRootDir;
		try {
			this.docRootDir = docRootDir.getCanonicalFile();
		} catch (IOException ex) {
		}
		this.indexFileName = indexFileName;
	}

	public static void processRequest(Socket conn) {
		synchronized (pool) {
			pool.add(pool.size(), conn);
			pool.notifyAll();
		}
	}

	public void run() {
		String root = docRootDir.getPath();
		while (true) {
			Socket conn;
			synchronized (pool) {
				while (pool.isEmpty()) {
					try {
						pool.wait();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}
				conn = pool.remove(0);
			}
			try {
				OutputStream raw = new BufferedOutputStream(
						conn.getOutputStream());
				Writer out = new OutputStreamWriter(raw);
				Reader in = new InputStreamReader(new BufferedInputStream(
						conn.getInputStream()));
				StringBuilder request = new StringBuilder();
				int c;
				while (true) {
					c = in.read();
					if (c == '\r' || c == '\n')
						break;
					request.append((char) c);
				}
				String get = request.toString();
				// 记录请求日志
				System.out.println(get);
				StringTokenizer st = new StringTokenizer(get);
				String method = st.nextToken();
				String version = "", fileName, contentType;
				if (method.equals("GET")) {
					fileName = st.nextToken();
					if (fileName.endsWith("/"))
						fileName += indexFileName;
					contentType = guessContentTypeFromName(fileName);
					if (st.hasMoreTokens())
						version = st.nextToken();
					File theFile = new File(docRootDir, fileName.substring(1,
							fileName.length()));
					// 不让请求超出文档根目录
					if (theFile.canRead()
							&& theFile.getCanonicalPath().startsWith(root)) {
						DataInputStream dis = new DataInputStream(
								new BufferedInputStream(new FileInputStream(
										theFile)));
						byte[] theData = new byte[(int) theFile.length()];
						dis.readFully(theData);
						dis.close();
						if (version.startsWith("HTTP ")) {
							out.write("HTTP/1.0 200 OK\r\n");
							out.write("Date" + new Date() + "\r\n");
							out.write("Server: JHTTP/1.0\r\n");
							out.write("Content-length: " + theData.length
									+ "\r\n");
							out.write("Content-type: " + contentType
									+ "\r\n\r\n");
							out.flush();
						}
						// 发送文件，可能是图片或其它二进制数据，所以使用底层的输出流不是书写器
						raw.write(theData);
						raw.flush();
					} else { // 没有找到文件
						if (version.startsWith("HTTP ")) { // send a MIME header
							out.write("HTTP/1.0 404 File Not Found\r\n");
							Date now = new Date();
							out.write("Date: " + now + "\r\n");
							out.write("Server: JHTTP/1.0\r\n");
							out.write("Content-type: text/html\r\n\r\n");
						}
						out.write("<HTML>\r\n");
						out.write("<HEAD><TITLE>File Not Found</TITLE>\r\n");
						out.write("</HEAD>\r\n");
						out.write("<BODY>");
						out.write("<H1>HTTP Error 404: File Not Found</H1>\r\n");
						out.write("</BODY></HTML>\r\n");
						out.flush();
					}
				} else { // method does not equal "GET"
					if (version.startsWith("HTTP ")) { // send a MIME header
						out.write("HTTP/1.0 501 Not Implemented\r\n");
						Date now = new Date();
						out.write("Date: " + now + "\r\n");
						out.write("Server: JHTTP 1.0\r\n");
						out.write("Content-type: text/html\r\n\r\n");
					}
					out.write("<HTML>\r\n");
					out.write("<HEAD><TITLE>Not Implemented</TITLE>\r\n");
					out.write("</HEAD>\r\n");
					out.write("<BODY>");
					out.write("<H1>HTTP Error 501: Not Implemented</H1>\r\n");
					out.write("</BODY></HTML>\r\n");
					out.flush();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			} finally {
				try {
					conn.close();
				} catch (IOException ex) {
				}
			}
		}
	}

	private String guessContentTypeFromName(String name) {
		if (name.endsWith(".html") || name.endsWith(".htm")) {
			return "text/html";
		} else if (name.endsWith(".txt") || name.endsWith(".java")) {
			return "text/plain";
		} else if (name.endsWith(".gif")) {
			return "image/gif";
		} else if (name.endsWith(".class")) {
			return "application/octet-stream";
		} else if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
			return "image/jpeg";
		} else
			return "text/plain";
	}
}
