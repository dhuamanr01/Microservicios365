package com.ms365.middleware.proyectos.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.ms365.middleware.proyectos.dto.UsuarioDTO;
import com.ms365.middleware.proyectos.feign.UsuarioFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class UsuarioService {
  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger(UsuarioService.class);

  @Autowired
  private UsuarioFeign usuarioFeign;

  public UsuarioService() {
    super();
  }

  public void finalize(){}

  @HystrixCommand(fallbackMethod="findByUsuarioFallback")
  public UsuarioDTO findByUsuario(Map<String, String> headers, Integer usuarioId) {
    UsuarioDTO dto = null;

    headers.put("alldependencies", "false");
    feign.Response response = this.usuarioFeign.findByUsuario(headers, usuarioId);
    String jsonResponse = (response.body() == null ? "" : response.body().toString());

    switch (response.status()) {
      case HttpServletResponse.SC_OK:
        dto = new Gson().fromJson(jsonResponse, UsuarioDTO.class);
    }

    return dto;
  }

  @SuppressWarnings("unused")
  private UsuarioDTO findByUsuarioFallback(Map<String, String> headers, Integer usuarioId) {
    return null;
  }

}
