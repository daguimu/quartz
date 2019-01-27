package com.schedular.service;

import com.schedular.dto.JobDto;

/**
 * @Description: 任务管理接口
 * @Author: Guimu
 * @Create: 2019/01/27 15:36:48
 **/

public interface TaskManagerService {

    boolean addJob(JobDto jobDto);
}
