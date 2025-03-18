package com.ajay;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyScheduler {
  @Scheduled(fixedRate = 200) // NOT concurrent
  @Async("jobExecutor")
  void logYou() {
      log.info("Scheduler2 started... {}", Thread.currentThread().getName());
      try {
          Thread.sleep(2000);
      } catch (InterruptedException e) {
          throw new RuntimeException(e);
      }
  }
}
