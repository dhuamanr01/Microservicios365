package com.ms365.middleware.proyectos.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuartzJobListener implements JobListener {
  private static Logger logger = LoggerFactory.getLogger(QuartzJobListener.class);

  @Override
  public String getName() {
    return "QuartzJobListener";
  }

  @Override
  public void jobToBeExecuted(JobExecutionContext context) {
    logger.info("Job: {}, Trigger: {}", context.getJobDetail().getKey().getName(), context.getTrigger().getKey().getName());
  }

  @Override
  public void jobExecutionVetoed(JobExecutionContext context) {
    logger.info("Job: {}, Trigger: {}", context.getJobDetail().getKey().getName(), context.getTrigger().getKey().getName());
  }

  @Override
  public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
    logger.info("Job: {}, Trigger: {}", context.getJobDetail().getKey().getName(), context.getTrigger().getKey().getName());
  }

}
