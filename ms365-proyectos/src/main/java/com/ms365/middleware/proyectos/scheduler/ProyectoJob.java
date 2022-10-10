package com.ms365.middleware.proyectos.scheduler;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ms365.middleware.proyectos.common.constants.Constants;
import com.ms365.middleware.proyectos.service.ProyectoService;

@DisallowConcurrentExecution
public class ProyectoJob implements Job {
  private static Logger logger = LoggerFactory.getLogger(ProyectoJob.class);

  @Autowired
  private ProyectoService proyectoService;

  @Override
  public void execute(JobExecutionContext context) throws JobExecutionException {
    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      logger.info("  registros actualizados: " + this.proyectoService.disableExpiration());
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }

    logger.trace(Constants.Logger.Method.FINALIZE);
  }
}
