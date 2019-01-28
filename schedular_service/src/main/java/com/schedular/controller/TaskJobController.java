package com.schedular.controller;

import com.schedular.dto.JobDto;
import com.schedular.service.TaskManagerService;
import org.quartz.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 定时任务controller
 * @Author: Guimu
 * @Create: 2019/01/27 15:34:34
 **/

@RestController
public class TaskJobController {

    @Autowired
    private TaskManagerService taskManagerService;

    @RequestMapping(value = "add")
    public String addJob(@RequestBody JobDto jobDto) {
        jobDto.setCornExpression(jobDto.getCornExpression().replaceAll("'", ""));
        return deal(taskManagerService.addJob(jobDto));
    }

    @RequestMapping(value = "remove/{groupname}/{jobname}")
    public String removeJob(@PathVariable("groupname") String group,
        @PathVariable("jobname") String jobName) {
        boolean flag = taskManagerService.remove(group, jobName);
        return deal(flag);
    }

    private String deal(boolean flag) {
        return flag ? "success" : "failed";
    }
}
