package com.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.io.IOException;

public class NettyClient2 {
	
	private Channel channel = null;
	private Bootstrap b = null;
	
	public static void main(String[] args)
	{
		NettyClient2 instance = new NettyClient2();
		instance.request();
		
		int i = 0;
		while(true)
		{
			instance.send("客户端发送的第" + i++ + "次消息");
			try
			{
				Thread.sleep(5000l);
			} catch (InterruptedException e)
			{
			}
		}
		
	}
	
	public ChannelFuture send(String msg)
	{
		System.out.println("send:" + msg);
		ByteBuf buf = channel.alloc().buffer(msg.length() * 4);
		buf.writeBytes(msg.getBytes());
		return send(buf);
	}
	
	public ChannelFuture send(final Object msg)
	{
//		return this.channel.writeAndFlush(msg);
		ChannelFuture xf = this.channel.write(msg);
		try
		{
			xf.addListener(new ChannelFutureListener() {

				private int tryTimes = 0;
				
	            @Override
	            public void operationComplete(ChannelFuture future) throws Exception {
	            	System.out.println("into future listener");
	                if (!future.isSuccess()) {
	                	Throwable cause = future.cause();
	                	if(cause != null && cause instanceof IOException)
	                	{
	                		// 失败了，重连
	                		System.out.println("失败了，重连");
	                		future.channel().close();// 强制释放失效连接
	                		
	                		if(tryTimes < 3)
	                		{
		                		// 重连
		                        ChannelFuture f = b.connect("127.0.0.1", 8888).sync();
		                        channel = f.channel();
		            			System.out.println("客户端已连接" + tryTimes++);
		            			
		                		// 把当前消息扔到该连接下重发，复用当前监听器
		            			send(msg).addListener(this);
	                		}
	                	}
	                }else
                	{
                		System.out.println("发送成功");
                	}
	            }
			});		
			
			this.channel.flush();
			
			System.out.println("---------------");
		}catch(Exception e)
		{
			System.out.println("xxxxxxxxxxxxxx");
		}
		
		return xf;
	}
	
	
	private void request()
	{
		EventLoopGroup group = new NioEventLoopGroup();
		try
		{
			b = new Bootstrap();
			b.group(group);
			b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ClientChannelHandler());
            ChannelFuture f = b.connect("127.0.0.1", 8888).sync();
            channel = f.channel();
			System.out.println("客户端已连接");
		}catch(Exception e)
		{
		}
	}
	
	private class ClientChannelHandler extends ChannelInitializer<SocketChannel>
	{
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new MyClientOutboundHandler(), new MyClientInboundHandler());
		}
	}
	
	private class MyClientInboundHandler extends ChannelInboundHandlerAdapter
	{

		
		
		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception
		{
			System.out.println("wwwwwwwwwwwwwww");
			ctx.close();
		}



		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
		{
			System.out.println("yyyyyyyyyyyyyyyyyyyyyyy");
			ctx.close();
		}



		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			System.out.println("进入MyClientInboundHandler-channelActive");
			
//			String msg = "第一个消息";
//			ByteBuf buf = channel.alloc().buffer(msg.length() * 4);
//			buf.writeBytes(msg.getBytes());
//			ctx.writeAndFlush(buf);
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			try
			{
				System.out.println("进入MyClientInboundHandler-channelRead");
				ByteBuf result = (ByteBuf)msg;
				byte[] bytes = new byte[result.readableBytes()];
				result.readBytes(bytes);
				String str = new String(bytes);
				System.out.println("receive：" + str);
				
//				int i = 0;
//				while(true)
//				{
//					String msg2 = "客户端发送的第" + i++ + "次消息";
//					ByteBuf buf = channel.alloc().buffer(msg2.length() * 4);
//					buf.writeBytes(msg2.getBytes());
//					ctx.writeAndFlush(buf);
//					Thread.sleep(5000);
//				}
			}finally
			{
				ReferenceCountUtil.release(msg);
				
				// 断开连接
//				ctx.close();
			}
		}
	}
	
	private class MyClientOutboundHandler extends ChannelOutboundHandlerAdapter
	{

		@Override
		public void write(ChannelHandlerContext ctx, final Object msg, ChannelPromise promise) throws Exception
		{
			System.out.println("进入MyClientOutboundHandler-write");
			ChannelFuture f = ctx.write(msg);
			
//			f.addListener(new ChannelFutureListener() {
//
//				private int tryTimes = 0;
//				
//	            @Override
//	            public void operationComplete(ChannelFuture future) throws Exception {
//	            	System.out.println("into future listener");
//	                if (!future.isSuccess()) {
//	                	Throwable cause = future.cause();
//	                	if(cause != null && cause instanceof IOException)
//	                	{
//	                		// 失败了，重连
//	                		System.out.println("失败了，重连");
//	                		future.channel().close();// 强制释放失效连接
//	                		
//	                		if(tryTimes < 3)
//	                		{
//		                		// 重连
//		                        ChannelFuture f = b.connect("127.0.0.1", 8888).sync();
//		                        channel = f.channel();
//		            			System.out.println("客户端已连接" + tryTimes++);
//		            			
//		                		// 把当前消息扔到该连接下重发，复用当前监听器
//		            			send(msg).addListener(this);
//	                		}
//	                	}
//	                }else
//                	{
//                		System.out.println("发送成功");
//                	}
//	            }
//			});
//			
//	        System.out.println("write end");
		}
		
	}
}
