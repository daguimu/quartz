package com.schedular.serviceimpl;

import com.schedular.mapper.JobMapper;
import com.schedular.dto.JobDto;
import com.schedular.service.JobService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Description: job service 接口实现类
 * @Author: Guimu
 * @Create: 2019/01/27 15:03:11
 **/

@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private JobMapper jobMapper;

    @Override
    public Optional<List<JobDto>> getAllJobs() {
        return Optional.ofNullable(jobMapper.getAllJobs());
    }

    /**
    *@Author: Guimu
    *@Description: 将该job添加到数据库
    *@Param: [jobDto]
    *@Return: java.util.Optional<java.lang.Integer>
    *@Date: 2019-01-27 16:01
    */
    @Override
    public Optional<Integer> insertOneJob(JobDto jobDto) {
        return Optional.of(jobMapper.insertJob(jobDto));
    }
}
