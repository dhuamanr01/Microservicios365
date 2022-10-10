package com.ms365.middleware.proyectosarchivos.feign;

import java.util.Map;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ms365.middleware.proyectosarchivos.config.FeignConfig;
import com.ms365.middleware.proyectosarchivos.config.RibbonConfig;

@FeignClient(name="MSPROYECTOS", decode404=true, configuration=FeignConfig.class)
@RibbonClient(name="MSPROYECTOS", configuration=RibbonConfig.class)
public interface ProyectoFeign {
  @GetMapping(value="/proyecto/findbyid/{id}",
              produces=MediaType.APPLICATION_JSON_VALUE)
  feign.Response findById(@RequestHeader Map<String, String> headerMap,
                          @PathVariable(name="id") Integer proyectoId);

}
