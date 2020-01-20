package t5750.dubbo.spi;

import org.junit.Test;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import com.alibaba.dubbo.remoting.Transporter;
import com.alibaba.dubbo.rpc.Protocol;

/**
 * 自适应拓展：getAdaptiveExtension()入口
 */
public class AdaptiveExtensionClassTest {
	@Test
	public void testProtocol() throws Exception {
		ExtensionLoader<Protocol> extensionLoader = ExtensionLoader
				.getExtensionLoader(Protocol.class);
		Protocol protocol = extensionLoader.getAdaptiveExtension();
		System.out.println(protocol.getClass());
	}

	@Test
	public void testTransporter() throws Exception {
		ExtensionLoader<Transporter> extensionLoader = ExtensionLoader
				.getExtensionLoader(Transporter.class);
		Transporter transporter = extensionLoader.getAdaptiveExtension();
		System.out.println(transporter.getClass());
	}
}
