package com.zk.zkclient;

import org.I0Itec.zkclient.ZkClient;

//使用zkclient 创建一个 客户端
public class Create_Sesssion_Sample {

	
	public static void main(String[] args) {
		ZkClient zk = new ZkClient("127.0.0.1:2181", 5000);
		System.out.println("zk session established");
		
	}
}
