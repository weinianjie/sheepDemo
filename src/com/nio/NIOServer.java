package com.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

	public static void main(String[] args) throws Exception
	{
		Selector selector = Selector.open();
		ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		serverSocketChannel.socket().bind(new InetSocketAddress(8888));
		System.out.println("服务器已经启动");
		
		ServerSocketChannel server = null;  
        SocketChannel client = null;
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        HashMap<Integer, Integer> sendCounter = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> receiveCounter = new HashMap<Integer, Integer>();
//		int sendCounter = 0;
//		int receiveCounter = 0;
		while(true)
		{
			try {
				selector.select();
				Set<SelectionKey> keys = selector.selectedKeys();
				Iterator<SelectionKey> iterator = keys.iterator();
				while(iterator.hasNext())
				{
					SelectionKey key = iterator.next();
					iterator.remove();
					System.out.println("接收到事件，事件类型为" + key.readyOps());
					if(key.isAcceptable())
					{
						System.out.println("进入accept");
						server = (ServerSocketChannel) key.channel();
						client = server.accept();
						client.configureBlocking(false);
						
						//初始化计数器
						sendCounter.put(client.socket().hashCode(), new Integer(0));
						receiveCounter.put(client.socket().hashCode(), new Integer(0));
						
						client.register(selector, SelectionKey.OP_READ);
					}else if(key.isReadable())
					{
						try {
							System.out.println("进入read");
							buffer.clear();
							client = (SocketChannel) key.channel();
							int bSize = client.read(buffer);
							if(bSize > 0)
							{
								Integer _sendCounter = sendCounter.get(client.socket().hashCode());
								_sendCounter = _sendCounter + 1;
								sendCounter.put(client.socket().hashCode(), _sendCounter);
								Integer _receiveCounter = receiveCounter.get(client.socket().hashCode());
								_receiveCounter = _receiveCounter + 1;
								receiveCounter.put(client.socket().hashCode(), _receiveCounter);
								
								System.out.println("socket=" + client.socket() + ",hashCode=" + client.socket().hashCode());
								String str = new String(buffer.array(), 0 ,bSize);
								System.out.println("服务端读取到客户端传过来的第" + _receiveCounter + "个数据：size=" + bSize + ",str=" + str);
								
								// 理论上这里直接针对读取到的数据做写回响应，没有下面的else了
								buffer.clear();
								str="韦念杰第一个NIO，服务端发给客户端的消息" + _sendCounter;
								buffer.put(str.getBytes());
								buffer.flip();
								client.write(buffer);
								System.out.println("服务端给客户端发送第" + _sendCounter + "个消息：size=" + "未知" + ",str=" + str);
								
								Thread.sleep(5000);
								
								key.interestOps(SelectionKey.OP_READ);
//						key.interestOps(SelectionKey.OP_READ  |  SelectionKey.OP_WRITE);
//						client.register(selector, SelectionKey.OP_WRITE);
							}
						} catch (Exception e) {
							e.printStackTrace();
							client = (SocketChannel) key.channel();
							client.close();
						}
					}else if(key.isWritable())
					{
						System.out.println("进入write");
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
