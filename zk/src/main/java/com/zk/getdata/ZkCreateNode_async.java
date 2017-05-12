package com.zk.getdata;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
/**
 * 创建节点 异步的方法

 */
public class ZkCreateNode_async {
	private static String url ="127.0.0.1:2181";
	private static CountDownLatch  connected = new CountDownLatch(1);
	public static void main(String[] args) throws Exception {
		
		ZooKeeper zk = new ZooKeeper(url, 5000, new Watcher() {
			
			@Override
			public void process(WatchedEvent event) {
				System.out.println("receive watched "+event);
				if(event.getState() == KeeperState.SyncConnected){
					connected.countDown();
				}
				
			}
		});
		connected.await();
		
		 zk.create("/zk-test", "".getBytes(), 
		    		Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, 
		    		new IStringCallback(), "I am cointext.");
		 zk.create("/zk-test", "".getBytes(), 
				 Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL, 
				 new IStringCallback(), "I am cointext.");
		 zk.create("/zk-test", "".getBytes(), 
				 Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL, 
				 new IStringCallback(), "I am cointext.");
		Thread.sleep(Integer.MAX_VALUE);
		
	
}

	}
 class IStringCallback implements AsyncCallback.StringCallback{
	  public void processResult(int rc, String path, Object ctx, String name) {
	    System.out.println("Create path result: [" + rc + ", " + path + ", "
	                   + ctx + ", real path name: " + name);
	    }


}
