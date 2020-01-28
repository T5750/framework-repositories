package t5750.netty.ende;

import t5750.netty.util.NettyUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class FixedLengthClient {
	public static void main(String[] args) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc)
							throws Exception {
						sc.pipeline().addLast(
								new FixedLengthFrameDecoder(
										NettyUtil.FIXED_LENGTH_FRAME_DECODER));
						sc.pipeline().addLast(new StringDecoder());
						sc.pipeline().addLast(new ClientHandler());
					}
				});
		ChannelFuture cf = b.connect(NettyUtil.INET_HOST, NettyUtil.PORT_8765)
				.sync();
		cf.channel().writeAndFlush(
				Unpooled.wrappedBuffer("aaaaabbbbb".getBytes()));
		// cf.channel().writeAndFlush(Unpooled.copiedBuffer("ccccccc".getBytes()));
		cf.channel().writeAndFlush(
				Unpooled.copiedBuffer("ccccccc   ".getBytes()));
		// 等待客户端端口关闭
		cf.channel().closeFuture().sync();
		group.shutdownGracefully();
	}
}
