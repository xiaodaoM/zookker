package com.zk.create;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
/**
 * 同步创建节点的方法
 */
public class ZkCreateNode_usage implements Watcher{
	private static String url ="127.0.0.1:2181";
	private static CountDownLatch  connected = new CountDownLatch(1);
	
	public static void main(String[] args) throws Exception {
		ZooKeeper zk = new ZooKeeper(url, 5000, new ZkCreateNode_usage());
		
		connected.await();
		
		String path1 = zk.create("/zk-test", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);//短暂的
		System.out.println("create success path1:"+ path1);
//		String path2 = zk.create("/zk-test", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);//短暂的
//		System.out.println("create success path1:"+ path2);
		
	}

	@Override
	public void process(WatchedEvent event) {
        if(KeeperState.SyncConnected == event.getState()){
        	connected.countDown();
             System.out.println("event :"+ event);
        
		
	}


}}