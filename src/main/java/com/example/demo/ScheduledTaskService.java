package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

//计划任务执行类
@Slf4j
@Service  //@Service注解为Service类并注册到spring容器中
public class ScheduledTaskService {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	/**
	 * 使用cron属性按照指定的时间执行，cron是Unix和Linux系统下的定时任务
	 * 格式[秒] [分] [小时] [日] [月] [周] [年]
	 * 每天11点28分执行
	 */
	//@Scheduled(cron = "0 28 11 * * *")
	public void cornTimeRun() {
		log.info(dateFormat.format(new Date()) + "\tcron-Time：\t在指定时间执行" );
	}

	/**
	 * 使用cron属性按照指定的时间执行，cron是Unix和Linux系统下的定时任务
	 * 格式[秒] [分] [小时] [日] [月] [周] [年]
	 * 每隔5秒执行一次
	 */
	//@Scheduled(cron = "*/5 * * * * *")
	public void cornSpanRun() {
		log.info(dateFormat.format(new Date()) + "\tcron-Span：\t每隔五秒执行一次");
	}

	/**
	 * 使用fixedDelay属性每隔固定时间（单位：毫秒）异步执行
	 * 每隔5秒执行一次,上一次执行完毕时间点之后多长时间再执行
	 */
	//@Scheduled(fixedDelay = 5000)
	public void fixedDelayRun() {
		log.info(dateFormat.format(new Date()) + "\tfixedDelay：\t每隔五秒执行一次");
	}

	/**
	 * 与fixedDelay相同，只是使用字符串的形式。并支持占位符。如：
	 * @Scheduled(fixedDelayString = "5000")
	 * 占位符的使用(配置文件中有配置：time.fixedDelay=5000)
	 * @Scheduled(fixedDelayString="${time.fixedDelay}")
	 */
	//@Scheduled(fixedDelayString = "${time.fixedDelay}")
	public void FixedDelayStringRun() {
		log.info(dateFormat.format(new Date()) + "\tFixedDelayStr：\t读取配置文件执行");
	}

	/**
	 * 通过@Scheduled声明该方法是计划任务，使用fixedRate属性每隔固定时间（单位：毫秒）异步执行
	 * 每隔1秒执行一次
	 */
	//@Scheduled(fixedRate = 1000,zone = "Asia/Shanghai")
	public void fixedRateRun() {
		log.info(dateFormat.format(new Date()) + "\tfixedRate：\t每隔一秒执行一次");
	}

	/**
	 * 与fixedRate相同，只是使用字符串的形式。并支持占位符。如：
	 * @Scheduled(fixedDelayString = "1000")
	 * 占位符的使用(配置文件中有配置：time.fixedRate=1000)：
	 * @Scheduled(fixedRateString="${time.fixedRate}")
	 */
	//@Scheduled(fixedRateString = "1000")
	public void fixedRateStringRun() {
		log.info(dateFormat.format(new Date()) + "\tfixedRateStr：\t每隔一秒执行一次");
	}

	/**
	 * 使用initialDelay属性第一次延迟多长时间后再执行
	 * 第一次延迟5秒后执行，之后按fixedRate的规则每1秒执行一次
	 */
	//@Scheduled(initialDelay=5000, fixedRate=1000)
	public void initialDelayRun() {
		log.info(dateFormat.format(new Date()) + "\tinitialDelay：\t延迟五秒执行");
	}

	/**
	 * 与initialDelay相同，只是使用字符串的形式。并支持占位符。如：
	 * @Scheduled(fixedDelayString = "5000",fixedRate=1000)
	 * 占位符的使用(配置文件中有配置：time.initialDelay=4000)：
	 * @Scheduled(fixedDelayString="${time.initialDelay}")
	 */
	//@Scheduled(initialDelayString="${time.initialDelay}", fixedRate=1000)
	public void initialDelayStringRun() {
		log.info(dateFormat.format(new Date()) + "\tinitialDelayStr：\t延迟五秒执行");
	}


	/**
	 *后一次执行的时间是fixedDelay的时间加阻塞时间
	 */
	//@Scheduled(fixedDelay=2000)
	public void fixedDelayDiff(){
		try
		{
			Thread.currentThread().sleep(1000);
		}
		catch(Exception e){}
		System.out.println("fixedDelay:\t"+new Date());
	}

	//@Scheduled(fixedRate=2000)
	public void fixedRateDiff(){
		try
		{
			Thread.currentThread().sleep(1000);
		}
		catch(Exception e){}
		System.out.println("fixedRate:\t"+new Date());
	}
}
