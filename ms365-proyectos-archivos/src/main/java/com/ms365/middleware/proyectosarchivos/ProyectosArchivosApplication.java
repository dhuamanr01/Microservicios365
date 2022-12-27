package com.ms365.middleware.proyectosarchivos;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients("com.ms365.middleware.proyectosarchivos.feign")
@EnableCircuitBreaker
//public class ProyectosArchivosApplication  extends SpringBootServletInitializer{
public class ProyectosArchivosApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProyectosArchivosApplication.class, args);
		ProyectosArchivosApplication.displaySplash();
  }
	
	/*@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ProyectosArchivosApplication.class);
    }*/

  private static void displaySplash() {
    InputStream inputStream = null;
    BufferedReader bufferedReader = null;

    try {
      inputStream = new BufferedInputStream(ProyectosArchivosApplication.class.getResourceAsStream("/splash.txt"));
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