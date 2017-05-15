package com.zk.curatortest;

import java.io.File;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.test.TestingServer;

public class Curator_test {
	
	static String path = "/zookeeper";
	
	public static void main(String[] args) throws Exception {
		
		TestingServer server = new TestingServer(2181, new File("e:/curatorTest"));
		
		CuratorFramework client = CuratorFrameworkFactory.builder()
		.connectString(server.getConnectString())
		.retryPolicy(new ExponentialBackoffRetry(1000, 3))
		.connectionTimeoutMs(5000)
		.build();
		
		client.start();
		System.out.println(client.getChildren().forPath(path));
		server.close();
		
	}

}
