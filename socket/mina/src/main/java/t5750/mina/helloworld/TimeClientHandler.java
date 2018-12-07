package t5750.mina.helloworld;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

public class TimeClientHandler extends IoHandlerAdapter {
	/**
	 * 当接收到客户端的请求信息后触发此方法
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String content = message.toString();
		System.out.println("client receive a message is :" + content);
	}

	/**
	 * 当信息已经传送给客户端后触发此方法
	 */
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("message - > : " + message);
	}
}
