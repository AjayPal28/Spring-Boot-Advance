package com.ajay;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JavaExecutorFrameWorkApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaExecutorFrameWorkApplication.class, args);
		run();
	}

	public static void run() {
//		System.out.println("Starting Main Thread :" + Thread.currentThread().getName());
//		ThreadPoolExecutor threadPoolExecutors = new ThreadPoolExecutor(50, 100, 2, TimeUnit.SECONDS,
//				new ArrayBlockingQueue<>(10), (runnable, executor) -> {
//					executor.submit(runnable);
//				});
//
//		for (int i = 1; i < 100; i++) {
//			threadPoolExecutors.submit(new LongRunningTask("" + i));
//		}
//		System.out.println("Ending Main Thread :" + Thread.currentThread().getName());
//
//		threadPoolExecutors.close();

		ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(2,
				new ThreadFactory() {

					@Override
					public Thread newThread(Runnable r) {

						return new Thread(r, "thread " + System.currentTimeMillis());
					}
				});
		
		scheduledThreadPoolExecutor.schedule(new LongRunningTask("Scheduled Task"), 4, TimeUnit.SECONDS);

	}
}
