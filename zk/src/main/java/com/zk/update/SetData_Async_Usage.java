package com.zk.update;

import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.data.Stat;
/**
 * 异步修改 data
 * @功能:TODO
 * @作者:zqp
 * @日期:2017年5月11日
 * @修改:
 */
public class SetData_Async_Usage implements Watcher{
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
		
		zk.setData(path, "456".getBytes(), -1, new IDataCallBack(), null);
		byte[] r1 = zk.getData(path, true, null);
		System.out.println(new String(r1));
		
		Thread.sleep(Integer.MAX_VALUE);
		
		
	}
}


class IDataCallBack implements AsyncCallback.StatCallback{

	@Override
	public void processResult(int rc, String path, Object ctx, Stat stat) {
		if(rc == 0){
			System.out.println("set async success");
		}
		
	}
	
}
