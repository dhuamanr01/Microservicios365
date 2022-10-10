package com.ms365.middleware.zuulserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.ms365.middleware.zuulserver.filters.ErrorFilter;
import com.ms365.middleware.zuulserver.filters.PostFilter;
import com.ms365.middleware.zuulserver.filters.PreFilter;
import com.ms365.middleware.zuulserver.filters.RouteFilter;

@SpringBootApplication
@Configuration
@ComponentScan
@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
@EnableEurekaClient
@EnableDiscoveryClient
@EnableZuulProxy
public class ZuulServerApplication {
  @Bean
  public PreFilter preFilter() {
    return new PreFilter();
  }

  @Bean
  public PostFilter postFilter() {
    return new PostFilter();
  }

  @Bean
  public ErrorFilter errorFilter() {
    return new ErrorFilter();
  }

  @Bean
  public RouteFilter routeFilter() {
    return new RouteFilter();
  }

	public static void main(String[] args) {
		SpringApplication.run(ZuulServerApplication.class, args);
		ZuulServerApplication.displaySplash();
  }

  private static void displaySplash() {
    InputStream inputStream = null;
    BufferedReader bufferedReader = null;

    try {
      inputStream = new BufferedInputStream(ZuulServerApplication.class.getResourceAsStream("/splash.txt"));
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
