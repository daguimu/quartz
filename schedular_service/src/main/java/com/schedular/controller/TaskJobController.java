package com.schedular.controller;

import com.schedular.dto.JobDto;
import com.schedular.service.TaskManagerService;
import org.springframework.beans.factory.annotation.Autowired;
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
        jobDto.setCornExpression(jobDto.getCornExpression().replaceAll("'",""));
        return taskManagerService.addJob(jobDto) ? "success" : "failed";
    }
}
