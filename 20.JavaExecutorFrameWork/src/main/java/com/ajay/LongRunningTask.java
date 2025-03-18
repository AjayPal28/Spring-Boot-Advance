package com.ajay;

public class LongRunningTask implements Runnable{

	private String name;
	
	
	
	public LongRunningTask(String name) {
		super();
		this.name = name;
	}



	@Override
	public void run() {
		System.out.println("Starting task...." + Thread.currentThread().getName()+"\t"+name);
//		try {
//			Thread.sleep(4000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		System.out.println("Ending task...." + Thread.currentThread().getName()+"\t"+name);

	}

}
