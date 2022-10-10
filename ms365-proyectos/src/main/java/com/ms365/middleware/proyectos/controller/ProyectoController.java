package com.ms365.middleware.proyectos.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms365.middleware.proyectos.common.constants.Constants;
import com.ms365.middleware.proyectos.dto.ProyectoDTO;
import com.ms365.middleware.proyectos.exception.FailledValidationException;
import com.ms365.middleware.proyectos.service.ProyectoService;
import com.ms365.middleware.proyectos.utilities.DateUtil;

@RestController
@RequestMapping("/proyecto")
public class ProyectoController extends BaseController {
  private static Logger logger = LoggerFactory.getLogger(ProyectoController.class);

  @Autowired
  private ProyectoService proyectoService;

  @Autowired
  public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
    super.httpServletRequest = httpServletRequest;
  }

  public ProyectoController() {
    super();
  }

  public void finalize(){}

  @GetMapping(path="/list",
              produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getList() {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      List<ProyectoDTO> list = this.proyectoService.findAll();

      if (list.size() == 0) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        response = new ResponseEntity<List<ProyectoDTO>>(list, HttpStatus.OK);
      }
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      response = new ResponseEntity<String>("{\"error\" : \"" + ex.getMessage() + "\"}",
                                             HttpStatus.INTERNAL_SERVER_ERROR);
    }
    finally {
      logger.info(" > " + DateUtil.timeElapsed(System.currentTimeMillis() - startProcess) + " took");
      logger.trace(Constants.Logger.Method.FINALIZE);
    }

    return response;
  }

  @GetMapping(path="/page/{num}/{size}",
              produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> getPage(@PathVariable(name="num") Integer num,
                                   @PathVariable(name="size") Integer size) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      Pageable pageable = PageRequest.of(num - 1, size);
      Page<ProyectoDTO> page = this.proyectoService.findAll(pageable);

      if (page == null || page.getContent().size() == 0) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        response = new ResponseEntity<Page<ProyectoDTO>>(page, HttpStatus.OK);
      }
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      response = new ResponseEntity<String>("{\"error\" : \"" + ex.getMessage() + "\"}",
                                             HttpStatus.INTERNAL_SERVER_ERROR);
    }
    finally {
      logger.info(" > " + DateUtil.timeElapsed(System.currentTimeMillis() - startProcess) + " took");
      logger.trace(Constants.Logger.Method.FINALIZE);
    }

    return response;
  }

  @GetMapping(path="/findbyid/{id}",
              produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findById(@PathVariable(name="id") Integer id) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      ProyectoDTO dto = this.proyectoService.findById(id, super.extractHeaders());

      if (dto == null) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        response = new ResponseEntity<ProyectoDTO>(dto, HttpStatus.OK);
      }
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      response = new ResponseEntity<String>("{\"error\" : \"" + ex.getMessage() + "\"}",
                                             HttpStatus.INTERNAL_SERVER_ERROR);
    }
    finally {
      logger.info(" > " + DateUtil.timeElapsed(System.currentTimeMillis() - startProcess) + " took");
      logger.trace(Constants.Logger.Method.FINALIZE);
    }

    return response;
  }

  @GetMapping(path="/findbyusuarioid/{id}",
              produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findByUsuarioId(@PathVariable(name="id") Integer id) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      List<ProyectoDTO> list = this.proyectoService.findByUsuarioId(id);

      if (list.size() == 0) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        response = new ResponseEntity<List<ProyectoDTO>>(list, HttpStatus.OK);
      }
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      response = new ResponseEntity<String>("{\"error\" : \"" + ex.getMessage() + "\"}",
                                             HttpStatus.INTERNAL_SERVER_ERROR);
    }
    finally {
      logger.info(" > " + DateUtil.timeElapsed(System.currentTimeMillis() - startProcess) + " took");
      logger.trace(Constants.Logger.Method.FINALIZE);
    }

    return response;
  }

  @PostMapping(produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> store(@RequestBody ProyectoDTO request) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      ProyectoDTO dto = this.proyectoService.store(request, super.extractHeaders());
      response = new ResponseEntity<ProyectoDTO>(dto,
                                                 HttpStatus.CREATED);
    }
    catch (FailledValidationException ex) {
      response = new ResponseEntity<String>("{\"error\" : \"" + ex.getMessage() + "\"}",
                                            HttpStatus.NOT_ACCEPTABLE);
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      response = new ResponseEntity<String>("{\"error\" : \"" + ex.getMessage() + "\"}",
                                            HttpStatus.INTERNAL_SERVER_ERROR);
    }
    finally {
      logger.info(" > " + DateUtil.timeElapsed(System.currentTimeMillis() - startProcess) + " took");
      logger.trace(Constants.Logger.Method.FINALIZE);
    }

    return response;
  }

  @PutMapping(produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> update(@RequestBody ProyectoDTO request) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      ProyectoDTO dto = this.proyectoService.update(request, super.extractHeaders());

      if (dto == null) {
        response = new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
      }
      else {
        response = new ResponseEntity<ProyectoDTO>(dto,
                                                   HttpStatus.ACCEPTED);
      }
    }
    catch (FailledValidationException ex) {
      response = new ResponseEntity<String>("{\"error\" : \"" + ex.getMessage() + "\"}",
                                            HttpStatus.NOT_ACCEPTABLE);
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      response = new ResponseEntity<String>("{\"error\" : \"" + ex.getMessage() + "\"}",
                                            HttpStatus.INTERNAL_SERVER_ERROR);
    }
    finally {
      logger.info(" > " + DateUtil.timeElapsed(System.currentTimeMillis() - startProcess) + " took");
      logger.trace(Constants.Logger.Method.FINALIZE);
    }

    return response;
  }

  @DeleteMapping(path="/{id}",
                 produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable(name="id") Integer id) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      ProyectoDTO dto = this.proyectoService.delete(id);

      if (dto == null) {
        response = new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
      }
      else {
        response = new ResponseEntity<String>("{\"message\" : \"Proyecto eliminado correctamente\"}",
                                              HttpStatus.ACCEPTED);
      }
    }
    catch (FailledValidationException ex) {
      response = new ResponseEntity<Object>("{\"error\" : \"" + ex.getMessage() + "\"}",
                                            HttpStatus.FAILED_DEPENDENCY);
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      response = new ResponseEntity<String>("{\"error\" : \"" + ex.getMessage() + "\"}",
                                            HttpStatus.INTERNAL_SERVER_ERROR);
    }
    finally {
      logger.info(" > " + DateUtil.timeElapsed(System.currentTimeMillis() - startProcess) + " took");
      logger.trace(Constants.Logger.Method.FINALIZE);
    }

    return response;
  }
}
