package t5750.springboot2.listener;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomCacheEventListener
		implements CacheEventListener<Object, Object> {
	private static final Logger LOG = LoggerFactory
			.getLogger(CustomCacheEventListener.class);

	@Override
	public void onEvent(CacheEvent cacheEvent) {
		LOG.info("Cache event = {}, Key = {},  Old value = {}, New value = {}",
				cacheEvent.getType(), cacheEvent.getKey(),
				cacheEvent.getOldValue(), cacheEvent.getNewValue());
	}
}