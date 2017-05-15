package com.zk.zkpath;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;
import org.apache.curator.utils.ZKPaths.PathAndNode;
import org.apache.zookeeper.ZooKeeper;

/**
 * 
 * @author zqp
 *
 */
public class ZkPaths_Sample {
		static String path="/zk-paths";
	
	static CuratorFramework client = CuratorFrameworkFactory
			.builder()
			.connectString("127.0.0.1:2181")
			.connectionTimeoutMs(5000)
			.retryPolicy(new ExponentialBackoffRetry(1000, 3))
			.build();
	public static void main(String[] args) throws Exception {
		client.start();
		ZooKeeper zookeeper = client.getZookeeperClient().getZooKeeper();
		//申请一个工作空间
		System.out.println(ZKPaths.fixForNamespace(path, "/sub"));
		System.out.println(ZKPaths.makePath(path, "/sub"));
		
		System.out.println(ZKPaths.getNodeFromPath("/zk-paths/sub1"));
		PathAndNode pn = ZKPaths.getPathAndNode("/zk-paths/sub1");
		System.out.println(pn.getNode());
		System.out.println(pn.getPath());
		
		String dir1 = path+"/child1";
//		String dir2 = path+"/child2";
		ZKPaths.mkdirs(zookeeper, dir1);
		client.setData().forPath("/zk-paths/child1","123".getBytes());
//		ZKPaths.mkdirs(zookeeper, dir2);
//		System.out.println(ZKPaths.getSortedChildren(zookeeper, path));
	
//		ZKPaths.deleteChildren(zookeeper, path, true);
	}
}
