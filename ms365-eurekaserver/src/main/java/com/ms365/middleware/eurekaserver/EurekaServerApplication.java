package com.ms365.middleware.eurekaserver;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@EnableEurekaServer
@EnableAutoConfiguration
public class EurekaServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(EurekaServerApplication.class, args);
		EurekaServerApplication.displaySplash();
	}

  private static void displaySplash() {
    InputStream inputStream = null;
    BufferedReader bufferedReader = null;

    try {
      inputStream = new BufferedInputStream(EurekaServerApplication.class.getResourceAsStream("/splash.txt"));
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