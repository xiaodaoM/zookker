package com.zk.zkclient;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
//获取节点的操作...'
public class GetData_Sample {
	private static String url ="127.0.0.1:2181";
	private static String path ="/zk-book";
	
	public static void main(String[] args) throws Exception {
		ZkClient zk = new ZkClient(url, 5000);
		
		zk.createEphemeral(path, "123");
		
		//监听事件 .
		zk.subscribeDataChanges(path, new IZkDataListener() {
			
			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
			System.out.println("Deleted:" + dataPath);
				
			}
			
			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("changed :" + dataPath + "data:"+ data);
				
			}
		});	
		
		System.out.println(zk.readData(path));
		Thread.sleep(1000);
		zk.writeData(path, "456");//更新
		Thread.sleep(1000);
		zk.delete(path);
		Thread.sleep(Integer.MAX_VALUE);
	
		
		
		
	}
}
