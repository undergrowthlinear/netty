package com.undergrowth.bio;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * �̳߳ط���
 * @author Administrator
 *
 */
public class PseudoAsyncExecutorService {
	private ExecutorService executorService;
	
	public PseudoAsyncExecutorService(){
		//����һ�����ݸ��ر仯���仯���̳߳�
		executorService=Executors.newCachedThreadPool();
	}
	
	public void execute(Runnable task){
		System.out.println("�ѽ������ύ���̳߳�");
		executorService.submit(task);
	}
}
