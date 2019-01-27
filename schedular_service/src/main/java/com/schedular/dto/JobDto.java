package com.schedular.dto;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Description: job dto
 * @Author: Guimu
 * @Create: 2019/01/27 14:19:05
 **/

@Data
public class JobDto {

    private int id;
    private String jobName;
    private String params;
    private String groupName;
    private String description;
    private String cornExpression;
    private int isEnable;


}
