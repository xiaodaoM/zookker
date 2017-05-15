package com.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用 curator 实现 分布式的barrier
 * @author Administrator
 *
 */
public class Recipes_Barrier {
	
	static String barrier_path= "/zk_brarier";
	
	static DistributedBarrier  barrier;
	
	public static void main(String[] args) throws Exception {
		
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						CuratorFramework	client = CuratorFrameworkFactory.builder()
						.connectString("127.0.0.1:2181")
						.connectionTimeoutMs(5000)
						.retryPolicy(new ExponentialBackoffRetry(1000, 3))
						.build();
						client.start();
						 barrier = new DistributedBarrier(client, barrier_path);
						
						 System.out.println(Thread.currentThread().getName()+" 号 barrier设置");
						 barrier.setBarrier();
						 barrier.waitOnBarrier();
						 System.err.println("启动。。");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();;

			
		}
		
		Thread.sleep(2000);
		barrier.removeBarrier();
	}

}
