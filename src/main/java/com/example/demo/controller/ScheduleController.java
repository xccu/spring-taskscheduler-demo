package com.example.demo.controller;

import com.example.demo.task.TaskRunnable;
import com.example.demo.vo.TaskVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;


/**
 * 这里只涉及任务的启动，停止，重启
 *
 */
@Slf4j
@RequestMapping("/schedule")
@RestController
public class ScheduleController {

    private static Map<Integer, ScheduledFuture> futures = new ConcurrentHashMap<>();

    //模拟数据库的任务配置数据
    private Map<Integer, TaskVO> taskVOMap = new HashMap(){{
        put(1, new TaskVO(1, "任务1", "0/5 * * * * *"));
        put(2, new TaskVO(2, "任务2", "0/6 * * * * *"));
        put(3, new TaskVO(3, "任务3", "0/7 * * * * *"));
        put(4, new TaskVO(4, "任务4", "0/8 * * * * *"));
        put(5, new TaskVO(5, "任务5", "0/9 * * * * *"));
    }};

    @Autowired
    private TaskScheduler taskScheduler;

    /**PostConstruct注解，程序启动时，启动所有任务*/
    @PostConstruct
    private void init() {
        for (Map.Entry<Integer, TaskVO> entry : taskVOMap.entrySet()) {
            TaskVO taskVO = entry.getValue();
            ScheduledFuture<?> future = taskScheduler.schedule(
                    new TaskRunnable(taskVO.getBussinessParam()),
                    new CronTrigger(taskVO.getCron())
            );
            futures.put(entry.getKey(), future);
        }
    }

    /**
     * 启动计划任务
     * http://localhost:8080/schedule/startTask?taskId=1
     * @param taskId
     * @return
     */
    @RequestMapping("/startTask")
    public String startTask(Integer taskId) {
        ScheduledFuture<?> future = futures.get(taskId);
        if (future == null) {
            TaskVO taskVO = taskVOMap.get(taskId);
            future = taskScheduler.schedule(
                    new TaskRunnable(taskVO.getBussinessParam()),
                    new CronTrigger(taskVO.getCron())
            );
            futures.put(taskId, future);
            log.info("启动任务:"+taskId);
        } else {
            log.info("任务已启动");
        }
        return "startTask Success:"+taskId;
    }

    /**
     * 结束计划任务
     * http://localhost:8080/schedule/stopTask?taskId=1
     * @param taskId
     * @return
     */
    @RequestMapping("/stopTask")
    public String stopTask(Integer taskId) {
        ScheduledFuture<?> future = futures.get(taskId);
        if (future != null) {
            future.cancel(true);
            futures.remove(taskId);
            log.info("停止任务:"+taskId);
        } else {
            log.info("任务已停止");
        }
        return "stopTask Success:"+taskId;
    }

    /**
     * 重启计划任务
     * http://localhost:8080/schedule/restartTask?taskId=1
     * @param taskId
     * @return
     */
    @RequestMapping("/restartTask")
    public String restartTask(Integer taskId) {
        ScheduledFuture<?> future = futures.get(taskId);
        if (future == null) {
            TaskVO taskVO = taskVOMap.get(taskId);
            future = taskScheduler.schedule(
                    new TaskRunnable(taskVO.getBussinessParam()),
                    new CronTrigger(taskVO.getCron())
            );
            futures.put(taskId, future);
            log.info("创建任务:"+taskId);
        } else {
            future.cancel(true);
            TaskVO taskVO = taskVOMap.get(taskId);
            future = taskScheduler.schedule(
                    new TaskRunnable(taskVO.getBussinessParam()),
                    new CronTrigger(taskVO.getCron())
            );
            futures.put(taskId, future);
            log.info("重启任务:"+taskId);
        }
        return "restartTask Success:"+taskId;
    }
}
