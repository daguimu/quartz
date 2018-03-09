package com.schedular.model;

import org.quartz.JobDetail;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.util.ArrayList;
import java.util.List;

import static org.quartz.JobBuilder.newJob;

/**
 * Description:
 * Created by guimu on 2018/3/8 上午11:00
 */
public class SchedularTaskModel {
    public static List<SchedularTaskModel> currentSchedularTaskModels = new ArrayList<>();
    private JobDetail jobDetail;
    private CronTriggerImpl cronTrigger;

    public static SchedularTaskModel getTaskModel(Class jobClass, String description, String jobName, String cornExpression) {
        JobDetail jobDetail = newJob(jobClass)
                .withDescription(description)
                .withIdentity(jobName, jobName + "jobGroup")
                .storeDurably()
                .build();
        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setName(jobName + "TriggerName");
        cronTrigger.setGroup("TriggerGroupName");
        try {
            cronTrigger.setCronExpression(cornExpression);
        } catch (Exception e) {
            e.printStackTrace();
        }
        cronTrigger.setJobKey(jobDetail.getKey());
        SchedularTaskModel schedularTaskModel = new SchedularTaskModel();
        schedularTaskModel.setCronTrigger(cronTrigger);
        schedularTaskModel.setJobDetail(jobDetail);
        return schedularTaskModel;
    }

    private SchedularTaskModel() {

    }

    public JobDetail getJobDetail() {
        return this.jobDetail;
    }

    public CronTriggerImpl getCronTrigger() {
        return this.cronTrigger;
    }

    public void setJobDetail(JobDetail jobDetail) {
        this.jobDetail = jobDetail;
    }

    public void setCronTrigger(CronTriggerImpl cronTrigger) {
        this.cronTrigger = cronTrigger;
    }


}
