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
import com.ms365.middleware.usuarios.dto.ProyectoDTO;
import com.ms365.middleware.usuarios.feign.ProyectoFeign;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class ProyectoService {
  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger(UsuarioService.class);

  @Autowired
  private ProyectoFeign proyectoFeign;

  public ProyectoService() {
    super();
  }

  public void finalize(){}

  @HystrixCommand(fallbackMethod="findByUsuarioFallback")
  public List<ProyectoDTO> findByUsuario(Map<String, String> headers, Integer usuarioId) {
    List<ProyectoDTO> list = null;

    feign.Response response = this.proyectoFeign.findByUsuario(headers, usuarioId);
    String jsonResponse = (response.body() == null ? "" : response.body().toString());

    switch (response.status()) {
      case HttpServletResponse.SC_OK:
        Type listType = new TypeToken<List<ProyectoDTO>>() {}.getType();
        list = new Gson().fromJson(jsonResponse, listType);

        //JsonParser jsonParser = new JsonParser();
        //JsonArray jsonArray = jsonParser.parse(jsonResponse).getAsJsonArray();
        //
        //list = new ArrayList<ProyectoDTO>();
        //for (JsonElement jsonElement : jsonArray) {
        //  list.add(new Gson().fromJson(jsonElement, ProyectoDTO.class));
        //}
    }

    return list;
  }

  @SuppressWarnings("unused")
  private List<ProyectoDTO> findByUsuarioFallback(Map<String, String> headers, Integer usuarioId) {
    return null;
  }
}
