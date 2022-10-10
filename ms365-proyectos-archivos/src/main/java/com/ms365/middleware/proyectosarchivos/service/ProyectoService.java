package com.ms365.middleware.proyectosarchivos.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ms365.middleware.proyectosarchivos.dto.ProyectoDTO;
import com.ms365.middleware.proyectosarchivos.feign.ProyectoFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ProyectoService {
  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger(ProyectoService.class);

  @Autowired
  private ProyectoFeign proyectoFeign;

  public ProyectoService() {
    super();
  }

  public void finalize(){}

  @HystrixCommand(fallbackMethod="findByIdFallback")
  public ProyectoDTO findById(Map<String, String> headers, Integer proyectoId) {
    ProyectoDTO dto = null;

    headers.put("alldependencies", "false");
    feign.Response response = this.proyectoFeign.findById(headers, proyectoId);
    String jsonResponse = (response.body() == null ? "" : response.body().toString());

    switch (response.status()) {
      case HttpServletResponse.SC_OK:
        dto = new Gson().fromJson(jsonResponse, ProyectoDTO.class);
    }

    return dto;
  }

  @SuppressWarnings("unused")
  private ProyectoDTO findByIdFallback(Map<String, String> headers, Integer usuarioId) {
    return null;
  }
}
