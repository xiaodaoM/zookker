package com.zk.update;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
/**
 * 同步更新 zk data 数据
 * @功能:TODO
 * @作者:zqp
 * @版本:1.0
 * @修改:
 */
public class SetData_Sync_Usage implements Watcher{
	private static String url ="127.0.0.1:2181";
	private static CountDownLatch  connected = new CountDownLatch(1);
	private static String path = "/zk-book";
	private  static ZooKeeper zk = null;
	@Override
	public void process(WatchedEvent event) {
	if(event.getState() == KeeperState.SyncConnected){
		if(event.getType() == EventType.None && null == event.getPath()){
			connected.countDown();
		}
	}
		
	}
	
	public static void main(String[] args) throws Exception {
		zk = new ZooKeeper(url, 5000, new SetData_Sync_Usage());
		
		zk.create(path, "123".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
		
		byte[] data = zk.getData(path, true, null);
		
		Stat stat = zk.setData(path, "456".getBytes(), -1);
		System.out.println("czxid = "+ stat.getCzxid()+ 	","
	  				+ " mzxid = "+ stat.getMzxid()+","
	  						+ " version ="+ stat.getVersion());
		Stat stat2 = zk.setData(path, "456".getBytes(), stat.getVersion());
		System.out.println("czxid = "+ stat2.getCzxid()+ 	","
				+ " mzxid = "+ stat2.getMzxid()+","
				+ " version ="+ stat2.getVersion());
		
		try {
			zk.setData(path, "456".getBytes(),stat.getVersion());
		} catch (KeeperException e) {
			System.out.println("error "+ e.code() +", "+ e.getMessage());
		}
		
		
		Thread.sleep(Integer.MAX_VALUE);
		
		
	}
	
	
	
}
