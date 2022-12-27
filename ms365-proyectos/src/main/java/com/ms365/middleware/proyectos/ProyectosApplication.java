package com.ms365.middleware.proyectos;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ms365.middleware.proyectos.scheduler.ProyectoJob;
import com.ms365.middleware.proyectos.scheduler.QuartzJobListener;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients("com.ms365.middleware.proyectos.feign")
@EnableCircuitBreaker
@EnableScheduling

//public class ProyectosApplication extends SpringBootServletInitializer {
public class ProyectosApplication  {	
	/*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ProyectosApplication.class);
    }*/

	
	
  @Bean(name = "ProyectoJob")
  public JobDetail proyectoJob() {
    return JobBuilder.newJob(ProyectoJob.class)
                     .withIdentity("ProyectoJob", "ms365")
                     .withDescription("Proyecto Job")
                     .storeDurably()
                     .build();
  }

  @Bean
  public Trigger proyectoTrigger(@Qualifier("ProyectoJob") JobDetail job) {
    return TriggerBuilder.newTrigger().forJob(job)
                         .withIdentity("ProyectoTrigger", "ms365")
                         .withDescription("Proyecto Trigger")
                         //.withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 * 1/1 * ? *")) // 2 Minutos
                         .withSchedule(CronScheduleBuilder.cronSchedule("0 0 4 1/1 * ? *")) // Todos los dias a las 4 am
                         .startNow()
                         .build();
  }

  @Bean(name = "QuartzJobListener")
  public JobListener quartzListener() {
    return new QuartzJobListener();
  }

  @Bean
  public SchedulerFactoryBeanCustomizer schedulerConfiguration(@Qualifier("QuartzJobListener") JobListener listener) {
    return bean -> {
      bean.setGlobalJobListeners(listener);
    };
  }

	public static void main(String[] args) {
		SpringApplication.run(ProyectosApplication.class, args);
		ProyectosApplication.displaySplash();
//		ProyectosApplication.testZuul();
  }

  private static void displaySplash() {
    InputStream inputStream = null;
    BufferedReader bufferedReader = null;

    try {
      inputStream = new BufferedInputStream(ProyectosApplication.class.getResourceAsStream("/splash.txt"));
      bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

      String line = null;
      System.out.println();
      while ((line = bufferedReader.readLine()) != null) {
        if (!line.startsWith("#")) System.out.println(line);
      }
    }
    catch (Exception ex) {}
    finally {
      if (bufferedReader != null) {
        try { bufferedReader.close(); }
        catch (IOException ex) {}
        bufferedReader = null;
      }

      if (inputStream != null) {
        try { inputStream.close(); }
        catch (IOException ex) {}
        inputStream = null;
      }
    }
  }
  

}