package com.zk.getchild;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooKeeper;

public class ConnectorZk_usage_simple implements Watcher{

	private static CountDownLatch connected = new CountDownLatch(1);
	private static String url ="127.0.0.1:2181";

	
	public static void main(String[] args) {
		
		try {
			ZooKeeper zk = new ZooKeeper(url, 5000, new ConnectorZk_usage_simple());
			System.out.println("查看zk的状态"+zk.getState());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			connected.await();
		} catch (InterruptedException e) {
			System.out.println("zookeeper session established");
		}
	
	}
	@Override
	public void process(WatchedEvent event) {
		System.out.println("receive watched "+event);
		
		if(KeeperState.SyncConnected  == event.getState()){
			connected.countDown();
		}
		
	}

}
