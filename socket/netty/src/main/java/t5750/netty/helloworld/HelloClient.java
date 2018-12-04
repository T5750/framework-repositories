package t5750.netty.helloworld;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import t5750.netty.util.NettyUtil;

public class HelloClient {
	public static final String CHANNEL_FUTURE1 = "cf1-";
	public static final String CHANNEL_FUTURE2 = "cf2-";

	public static void main(String[] args) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc)
							throws Exception {
						sc.pipeline().addLast(new ClientHandler());
					}
				});
		ChannelFuture cf1 = b.connect(NettyUtil.INET_HOST, NettyUtil.PORT_8765)
				.sync();
		ChannelFuture cf2 = b.connect(NettyUtil.INET_HOST, NettyUtil.PORT_8764)
				.sync();
		System.out.println("Client connected...");
		// 发送消息
		Thread.sleep(1000);
		cf1.channel().writeAndFlush(
				Unpooled.copiedBuffer((CHANNEL_FUTURE1 + "777").getBytes()));
		cf1.channel().writeAndFlush(
				Unpooled.copiedBuffer((CHANNEL_FUTURE1 + "666").getBytes()));
		cf2.channel().writeAndFlush(
				Unpooled.copiedBuffer((CHANNEL_FUTURE2 + "888").getBytes()));
		Thread.sleep(2000);
		cf1.channel().writeAndFlush(
				Unpooled.copiedBuffer((CHANNEL_FUTURE1 + "888").getBytes()));
		cf2.channel().writeAndFlush(
				Unpooled.copiedBuffer((CHANNEL_FUTURE2 + "666").getBytes()));
		cf1.channel().closeFuture().sync();
		cf2.channel().closeFuture().sync();
		group.shutdownGracefully();
	}
}
