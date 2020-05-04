package gov.epa.cef.web.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import gov.epa.cef.web.service.task.SccUpdateTask;

@Configuration
@EnableScheduling
public class SchedulerConfig implements SchedulingConfigurer {

    @Autowired
    private CefConfig cefConfig;
    
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskScheduler());
        taskRegistrar.addCronTask(sccUpdateTask(), this.cefConfig.getSccUpdateTaskCron());
    }

    @Bean(destroyMethod="shutdown")
    public Executor taskScheduler() {
        return Executors.newScheduledThreadPool(10);
    }

    @Bean
    public SccUpdateTask sccUpdateTask() {
        return new SccUpdateTask();
    }

}
