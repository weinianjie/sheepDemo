package com.socket.L4;

import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerPool {
	private static List<Socket> pool = null;
	
	public ServerPool() {
		pool = new ArrayList<Socket>();
	}
	
	
}
