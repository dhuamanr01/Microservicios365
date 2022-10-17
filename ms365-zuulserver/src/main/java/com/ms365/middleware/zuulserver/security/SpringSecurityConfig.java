package com.ms365.middleware.zuulserver.security;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ms365.middleware.zuulserver.service.AuthenticatorService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger(SpringSecurityConfig.class);

  @Autowired
  private AuthenticatorService authenticatorService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${management.endpoints.web.basePath}")
  private String managementUrl;

  @Bean
  public JWTAuthorizationFilter jwtAuthorizationFilterBean() {
    return new JWTAuthorizationFilter();
  }

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(this.authenticatorService).passwordEncoder(this.passwordEncoder);
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring()
        .antMatchers(this.managementUrl)
        .antMatchers(this.managementUrl + "/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .enableSessionUrlRewriting(false);

    http.cors();

    http.csrf().disable();

    http.authorizeRequests()
          .antMatchers(HttpMethod.GET, this.managementUrl).permitAll()
          .antMatchers(HttpMethod.GET, this.managementUrl + "/**").permitAll()
          .antMatchers(HttpMethod.POST, "/login").permitAll()
          .anyRequest().authenticated()
        .and()
          .addFilterBefore(new JWTAuthenticationFilter(super.authenticationManager()),
                                                       UsernamePasswordAuthenticationFilter.class)
          .addFilterBefore(this.jwtAuthorizationFilterBean(),
                           UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    logger.info("iniciando  CorsConfigurationSource");
//    configuration.setAllowedHeaders(Arrays.asList("Origin,Accept", "X-Requested-With", "Content-Type", "Access-Control-Request-Method", "Access-Control-Request-Headers","Authorization"));

    
    //    configuration.setAllowedOrigins(Arrays.asList("http://192.168.1.9:4200"));
   System.out.println("despues de setAllowedOrigins");
   logger.info("despues de setAllowedOrigins");
//    configuration.setExposedHeaders(Arrays.asList("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));   
  
    logger.info("exposed headers");
    configuration.setAllowedMethods(Arrays.asList("HEAD","GET", "POST", "PUT", "DELETE", "TRACE", "OPTIONS"));
    System.out.println("despues de set allowed methods");

    configuration.setAllowedOrigins(Arrays.asList("*"));
//    configuration.addAllowedOrigin("http://192.168.1.9:4200");
    logger.info("despues de addAllowedOrigin");
 
    configuration.setAllowedHeaders(Arrays.asList("*"));
    configuration.setMaxAge(1800L);
    System.out.println("despues de setmaxage");
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
