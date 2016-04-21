package com.HTTPServer.L3;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Java版 HTTP服务器
 */
public class JHTTPServer extends Thread {

	private File docRootDir;
	private String indexFileName;
	private ServerSocket server;
	private int numThread = 50;

	public JHTTPServer(File docRootDir) throws IOException {
		this(docRootDir, 80, "index.html");
	}
	
	public JHTTPServer(File docRootDir, int port) throws IOException {
		this(docRootDir, port, "index.html");
	}	

	public JHTTPServer(File docRootDir, int port, String indexFileName)
			throws IOException {
		this.docRootDir = docRootDir;
		this.indexFileName = indexFileName;
		server = new ServerSocket(port);
	}

	public void run() {
		for (int i = 0; i < numThread; i++) {
			Thread t = new Thread(new RequestProcessor(docRootDir,
					indexFileName));
			t.start();
		}
		System.out.println("Accepting connections on port "
				+ server.getLocalPort());
		System.out.println("Document Root: " + docRootDir);
		while (true) {
			try {
				Socket conn = server.accept();
				RequestProcessor.processRequest(conn);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void main(String[] args) {
		File docRoot = new File("D:/src/HTML_CSS");
		try {
			new JHTTPServer(docRoot, 8892).start();
		} catch (IOException e) {
			System.out.println("Server could not start because of an "
					+ e.getClass());
			System.out.println(e);
		}
	}
}
