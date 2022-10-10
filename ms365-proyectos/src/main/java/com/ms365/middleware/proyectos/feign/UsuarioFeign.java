package com.ms365.middleware.proyectos.feign;

import java.util.Map;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ms365.middleware.proyectos.config.FeignConfig;
import com.ms365.middleware.proyectos.config.RibbonConfig;

@FeignClient(name="MSUSUARIOS", decode404=true, configuration=FeignConfig.class)
@RibbonClient(name="MSUSUARIOS", configuration=RibbonConfig.class)
public interface UsuarioFeign {
  @GetMapping(value="/usuario/findbyid/{id}",
              produces=MediaType.APPLICATION_JSON_VALUE)
  feign.Response findByUsuario(@RequestHeader Map<String, String> headerMap,
                               @PathVariable(name="id") Integer usuarioId);

}
