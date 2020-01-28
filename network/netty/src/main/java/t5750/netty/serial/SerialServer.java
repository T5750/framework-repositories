package t5750.netty.serial;

import t5750.netty.util.MarshallingCodeCFactory;
import t5750.netty.util.NettyUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SerialServer {
	public static void main(String[] args) throws Exception {
		EventLoopGroup pGroup = new NioEventLoopGroup();
		EventLoopGroup cGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(pGroup, cGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				// 设置日志
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc)
							throws Exception {
						sc.pipeline().addLast(
								MarshallingCodeCFactory
										.buildMarshallingDecoder());
						sc.pipeline().addLast(
								MarshallingCodeCFactory
										.buildMarshallingEncoder());
						sc.pipeline().addLast(new ServerHandler());
					}
				});
		ChannelFuture cf = b.bind(NettyUtil.PORT_8765).sync();
		cf.channel().closeFuture().sync();
		pGroup.shutdownGracefully();
		cGroup.shutdownGracefully();
	}
}
