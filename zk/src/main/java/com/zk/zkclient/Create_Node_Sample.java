package com.zk.zkclient;

import org.I0Itec.zkclient.ZkClient;

//递归创建父节店
public class Create_Node_Sample {

	private static String url ="127.0.0.1:2181";
	public static void main(String[] args) {
		ZkClient zk = new ZkClient(url, 5000);
//		zk.createPersistent("/zk-book/c1", true);
		//递归删除
		zk.deleteRecursive("/zk-book");
	}
}
