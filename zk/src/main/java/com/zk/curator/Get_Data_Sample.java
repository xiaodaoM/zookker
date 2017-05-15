package com.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
/**
 * 获取节点数据
 * @author Administrator
 *
 */
public class Get_Data_Sample {
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
		 Stat stat = new Stat();
		 //获取数据
		System.out.println(new String(client.getData().storingStatIn(stat).forPath(path)));
		 
	}
}
