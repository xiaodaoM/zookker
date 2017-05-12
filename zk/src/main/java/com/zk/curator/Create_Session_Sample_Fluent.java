package com.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用 fluent f风格创建一个会话
 * @功能:TODO
 * @作者:zqp
 * @修改:
 */
public class Create_Session_Sample_Fluent {
	
	public static void main(String[] args) throws InterruptedException {
		ExponentialBackoffRetry policyRetry = new ExponentialBackoffRetry(1000, 3);
		
		CuratorFramework client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181")
		.sessionTimeoutMs(5000).retryPolicy(policyRetry).build();
		
		client.start();
		Thread.sleep(Integer.MAX_VALUE);
	}

}
