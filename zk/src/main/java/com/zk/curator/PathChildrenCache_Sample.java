package com.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

//对子节点进行监听。只能对一级子节点进行监听 而且不能监听他的本身
public class PathChildrenCache_Sample {
	static String path = "/zk-book";
	static CuratorFramework client = CuratorFrameworkFactory
			.builder()
			.connectString("127.0.0.1:2181")
			.connectionTimeoutMs(5000)
			.retryPolicy(new ExponentialBackoffRetry(1000, 3))
			.build();
	
	public static void main(String[] args) throws Exception {
		client.start();
		PathChildrenCache cache = new PathChildrenCache(client, path, false);
		cache.start(StartMode.POST_INITIALIZED_EVENT);
		cache.getListenable().addListener(new PathChildrenCacheListener() {
			
			@Override
			public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
				// TODO Auto-generated method stub
				switch (event.getType()) {
				case CHILD_ADDED:
					System.out.println("CHILD_ADDED"+ event.getData());
					break;
				case CHILD_UPDATED:
					System.out.println("CHILD_UPDATED"+ event.getData());
					break;
				case CHILD_REMOVED:
					System.out.println("CHILD_REMOVED"+ event.getData());
					break;
				default:
					break;
				}
			}
		});
		
		client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
		.forPath(path);
		Thread.sleep(1000);
		client.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
		.forPath(path+"/c1","init".getBytes());
		Thread.sleep(1000);
		
		client.delete().forPath(path+"/c1");
		Thread.sleep(1000);
		client.delete().forPath(path);
		Thread.sleep(Integer.MAX_VALUE);
		
	}
}
