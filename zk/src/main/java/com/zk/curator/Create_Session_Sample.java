package com.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

//* 使用 curator 创建一个会话
public class Create_Session_Sample {

	
	public static void main(String[] args) throws InterruptedException {
		//先创建一个重试策略   1初始 sleep 的时间,最大重试的次数
		ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);
		CuratorFramework zkClient = CuratorFrameworkFactory.
				newClient("127.0.0.1:2181", 30000, 5000, retryPolicy);
		
		zkClient.start();
		Thread.sleep(Integer.MAX_VALUE);
	}
}
