package t5750.netty.serial;

import java.io.File;
import java.io.FileInputStream;

import t5750.netty.util.MarshallingCodeCFactory;
import t5750.netty.util.NettyUtil;
import t5750.socket.util.GzipUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class SerialClient {
	public static void main(String[] args) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc)
							throws Exception {
						sc.pipeline().addLast(
								MarshallingCodeCFactory
										.buildMarshallingDecoder());
						sc.pipeline().addLast(
								MarshallingCodeCFactory
										.buildMarshallingEncoder());
						sc.pipeline().addLast(new ClientHandler());
					}
				});
		ChannelFuture cf = b.connect(NettyUtil.INET_HOST, NettyUtil.PORT_8765)
				.sync();
		for (int i = 0; i < 5; i++) {
			Req req = new Req();
			req.setId("" + i);
			req.setName("pro" + i);
			req.setRequestMessage("数据信息" + i);
			File file = new File(GzipUtils.READ_PATH);
			FileInputStream in = new FileInputStream(file);
			byte[] data = new byte[in.available()];
			in.read(data);
			in.close();
			req.setAttachment(GzipUtils.gzip(data));
			cf.channel().writeAndFlush(req);
		}
		cf.channel().closeFuture().sync();
		group.shutdownGracefully();
	}
}
