package com.schedular.jobs;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class BaseJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        System.err.println(
            "Hello!  BaseJob is executing." + new Date() + "---->" + jobDetail.getJobDataMap()
                .get("url") + "groupName: " + jobDetail.getKey().getGroup() + "jobName:" + jobDetail
                .getKey().getName());
        /*
        //取得job详情
        JobDetail jobDetail = context.getJobDetail();
        // 取得job名称
        String jobName = jobDetail.getClass().getName();
        System.out.println("Name: " + jobDetail.getClass().getSimpleName());
        //取得job的类
        System.out.println("Job Class: " + jobDetail.getJobClass());
        //取得job开始时间
        System.out.println(jobName + " fired at " + context.getFireTime());
        System.out.println("Next fire time " + context.getNextFireTime());*/
    }

}
