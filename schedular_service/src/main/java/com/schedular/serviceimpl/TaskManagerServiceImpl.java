package com.schedular.serviceimpl;

import com.schedular.dto.JobDto;
import com.schedular.jobs.BaseJob;
import com.schedular.model.SchedularTaskModel;
import com.schedular.service.JobService;
import com.schedular.service.TaskManagerService;
import java.util.Optional;
import org.quartz.JobKey;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description: 定时任务管理接口实现类
 * @Author: Guimu
 * @Create: 2019/01/27 15:37:42
 **/

@Service
public class TaskManagerServiceImpl implements TaskManagerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JobService jobService;
    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    @Transactional
    public boolean addJob(JobDto jobDto) {
        try {
            JobKey jobKey = new JobKey(jobDto.getJobName(), jobDto.getGroupName());
            boolean isExisted = schedulerFactoryBean.getScheduler().checkExists(jobKey);
            if (isExisted) {
                logger.error("添加定时任务:{}失败,该job已存在", jobDto.getJobName());
                return false;
            }
            SchedularTaskModel taskModel = SchedularTaskModel
                .getTaskModel(BaseJob.class, jobDto);
            jobService.insertOneJob(jobDto);
            schedulerFactoryBean.getScheduler()
                .scheduleJob(taskModel.getJobDetail(), taskModel.getCronTrigger());
        } catch (SchedulerException e) {
            logger.error("添加定时任务:{}失败", jobDto.getJobName(), e);
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public boolean remove(String groupName, String jobName) {
        JobKey jobKey = new JobKey(jobName, groupName);
        boolean flag;
        try {
            boolean isExisted = schedulerFactoryBean.getScheduler().checkExists(jobKey);
            if (!isExisted) {
                logger.info("组名:{},任务名:{} 的任务不存在,移除失败", groupName, jobName);
                return false;
            }
            Optional<Boolean> optional = jobService.removeJob(groupName, jobName);
            flag = optional.isPresent() ? optional.get() : false;
            flag = flag && schedulerFactoryBean.getScheduler().deleteJob(jobKey);
        } catch (SchedulerException e) {
            logger.error("组名:{},任务名:{} 的任务移除失败，发生异常", groupName, jobName, e);
            return false;
        }
        return flag;
    }
}
