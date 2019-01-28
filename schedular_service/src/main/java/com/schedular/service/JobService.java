package com.schedular.service;

import com.schedular.dto.JobDto;
import java.util.List;
import java.util.Optional;

/**
 * @Description: Job service
 * @Author: Guimu
 * @Create: 2019/01/27 15:00:21
 **/

public interface JobService {

    Optional<List<JobDto>> getAllJobs();

    Optional<Boolean> removeJob(String groupName, String jobName);

    Optional<Integer> insertOneJob(JobDto jobDto);
}
