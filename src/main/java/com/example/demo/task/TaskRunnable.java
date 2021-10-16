package com.example.demo.task;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TaskRunnable implements Runnable {
    private String param;
    public TaskRunnable(String param) {
        this.param = param;
    }

    @Override
    public void run() {
        log.info("TaskRunnable:{}", param);
    }
}
