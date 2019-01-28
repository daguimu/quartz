package com.schedular.mapper;

import com.schedular.dto.JobDto;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @Description: job mapper
 * @Author: Guimu
 * @Create: 2019/01/27 14:17:38
 **/

@Mapper
public interface JobMapper {

    List<JobDto> getAllJobs();

    int disable(@Param("groupName") String groupName, @Param("jobname") String jobName);

    int insertJob(@Param("job") JobDto jobDto);
}
