package com.ms365.middleware.usuarios.feign;

import java.util.Map;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ms365.middleware.usuarios.config.FeignConfig;
import com.ms365.middleware.usuarios.config.RibbonConfig;

@FeignClient(name="MSPROYECTOSARCHIVOS", decode404=true, configuration=FeignConfig.class)
@RibbonClient(name="MSPROYECTOSARCHIVOS", configuration=RibbonConfig.class)
public interface ProyectoArchivoFeign {
  @GetMapping(value="/proyecto/archivos/findbyproyectoid/{id}",
              produces=MediaType.APPLICATION_JSON_VALUE)
  feign.Response findByProyecto(@RequestHeader Map<String, String> headerMap,
                               @PathVariable(name="id") Integer proyectoId);

}
