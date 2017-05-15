package com.zk.curator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * 使用curator 实现分布式锁
 * 就是 使用 interProcessMetex 的 aqu releasea 获取和释放
 * @author Administrator
 *
 */
public class Recipes_Lock {
	private static String path = "/curator_recipes_lock";
	
	 static CuratorFramework client =  CuratorFrameworkFactory.builder()
			 .connectString("127.0.0.1:2181")
			 .sessionTimeoutMs(30000)
			 .connectionTimeoutMs(50000)
			 .retryPolicy( new ExponentialBackoffRetry(1000, 3))
			 .build();
	 
	 public static void main(String[] args) {
		 client.start();
		final CountDownLatch down = new CountDownLatch(1);
		final	InterProcessMutex process = new InterProcessMutex(client, path);
		
		for (int i = 0; i < 30; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						down.await();
						process.acquire();
					} catch (Exception e) {
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd | SSS");
					String format = sdf.format(new Date());
					System.out.println("产生的订单编号为：" + format );
					
					
					try {
						process.release();
					} catch (Exception e) {
					}
				}
			}).start();
		}
	
		down.countDown();	
		
		
	}
}
