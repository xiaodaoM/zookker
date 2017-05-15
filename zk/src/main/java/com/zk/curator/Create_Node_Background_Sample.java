package com.zk.curator;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.BackgroundCallback;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

//curator 异步创建
public class Create_Node_Background_Sample {

	static String path="/zk-book";
	
	static CuratorFramework client = CuratorFrameworkFactory
			.builder()
			.connectString("127.0.0.1:2181")
			.connectionTimeoutMs(5000)
			.retryPolicy(new ExponentialBackoffRetry(1000, 3))
			.build();
	static CountDownLatch samephore = new CountDownLatch(2);
	//固定2个线程的线程池
	static ExecutorService executor = Executors.newFixedThreadPool(2);
	
	public static void main(String[] args) throws Exception {
		client.start();
		System.out.println("main thread "+ Thread.currentThread().getName());
		client.create()
		.creatingParentsIfNeeded()//如果没有父节点就创建父节点
		.withMode(CreateMode.EPHEMERAL)
		.inBackground(new BackgroundCallback() {
			
			@Override
			public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
				// TODO Auto-generated method stub
				
				System.out.println("event [code :" + event.getResultCode()
				+" type :"+ event.getType()+"]");
				
				System.out.println("Thread processResult : "+ Thread.currentThread().getName());
				samephore.countDown();
			}
		}, executor)//这里传入自定义的线程池
		
		.forPath(path, "init".getBytes());
		
		
		//这里创建节点   不传入线程池
		
		client.create()
		.creatingParentsIfNeeded()
		.inBackground(new BackgroundCallback() {
			
			@Override
			public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
				System.out.println("event [code :" + event.getResultCode()
				+" type :"+ event.getType()+"]");
				
				System.out.println("Thread processResult : "+ Thread.currentThread().getName());
				samephore.countDown();
				
			}
		}).forPath(path,"async no executor".getBytes());
		
		samephore.await();
		executor.shutdown();
	}
	
}
