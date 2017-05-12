package com.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.log4j.chainsaw.Main;
import org.apache.zookeeper.CreateMode;

/**
 * 使用 curator  创建节点
 * @author zqp
 *
 */
public class Create_Node_Sample {
	private static String path = "/zk-book/c1";
	
	 static CuratorFramework client =  CuratorFrameworkFactory.builder()
			 .connectString("127.0.0.1:2181")
			 .sessionTimeoutMs(30000)
			 .connectionTimeoutMs(50000)
			 .retryPolicy( new ExponentialBackoffRetry(1000, 3))
			 .build();
	 
	 public static void main(String[] args) throws Exception {
		
		 client.start();
		 
		 client.create()
		 .creatingParentsIfNeeded()
		 .withMode(CreateMode.EPHEMERAL)
		 .forPath(path,"init".getBytes());
		 
	}

}
