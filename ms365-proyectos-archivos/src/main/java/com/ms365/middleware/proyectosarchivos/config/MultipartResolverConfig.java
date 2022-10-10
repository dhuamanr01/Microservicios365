package com.ms365.middleware.proyectosarchivos.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.ms365.middleware.proyectosarchivos.common.constants.Constants;

@Configuration
public class MultipartResolverConfig {
  private static Logger logger = LoggerFactory.getLogger(MultipartResolverConfig.class);

  @Bean
  public CommonsMultipartResolver multipartResolver() {
    logger.info(Constants.Logger.Method.INITIALIZE);

    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
    //multipartResolver.setMaxUploadSize(1000000);

    logger.info(Constants.Logger.Method.FINALIZE);
    return multipartResolver;
  }
}
