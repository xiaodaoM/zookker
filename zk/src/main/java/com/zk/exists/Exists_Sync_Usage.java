package com.zk.exists;

import java.util.concurrent.CountDownLatch;

import org.apache.log4j.chainsaw.Main;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

/**
 * 同步检测 节点是否存在
 * @author Administrator
 *
 */
public class Exists_Sync_Usage implements Watcher{
	private static String url ="127.0.0.1:2181";
	private static CountDownLatch  connected = new CountDownLatch(1);
	private static String path = "/zk-book";
	private  static ZooKeeper zk = null;
	public void process(WatchedEvent event) {
		if(KeeperState.SyncConnected == event.getState()){
			try {
				if(event.getPath() == null && EventType.None == event.getType()){
					connected.countDown();
				}else if(EventType.NodeCreated == event.getType()){
					System.out.println("node("+ event.getPath() +") created");
					zk.exists(event.getPath(), true);
				}else if(EventType.NodeDeleted == event.getType()){
					System.out.println("node("+ event.getPath() +") deleted");
					zk.exists(event.getPath(), true);
					
				}else if(EventType.NodeDataChanged == event.getType()){
					System.out.println("node("+ event.getPath() +") changed");
					zk.exists(event.getPath(), true);
					
				}
			} catch (KeeperException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public static void main(String[] args) throws Exception {
		zk = new ZooKeeper(url, 500, new Exists_Sync_Usage() );
		connected.await();
		
		zk.exists( path, true );
    	
    	zk.create( path, "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT );
    	
    	zk.setData( path, "123".getBytes(), -1 );
    	
    	zk.create( path+"/c1", "".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT );
    	
    	zk.delete( path+"/c1", -1 );
    	
    	zk.delete( path, -1 );
    	
        Thread.sleep( Integer.MAX_VALUE );
		
	}

}
