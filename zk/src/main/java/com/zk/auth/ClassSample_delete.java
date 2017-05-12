package com.zk.auth;

import java.io.IOException;

import org.apache.log4j.chainsaw.Main;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooDefs.Ids;

public class ClassSample_delete {
	private static String path1="/zk-auth-test";
	private static String path2="/zk-auth-test/c1";
	
	private static String url ="127.0.0.1:2181";
	
	public static void main(String[] args) throws Exception {
		ZooKeeper zk =new ZooKeeper(url, 500, null);
		
		zk.addAuthInfo("digest", "foo:true".getBytes());
		
		zk.create(path1, "node1".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.PERSISTENT);
		zk.create(path2, "children".getBytes(), Ids.CREATOR_ALL_ACL, CreateMode.EPHEMERAL);
		
		
		ZooKeeper zk1 =new ZooKeeper(url, 500, null);
		try {
			zk1.delete(path2, -1);
		} catch (Exception e) {
			System.out.println("删除path2 节点失败 ");
		}
		
		ZooKeeper zk2 =new ZooKeeper(url, 500, null);
		zk2.addAuthInfo("digest", "foo:true".getBytes());
		zk2.delete(path2, -1);
		System.out.println("zk2 删除path2 成功");
		
		//客户端 添加权限是个 这个节点的子节点 。所以删除子节点的是需要权限 。删除这个节点不需要权限。。。。
		ZooKeeper zk3 =new ZooKeeper(url, 500, null);
		zk3.delete(path1, -1);
		System.out.println("zk3 删除path1 成功");
		
		
	}
}
