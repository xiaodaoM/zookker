package com.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

/**
 * 更新 一个节点数据。
 * @author Administrator
 *
 */
public class Update_Data_Sample {
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
		System.out.println("version:"+stat.getVersion());
		
		Stat stat1 = client.setData()
		.withVersion(stat.getVersion())
		.forPath(path, "update".getBytes());
		System.out.println("version:"+stat1.getVersion());
		
		try {
			client.setData()
					.withVersion(stat.getVersion())
					.forPath(path, "update".getBytes());
		} catch (Exception e) {
			System.out.println("update error:"+ e.getMessage());
		}
		
	}
}
