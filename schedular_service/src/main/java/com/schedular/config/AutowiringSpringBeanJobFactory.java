package com.schedular.config;

/**
 * Description:
 * Created by guimu on 2018/3/8 上午9:24
 */
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.stereotype.Component;

@Component
public class AutowiringSpringBeanJobFactory extends AdaptableJobFactory {
    @Autowired
    private transient AutowireCapableBeanFactory capableBeanFactory;
    protected Object createJobInstance(TriggerFiredBundle bundle)
            throws Exception {
        // 调用父类的方法
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入,这属于Spring的技术,不清楚的可以查看Spring的API.
        capableBeanFactory.autowireBean(jobInstance);
        return jobInstance;
    }
}