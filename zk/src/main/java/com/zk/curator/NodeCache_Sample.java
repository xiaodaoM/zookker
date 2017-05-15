package com.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 使用 curator 对节点 进行监听
 * @author Administrator
 *
 */
public class NodeCache_Sample {
	
	static String path = "/zk-book/nodecache";
	static CuratorFramework client = CuratorFrameworkFactory
			.builder()
			.connectString("127.0.0.1:2181")
			.connectionTimeoutMs(5000)
			.retryPolicy(new ExponentialBackoffRetry(1000, 3))
			.build();
	
	public static void main(String[] args) throws Exception {
		client.start();
		//创建一个节点
		client.create()
		.creatingParentsIfNeeded()
		.withMode(CreateMode.EPHEMERAL)
		.forPath(path, "init".getBytes());
		
		//创建nodecache 最后一个参数是否进行数据压缩。。
		final NodeCache cache = new NodeCache(client,path,false);
		//如果为true 时 第一次启动时就
		//从服务器上 拉取数据放到cache z中
		cache.start(true);
		//进行监听。如果有值改变的话就监听
		cache.getListenable().addListener(new NodeCacheListener() {
			
			public void nodeChanged() throws Exception {
			System.out.println("node update current data:"+
			new String(cache.getCurrentData().getData()));
				
			}
		});
		
		client.setData().forPath(path, "u".getBytes());
		Thread.sleep(1000);
		//节点删除时并不能触发 nodechanged
		client.delete().deletingChildrenIfNeeded()
		.forPath(path);
		Thread.sleep(Integer.MAX_VALUE);
		
	}
			
			

}
