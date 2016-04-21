package com.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


public class NettyServer {
	public static void main(String[] args)
	{
        new NettyServer().start();
	}
	
	private void start()
	{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup);
			b.channel(NioServerSocketChannel.class);
			b.option(ChannelOption.SO_BACKLOG, 1024);
			b.childHandler(new ServerChannelHandler());
			b.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			ChannelFuture f = b.bind(8888).sync();
			System.out.println("服务器已启动");
			f.channel().closeFuture().sync();
			System.out.println("closeFuture");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
	}
	
	private class ServerChannelHandler extends ChannelInitializer<SocketChannel>
	{
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			//ChannelOutboundHandler 在注册的时候需要放在最后一个ChannelInboundHandler之前，否则将无法传递到ChannelOutboundHandler。
			ch.pipeline().addLast(new MyServerOutboundHandler1());
			ch.pipeline().addLast(new MyServerOutboundHandler2());
			ch.pipeline().addLast(new MyServerInboundHandler1());
			ch.pipeline().addLast(new MyServerInboundHandler2());
		}
	}
	
	private class MyServerInboundHandler1 extends ChannelInboundHandlerAdapter
	{
		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			System.out.println("进入MyServerInboundHandler1-channelRead");
				ctx.fireChannelRead(msg);
		}

		@Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
	        cause.printStackTrace();
	        ctx.close();
	    }
	}
	
	private class MyServerInboundHandler2 extends ChannelInboundHandlerAdapter
	{

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			try {
				System.out.println("进入MyServerInboundHandler2-channelRead");
				ByteBuf result = (ByteBuf)msg;
				byte[] bytes = new byte[result.readableBytes()];
				result.readBytes(bytes);
				String str = new String(bytes);
				System.out.println("接收到客户端数据：" + str);
				
				// 回写数据
				result.resetReaderIndex();
				ctx.write(msg);
				ctx.flush();
				
//		        result.release();// msg也就是result，被写到线路里了，写是能自动release的
		    } finally {
//		        ReferenceCountUtil.release(msg);
		    }
		}
		
		@Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { 
	        cause.printStackTrace();
	        ctx.close();
	    }
	}
	
	private class MyServerOutboundHandler1 extends ChannelOutboundHandlerAdapter
	{

		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
			System.out.println("进入MyServerOutboundHandler1-write");
			// 拦截数据
			ByteBuf result = (ByteBuf)msg;
			byte[] bytes = new byte[result.readableBytes()];
			result.readBytes(bytes);
			String str = new String(bytes);
			System.out.println("拦截到数据：" + str);
			
			// 中转数据，同时改写
			str = "数据经过了中转";
			ByteBuf buf = ctx.alloc().buffer(str.length() * 4);
			buf.writeBytes(str.getBytes());
			ctx.write(buf);
			ctx.flush();
		}
	}
	
	private class MyServerOutboundHandler2 extends ChannelOutboundHandlerAdapter
	{

		@Override
		public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
			System.out.println("进入MyServerOutboundHandler2-write");
			super.write(ctx, msg, promise);
		}
	}
}
