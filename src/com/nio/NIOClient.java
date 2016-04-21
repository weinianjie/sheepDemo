package com.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOClient {
	public static void main(String[] args) throws Exception
	{
		Selector selector = Selector.open();
		SocketChannel socketChannel = SocketChannel.open();
		socketChannel.configureBlocking(false);
		socketChannel.register(selector, SelectionKey.OP_CONNECT);
		socketChannel.connect(new InetSocketAddress("127.0.0.1", 8888));
		System.out.println("客户端已经连接");
		
		SocketChannel server = null;
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		int sendCounter = 0;
		int receiveCounter = 0;
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
					if(key.isConnectable())
					{
						System.out.println("进入connect");
						server = (SocketChannel) key.channel();
						if (server.isConnectionPending()) {
							server.finishConnect();
							System.out.println("已经完成连接");
							
							// 连接后，首先由客户端给服务端发送消息
							System.out.println("socket=" + server.socket() + ",hashCode=" + server.socket().hashCode());
							buffer.clear();
							String str="韦念杰第一个NIO，客户端发给服务端的消息" + ++sendCounter;
							buffer.put(str.getBytes());
							buffer.flip();
							server.write(buffer);
							System.out.println("客户端给服务端发送第" + sendCounter + "个消息：size=" + "未知" + ",str=" + str);
							
							key.interestOps(SelectionKey.OP_READ );
//							key.interestOps(SelectionKey.OP_READ  |  SelectionKey.OP_WRITE);
//						client.register(selector, SelectionKey.OP_WRITE);
						}
					}else if(key.isReadable())
					{
						System.out.println("进入read");
						buffer.clear();
						server = (SocketChannel) key.channel();
						int bSize = server.read(buffer);
						if(bSize > 0)
						{
							System.out.println("socket=" + server.socket() + ",hashCode=" + server.socket().hashCode());
							String str = new String(buffer.array(), 0 ,bSize);
							System.out.println("客户端读取到服务端传过来的第" + ++receiveCounter + "个数据：size=" + bSize + ",str=" + str);
							
							// 理论上这里直接针对读取到的数据做写回响应，没有下面的else了
							buffer.clear();
							str="韦念杰第一个NIO，客户端发给服务端的消息" + ++sendCounter;
							buffer.put(str.getBytes());
							buffer.flip();
							server.write(buffer);
							System.out.println("客户端给服务端发送第" + sendCounter + "个消息：size=" + "未知" + ",str=" + str);
							
							Thread.sleep(5000);
							
							key.interestOps(SelectionKey.OP_READ );
//						key.interestOps(SelectionKey.OP_READ  |  SelectionKey.OP_WRITE);
//						client.register(selector, SelectionKey.OP_WRITE);
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
