package t5750.netty.upload;

import t5750.netty.util.HttpHeaderUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

/**
 * Handler that just dumps the contents of the response from the server
 */
public class HttpUploadClientHandler extends
		SimpleChannelInboundHandler<HttpObject> {
	private boolean readingChunks;

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) {
		if (msg instanceof HttpResponse) {
			HttpResponse response = (HttpResponse) msg;
			System.err.println("STATUS: " + response.status());
			System.err.println("VERSION: " + response.protocolVersion());
			if (!response.headers().isEmpty()) {
				for (CharSequence name : response.headers().names()) {
					for (CharSequence value : response.headers().getAll(name)) {
						System.err.println("HEADER: " + name + " = " + value);
					}
				}
			}
			if (response.status().code() == 200
					&& HttpHeaderUtil.isTransferEncodingChunked(response)) {
				readingChunks = true;
				System.err.println("CHUNKED CONTENT {");
			} else {
				System.err.println("CONTENT {");
			}
		}
		if (msg instanceof HttpContent) {
			HttpContent chunk = (HttpContent) msg;
			System.err.println(chunk.content().toString(CharsetUtil.UTF_8));
			if (chunk instanceof LastHttpContent) {
				if (readingChunks) {
					System.err.println("} END OF CHUNKED CONTENT");
				} else {
					System.err.println("} END OF CONTENT");
				}
				readingChunks = false;
			} else {
				System.err.println(chunk.content().toString(CharsetUtil.UTF_8));
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.channel().close();
	}

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg)
			throws Exception {
	}
}