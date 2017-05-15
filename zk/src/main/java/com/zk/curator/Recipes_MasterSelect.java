package com.zk.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 *  master 选举 。curator 已经封装好了
 * @author Administrator
 * 
 * 暂时未实验成功 。可能需要3台  懒得配置了。。。。
 *
 */
public class Recipes_MasterSelect {
	private static String path = "/master_recipes_select";
	
	 static CuratorFramework client =  CuratorFrameworkFactory.builder()
			 .connectString("127.0.0.1:2181")
			 .sessionTimeoutMs(30000)
			 .connectionTimeoutMs(5000)
			 .retryPolicy( new ExponentialBackoffRetry(1000, 3))
			 .build();
	 
	 public static void main(String[] args) throws InterruptedException {
		client.start();
		LeaderSelector leaderSelector = new LeaderSelector(client, path,
				new LeaderSelectorListenerAdapter() {
			
			@Override
			public void takeLeadership(CuratorFramework client) throws Exception {
				String currentConnectionString = client.getZookeeperClient().getCurrentConnectionString();
			System.out.println("成为master 成功"+ currentConnectionString);

			Thread.sleep(3000);
			System.out.println("完成 master 选举  释放 master 权利");
			}
		});
		leaderSelector.autoRequeue();
		leaderSelector.start();
		Thread.sleep(Integer.MAX_VALUE);
		
		
	}
}
