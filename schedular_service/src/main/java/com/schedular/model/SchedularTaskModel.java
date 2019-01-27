package com.schedular.model;

import com.schedular.dto.JobDto;
import lombok.Data;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.impl.triggers.CronTriggerImpl;

import java.util.ArrayList;
import java.util.List;

import static org.quartz.JobBuilder.newJob;

/**
 * Description: Created by guimu on 2018/3/8 上午11:00
 */
@Data
public class SchedularTaskModel {


    public static List<SchedularTaskModel> currentSchedularTaskModels = new ArrayList<>();
    private JobDetail jobDetail;
    private CronTriggerImpl cronTrigger;

    public static SchedularTaskModel getTaskModel(Class<? extends Job> jobClass, JobDto jobDto) {
        JobDetail jobDetail = newJob(jobClass)
            .withDescription(jobDto.getDescription())
            .withIdentity(jobDto.getJobName(), jobDto.getGroupName())
            .usingJobData("url", jobDto.getParams())
            .storeDurably()
            .build();

        CronTriggerImpl cronTrigger = new CronTriggerImpl();
        cronTrigger.setName(jobDto.getJobName());
        cronTrigger.setGroup(jobDto.getGroupName());
        try {
            cronTrigger.setCronExpression(jobDto.getCornExpression());
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
}
