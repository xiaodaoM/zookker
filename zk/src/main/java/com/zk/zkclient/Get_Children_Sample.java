package com.zk.zkclient;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;

// 获取知子节点
public class Get_Children_Sample {
	private static String url ="127.0.0.1:2181";
	private static String path ="/zk-book";
	
	public static void main(String[] args) throws Exception {
		ZkClient zk = new ZkClient(url, 5000);
		//监听..节点变化
		zk.subscribeChildChanges(path, new IZkChildListener() {
			
			@Override
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				// TODO Auto-generated method stub
				System.out.println(parentPath +" child changed currentChilds"+ currentChilds);
			}
		});

		
		zk.createPersistent(path);
		Thread.sleep(1000);
		
		System.out.println(zk.getChildren(path));
		zk.createPersistent(path+"/c1");
		Thread.sleep(1000);
		zk.delete(path+"/c1");
		Thread.sleep(1000);
		zk.delete(path);
		Thread.sleep(Integer.MAX_VALUE);

		
	}
}
