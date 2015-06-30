package com.undergrowth.bio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 线程池服务
 * @author Administrator
 *
 */
public class PseudoAsyncExecutorService {
	private ExecutorService executorService;
	
	public PseudoAsyncExecutorService(){
		//创建一个根据负载变化而变化的线程池
		executorService=Executors.newCachedThreadPool();
	}
	
	public void execute(Runnable task){
		System.out.println("已将任务提交到线程池");
		executorService.submit(task);
	}
}
