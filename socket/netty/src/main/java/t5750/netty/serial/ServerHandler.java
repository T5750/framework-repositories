package t5750.netty.serial;

import java.io.FileOutputStream;

import t5750.util.GzipUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<Object> {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("---------------------------------------");
		System.out.println("Server channel active... ");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		Req req = (Req) msg;
		System.out.println("Server : " + req.getId() + ", " + req.getName()
				+ ", " + req.getRequestMessage());
		byte[] attachment = GzipUtils.ungzip(req.getAttachment());
		String path = GzipUtils.WRITE_PATH;
		FileOutputStream fos = new FileOutputStream(path);
		fos.write(attachment);
		fos.close();
		Resp resp = new Resp();
		resp.setId(req.getId());
		resp.setName("resp" + req.getId());
		resp.setResponseMessage("响应内容" + req.getId());
		ctx.writeAndFlush(resp);// .addListener(ChannelFutureListener.CLOSE);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg)
			throws Exception {
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		ctx.close();
	}
}
