package com.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

public class NettyClient {
	public static void main(String[] args)
	{
		new NettyClient().request();
	}
	
	private void request()
	{
		EventLoopGroup group = new NioEventLoopGroup();
		try
		{
			Bootstrap b = new Bootstrap();
			b.group(group);
			b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ClientChannelHandler());
            ChannelFuture f = b.connect("127.0.0.1", 8888).sync();
			System.out.println("客户端已连接");
			f.channel().closeFuture().sync();
			System.out.println("closeFuture");
		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			group.shutdownGracefully();
		}
	}
	
	private class ClientChannelHandler extends ChannelInitializer<SocketChannel>
	{
		@Override
		protected void initChannel(SocketChannel ch) throws Exception {
			ch.pipeline().addLast(new MyClientInboundHandler());
		}
	}
	
	private class MyClientInboundHandler extends ChannelInboundHandlerAdapter
	{

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			System.out.println("进入MyClientInboundHandler-channelActive");
			
			String str = "这是客户端发给服务端的数据";
			ByteBuf buf = ctx.alloc().buffer(str.length() * 4);
			buf.writeBytes(str.getBytes());
			ctx.write(buf);
			ctx.flush();
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
				System.out.println("接收到服务端数据：" + str);
			}finally
			{
				ReferenceCountUtil.release(msg);
				
				// 断开连接
				ctx.close();
			}
		}
	}
}
