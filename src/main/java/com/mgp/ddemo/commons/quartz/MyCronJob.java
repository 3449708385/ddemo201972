package com.mgp.ddemo.commons.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class MyCronJob extends QuartzJobBean {



    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //System.out.println("任务执行了" + new Date());
        // indexController.testMail();
    }
}
