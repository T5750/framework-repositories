package t5750.netty.ende;

import t5750.netty.util.NettyUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class FixedLengthServer {
	public static void main(String[] args) throws Exception {
		// 1 创建2个线程，一个是负责接收客户端的连接。一个是负责进行数据传输的
		EventLoopGroup pGroup = new NioEventLoopGroup();
		EventLoopGroup cGroup = new NioEventLoopGroup();
		// 2 创建服务器辅助类
		ServerBootstrap b = new ServerBootstrap();
		b.group(pGroup, cGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024)
				// .option(ChannelOption.SO_SNDBUF, 32 * 1024)
				.option(ChannelOption.SO_RCVBUF, 32 * 1024)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc)
							throws Exception {
						// 设置定长字符串接收
						sc.pipeline().addLast(
								new FixedLengthFrameDecoder(
										NettyUtil.FIXED_LENGTH_FRAME_DECODER));
						// 设置字符串形式的解码
						sc.pipeline().addLast(new StringDecoder());
						sc.pipeline().addLast(new FixedLengthServerHandler());
					}
				});
		// 4 绑定连接
		ChannelFuture cf = b.bind(NettyUtil.PORT_8765).sync();
		// 等待服务器监听端口关闭
		cf.channel().closeFuture().sync();
		pGroup.shutdownGracefully();
		cGroup.shutdownGracefully();
	}
}
