package t5750.netty.udp;

import java.net.InetSocketAddress;

import t5750.netty.util.NettyUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

public class UdpClient {
	public void run(int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class)
					.option(ChannelOption.SO_BROADCAST, true)
					.handler(new ClientHandler());
			Channel ch = b.bind(0).sync().channel();
			// 向网段内的所有机器广播UDP消息
			ch.writeAndFlush(
					new DatagramPacket(Unpooled.copiedBuffer("谚语字典查询?",
							CharsetUtil.UTF_8), new InetSocketAddress(
							NettyUtil.INET_HOST, port))).sync();
			if (!ch.closeFuture().await(15000)) {
				System.out.println("查询超时!");
			}
		} finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws Exception {
		new UdpClient().run(NettyUtil.PORT_8765);
	}
}
