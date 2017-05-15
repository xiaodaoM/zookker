package com.zk.curator;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * jdk 中的 cyclicbarrier 就等所有线程就绪后在执行
 * 抢购系统可以用到。
 * @author Administrator
 *
 */
public class Recipes_CyclicBarrier {

	static CyclicBarrier barrier = new CyclicBarrier(3);
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(3);
		executor.submit(new Runner("1号 选手"));
		executor.submit(new Runner("2号 选手"));
		executor.submit(new Runner("3号 选手"));
		executor.shutdown();
	}
}
class Runner implements  Runnable {
	
	private String name;
	
	public Runner(String name) {
		super();
		this.name = name;
	}

	public void run() {
	
		System.out.println("装备好了。。。");
		try {
			Recipes_CyclicBarrier.barrier.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		} catch (BrokenBarrierException e) {
			// TODO Auto-generated catch block
		}
		
		System.out.println("起跑！");
		
	}
}