package com.zk.curator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;

/**
 * 
 * @author Administrator
 * 典型的时间搓  并发错误
 */
public class Recipes_NoLock {

	
	public static void main(String[] args) {
	 final	CountDownLatch countDown =new CountDownLatch(1);
		
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					//主线程开始就启动 让此在该方法上阻塞 等待其他线程的完成
					try {
						countDown.await();
					} catch (InterruptedException e) {
					}
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd | SSS");
					String format = sdf.format(new Date());
					System.out.println("产生的订单编号为：" + format );
				}
			}).start();;
		}
		
		countDown.countDown();	
	
	}
}
