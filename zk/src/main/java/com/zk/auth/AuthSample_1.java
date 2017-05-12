package com.zk.auth;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
/**
 * 权限...
 * @功能:TODO
 * @作者:zqp
 * @版本:1.0
 * @修改:
 */
public class AuthSample_1 {
	
	private static String path="/zk-auth-test";
	private static String url ="127.0.0.1:2181";
	
	public static void main(String[] args) throws Exception {
		ZooKeeper	 zk = new ZooKeeper(url, 5000, null);
		zk.addAuthInfo("digest", "foo:true".getBytes());
		zk.create(path, "init".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
		//zk1
		ZooKeeper	 zk1 = new ZooKeeper(url, 5000, null);
		zk1.addAuthInfo("digest", "foo:true".getBytes());
		System.out.println(new String(zk1.getData(path, false, null)));
		//zk2
//		ZooKeeper	 zk2 = new ZooKeeper(url, 5000, null);
//		zk2.addAuthInfo("digest", "foo:false".getBytes());
//		System.out.println(new String(zk2.getData(path, false, null)));
//		
//		ZooKeeper	 zk3 = new ZooKeeper(url, 5000, null);
//		System.out.println(new String(zk3.getData(path, false, null)));
//		
		ZooKeeper	 zk4 = new ZooKeeper(url, 5000, null);
		zk4.addAuthInfo("ip", "foo:true".getBytes());
		System.out.println(new String(zk4.getData(path, false, null)));
		
	}

}
