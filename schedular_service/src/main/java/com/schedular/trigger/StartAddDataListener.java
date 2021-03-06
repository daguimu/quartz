package com.schedular.trigger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Created by guimu on 2018/3/8 下午3:08
 */
@Service
public class StartAddDataListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private SchedulerFactoryBean factory;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null)//root application context 没有parent，他就是老大.
        {
            //需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
            System.out.println("load finished");
//            try {
//                Scheduler scheduler = factory.getScheduler();
//                Set<JobKey> jobKeys = scheduler.getJobKeys(GroupMatcher.anyJobGroup());
//                List<JobKey> jobKeyList = new ArrayList<>();
//                for (SchedularTaskServiceImpl schedularTaskModel : SchedularTaskServiceImpl.currentSchedularTaskModels) {
//                    jobKeyList.add(schedularTaskModel.getCronTrigger().getJobKey());
//                }
//                jobKeys.removeAll(jobKeyList);
//                scheduler.deleteJobs(new ArrayList<>(jobKeys));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }
    }
}



