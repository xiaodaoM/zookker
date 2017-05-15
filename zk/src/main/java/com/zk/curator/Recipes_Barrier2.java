package com.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.barriers.DistributedDoubleBarrier;
import org.apache.curator.retry.ExponentialBackoffRetry;
/**
 *  
 * @author zqp
 * 
 *
 */
public class Recipes_Barrier2 {
static String barrier_path= "/zk_brarier";
	
	
	public static void main(String[] args) throws Exception {
		
		for (int i = 0; i < 5; i++) {
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
						DistributedDoubleBarrier doubleBarrier = new  DistributedDoubleBarrier(client, barrier_path, 5);
						Thread.sleep(Math.round(Math.random()*3000));
						System.out.println(Thread.currentThread().getName()+" 号 barrier设置");
						//所有调用enter 方法后等待 如果进入的线程数量= 给它是设置的阈值 就同时触发进入
						doubleBarrier.enter();
						System.err.println("启动。。");
						Thread.sleep(Math.round(Math.random()*3000));
						//所有调用leave 方法后等待 如果进入的线程数量= 给它是设置的阈值 就同时触发
						doubleBarrier.leave();
						System.err.println("离开。。");
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}).start();;

			
		}
		
	}

}
