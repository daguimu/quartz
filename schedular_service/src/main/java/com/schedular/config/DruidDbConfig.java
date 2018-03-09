package com.schedular.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;


@Component
public class DruidDbConfig {

    private static Logger logger = LoggerFactory.getLogger(DruidDbConfig.class);



    @Value("${org.quartz.dataSource.myDS.driver}")
    private String myDSDriver;

    @Value("${org.quartz.dataSource.myDS.URL}")
    private String myDSURL;

    @Value("${org.quartz.dataSource.myDS.user}")
    private String myDSUser;

    @Value("${org.quartz.dataSource.myDS.password}")
    private String myDSPassword;


    @Bean   //声明其为Bean实例
    @Primary  //在同样的DataSource中，首先使用被标注的DataSource
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        datasource.setUrl(this.myDSURL);
        datasource.setUsername(this.myDSUser);
        datasource.setPassword(this.myDSPassword);
        datasource.setDriverClassName(this.myDSDriver);
        return datasource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}

