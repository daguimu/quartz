package com.schedular.serviceimpl;

import com.schedular.dto.JobDto;
import com.schedular.jobs.BaseJob;
import com.schedular.model.SchedularTaskModel;
import com.schedular.service.JobService;
import com.schedular.service.TaskInitService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: 初始化任务接口实现类
 * @Author: Guimu
 * @Create: 2019/01/27 15:30:01
 **/

@Service
public class TaskInitServiceImpl implements TaskInitService {

    @Autowired
    private JobService jobService;

    @Override
    public SchedularTaskModel[] initScheduLar() {
        Optional<List<JobDto>> optional = jobService.getAllJobs();
        return optional.isPresent() ? optional.get().stream().map(this::convenTer).collect(
            Collectors.toList()).toArray(new SchedularTaskModel[optional.get().size()])
            : new SchedularTaskModel[0];
    }

    private SchedularTaskModel convenTer(JobDto jobDto) {
        return SchedularTaskModel
            .getTaskModel(BaseJob.class, jobDto);
    }
}
