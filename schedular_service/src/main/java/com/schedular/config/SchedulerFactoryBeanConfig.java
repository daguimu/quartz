package com.schedular.config;

import com.schedular.jobs.Job1;
import com.schedular.jobs.NewJob;
import com.schedular.model.SchedularTaskModel;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;


@Configuration
public class SchedulerFactoryBeanConfig {

    @Value("${quartz.scheduler.instanceName}")
    private String quartzInstanceName;

    @Value("${org.quartz.dataSource.myDS.driver}")
    private String myDSDriver;

    @Value("${org.quartz.dataSource.myDS.URL}")
    private String myDSURL;

    @Value("${org.quartz.dataSource.myDS.user}")
    private String myDSUser;

    @Value("${org.quartz.dataSource.myDS.password}")
    private String myDSPassword;

    @Value("${org.quartz.dataSource.myDS.maxConnections}")
    private String myDSMaxConnections;

    @Autowired
    DataSource dataSource;

    /**
     * 定时任务集群配置
     * 设置属性
     *
     * @return
     * @throws IOException
     */
    private Properties quartzProperties() throws IOException {
        Properties prop = new Properties();
        prop.put("quartz.scheduler.instanceName", quartzInstanceName);
        prop.put("org.quartz.scheduler.instanceId", "AUTO");
        prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
        prop.put("org.quartz.scheduler.jmx.export", "true");

        prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
        prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
        prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
        //prop.put("org.quartz.jobStore.tablePrefix", "");
        prop.put("org.quartz.jobStore.isClustered", "true");

        prop.put("org.quartz.jobStore.clusterCheckinInterval", "20000");
        prop.put("org.quartz.jobStore.dataSource", "myDS");
        prop.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");
        prop.put("org.quartz.jobStore.misfireThreshold", "120000");
        prop.put("org.quartz.jobStore.txIsolationLevelSerializable", "true");
        prop.put("org.quartz.jobStore.selectWithLockSQL", "SELECT * FROM {0}LOCKS WHERE LOCK_NAME = ? FOR UPDATE");

        prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "10");
        prop.put("org.quartz.threadPool.threadPriority", "5");
        prop.put("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread", "true");

        prop.put("org.quartz.dataSource.myDS.driver", myDSDriver);
        prop.put("org.quartz.dataSource.myDS.URL", myDSURL);
        prop.put("org.quartz.dataSource.myDS.user", myDSUser);
        prop.put("org.quartz.dataSource.myDS.password", myDSPassword);
        prop.put("org.quartz.dataSource.myDS.maxConnections", myDSMaxConnections);

        prop.put("org.quartz.plugin.triggHistory.class", "org.quartz.plugins.history.LoggingJobHistoryPlugin");
        prop.put("org.quartz.plugin.shutdownhook.class", "org.quartz.plugins.management.ShutdownHookPlugin");
        prop.put("org.quartz.plugin.shutdownhook.cleanShutdown", "true");
        return prop;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(AutowiringSpringBeanJobFactory jobFactory) throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJobFactory(jobFactory);
        factory.setSchedulerName("SchedularService");
        factory.setApplicationContextSchedulerContextKey("applicationContextKey");
        factory.setOverwriteExistingJobs(true);
        factory.setAutoStartup(true);
        factory.setQuartzProperties(quartzProperties());
        SchedularTaskModel schedularTaskModel = SchedularTaskModel
                .getTaskModel(NewJob.class,
                        "mydescription", "newjobName4", "*/3 * * * * ?"
                );
        SchedularTaskModel schedularTaskModel1 = SchedularTaskModel
                .getTaskModel(Job1.class,
                        "mydescription", "newjobName", "*/5 * * * * ?"
                );
        this.SetSchedulerFactoryBeanTask(factory, schedularTaskModel1);

        return factory;
    }


    private void SetSchedulerFactoryBeanTask(SchedulerFactoryBean factoryBean, SchedularTaskModel... schedularTaskModels) {
        SchedularTaskModel.currentSchedularTaskModels= Arrays.asList(schedularTaskModels);
        Trigger[] triggers = new Trigger[schedularTaskModels.length];
        JobDetail[] jobDetails = new JobDetail[schedularTaskModels.length];
        for (int i = 0; i < schedularTaskModels.length; i++) {
            triggers[i] = schedularTaskModels[i].getCronTrigger();
            jobDetails[i] = schedularTaskModels[i].getJobDetail();
        }

        factoryBean.setTriggers(triggers);
        factoryBean.setJobDetails(jobDetails);
    }
}
