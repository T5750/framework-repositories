package t5750.dubbox.service.extension;

import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;

public class DynamicTraceBinding implements DynamicFeature {
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		context.register(DynamicTraceInterceptor.class);
	}
}