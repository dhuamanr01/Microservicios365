package com.ms365.middleware.proyectos.config;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.Decoder;
import feign.codec.Encoder;

public class FeignConfig {
  @Bean
  public Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

  @Bean
  public RequestInterceptor requestInterceptor(){
    return requestTemplate -> {
      requestTemplate.header("Content-Type", "application/json");
      requestTemplate.header("Accept",       "application/json");
    };
  }

  //@Bean
  //public Decoder feignDecoder() {
  //  return (response, type) -> {
  //    String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
  //    JavaType javaType = TypeFactory.defaultInstance().constructType(type);
  //    return new ObjectMapper().readValue(bodyStr, javaType);
  //  };
  //}

  @Bean
  public Decoder feignDecoder() {
    HttpMessageConverter<?> jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
    ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
    return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
  }

  @Bean
  public Encoder feignEncoder() {
    HttpMessageConverter<?> jacksonConverter = new MappingJackson2HttpMessageConverter(customObjectMapper());
    ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);
    return new SpringEncoder(objectFactory);
  }

  public ObjectMapper customObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    //ACCEPT_EMPTY_STRING_AS_NULL_OBJECT
    //ACCEPT_SINGLE_VALUE_AS_ARRAY
    //ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT
    return objectMapper;
  }
}
