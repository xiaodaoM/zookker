package com.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.atomic.AtomicValue;
import org.apache.curator.framework.recipes.atomic.DistributedAtomicInteger;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.retry.RetryNTimes;

/**
 * 使用 curator 实现分布式 计数器
 * @author Administrator
 *
 */
public class Dist_Atomic {

	static String distAtomic = "/zk_disAtomic_path";
	 static CuratorFramework client =  CuratorFrameworkFactory.builder()
			 .connectString("127.0.0.1:2181")
			 .sessionTimeoutMs(30000)
			 .connectionTimeoutMs(50000)
			 .retryPolicy( new ExponentialBackoffRetry(1000, 3))
			 .build();
	 
	 public static void main(String[] args) throws Exception {
		
		 client.start();
		 DistributedAtomicInteger distributedAtomicInteger = new DistributedAtomicInteger(client, distAtomic, new RetryNTimes(3, 1000));
		 	
		 AtomicValue<Integer> add = distributedAtomicInteger.add(8);
		 System.out.println("result:"+ add.succeeded());
	 }
	
}
