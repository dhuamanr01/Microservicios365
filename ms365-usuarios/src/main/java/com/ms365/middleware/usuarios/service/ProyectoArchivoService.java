package com.ms365.middleware.usuarios.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ms365.middleware.usuarios.dto.ProyectoArchivoDTO;
import com.ms365.middleware.usuarios.feign.ProyectoArchivoFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ProyectoArchivoService {
  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger(ProyectoArchivoService.class);

  @Autowired
  private ProyectoArchivoFeign proyectoArchivoFeign;

  public ProyectoArchivoService() {
    super();
  }

  public void finalize(){}

  @HystrixCommand(fallbackMethod="findByProyectoFallback")
  public List<ProyectoArchivoDTO> findByProyecto(Map<String, String> headers, Integer proyectoId) {
    List<ProyectoArchivoDTO> list = null;

    feign.Response response = this.proyectoArchivoFeign.findByProyecto(headers, proyectoId);
    String jsonResponse = (response.body() == null ? "" : response.body().toString());

    switch (response.status()) {
      case HttpServletResponse.SC_OK:
        Type listType = new TypeToken<List<ProyectoArchivoDTO>>() {}.getType();
        list = new Gson().fromJson(jsonResponse, listType);
    }

    return list;
  }

  @SuppressWarnings("unused")
  private List<ProyectoArchivoDTO> findByProyectoFallback(Map<String, String> headers, Integer usuarioId) {
    return null;
  }
}
