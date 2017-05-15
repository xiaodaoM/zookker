package com.zk.zkpath;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.EnsurePath;

/**
 * 使用这个方法是因为 不用判断节点存在否 如果存在也不会抛出异常。不存在就创建一个
 * @author Administrator
 *
 */
public class EnSurePathSample {
static String path="/zk-book/c1";
	
	static CuratorFramework client = CuratorFrameworkFactory
			.builder()
			.connectString("127.0.0.1:2181")
			.connectionTimeoutMs(5000)
			.retryPolicy(new ExponentialBackoffRetry(1000, 3))
			.build();
	
	public static void main(String[] args) throws Exception {
		client.start();
		client.usingNamespace("zk-book");
//		
		EnsurePath ensure = new EnsurePath(path);
		//这一步是创建节点 
		ensure.ensure(client.getZookeeperClient());
		// 第二次创建没有报错
		ensure.ensure(client.getZookeeperClient());
//		
		EnsurePath ensure2 = client.newNamespaceAwareEnsurePath("/c1");
		ensure2.ensure(client.getZookeeperClient());
//		client.delete().deletingChildrenIfNeeded().forPath("/zk-paths");
//		client.delete().deletingChildrenIfNeeded().forPath("/c1");
	}
	

}
