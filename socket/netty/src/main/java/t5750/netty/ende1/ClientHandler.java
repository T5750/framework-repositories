package t5750.netty.ende1;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

public class ClientHandler extends SimpleChannelInboundHandler<Object> {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("Client channel active... ");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		try {
			String response = (String) msg;
			System.out.println("Client: " + response);
		} finally {
			ReferenceCountUtil.release(msg);
		}
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx)
			throws Exception {
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
}
