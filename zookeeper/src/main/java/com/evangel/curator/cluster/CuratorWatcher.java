package com.evangel.curator.cluster;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.zookeeper.CreateMode;

import com.evangel.curator.util.CuratorUtil;

public class CuratorWatcher {
	/** 父节点path */
	static final String PARENT_PATH = "/super";

	public CuratorWatcher() throws Exception {
		CuratorFramework cf = CuratorUtil.startCuratorFramework();
		// 4 创建跟节点
		if (cf.checkExists().forPath(PARENT_PATH) == null) {
			cf.create().withMode(CreateMode.PERSISTENT)
					.forPath(PARENT_PATH, "super init".getBytes());
		}
		// 4 建立一个PathChildrenCache缓存,第三个参数为是否接受节点数据内容 如果为false则不接受
		PathChildrenCache cache = new PathChildrenCache(cf, PARENT_PATH, true);
		// 5 在初始化的时候就进行缓存监听
		cache.start(StartMode.POST_INITIALIZED_EVENT);
		cache.getListenable().addListener(new PathChildrenCacheListener() {
			/**
			 * <B>方法名称：</B>监听子节点变更<BR>
			 * <B>概要说明：</B>新建、修改、删除<BR>
			 * 
			 * @see org.apache.curator.framework.recipes.cache.PathChildrenCacheListener#childEvent(org.apache.curator.framework.CuratorFramework,
			 *      org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent)
			 */
			@Override
			public void childEvent(CuratorFramework cf,
					PathChildrenCacheEvent event) throws Exception {
				switch (event.getType()) {
				case CHILD_ADDED:
					System.out.println("CHILD_ADDED :"
							+ event.getData().getPath());
					System.out.println("CHILD_ADDED :"
							+ new String(event.getData().getData()));
					break;
				case CHILD_UPDATED:
					System.out.println("CHILD_UPDATED :"
							+ event.getData().getPath());
					System.out.println("CHILD_UPDATED :"
							+ new String(event.getData().getData()));
					break;
				case CHILD_REMOVED:
					System.out.println("CHILD_REMOVED :"
							+ event.getData().getPath());
					System.out.println("CHILD_REMOVED :"
							+ new String(event.getData().getData()));
					break;
				default:
					break;
				}
			}
		});
	}
}
