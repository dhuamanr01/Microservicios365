package com.ms365.middleware.usuarios.controller;

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

import com.ms365.middleware.usuarios.common.constants.Constants;
import com.ms365.middleware.usuarios.dto.ClienteDTO;
import com.ms365.middleware.usuarios.dto.UsuarioDTO;
import com.ms365.middleware.usuarios.exception.FailledValidationException;
import com.ms365.middleware.usuarios.service.UsuarioService;
import com.ms365.middleware.usuarios.utilities.DateUtil;

@RestController
@RequestMapping("/usuario")
public class UsuarioController extends BaseController {
  private static Logger logger = LoggerFactory.getLogger(UsuarioController.class);

  @Autowired
  private UsuarioService usuarioService;

  @Autowired
  public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
    super.httpServletRequest = httpServletRequest;
  }

  public UsuarioController() {
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
      List<UsuarioDTO> list = this.usuarioService.findAll();

      if (list.size() == 0) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        response = new ResponseEntity<List<UsuarioDTO>>(list, HttpStatus.OK);
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
      Page<UsuarioDTO> page = this.usuarioService.findAll(pageable);

      if (page == null || page.getContent().size() == 0) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        response = new ResponseEntity<Page<UsuarioDTO>>(page, HttpStatus.OK);
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
      UsuarioDTO dto = this.usuarioService.findById(id, super.extractHeaders());

      if (dto == null) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        for (ClienteDTO cliente : dto.getClientes()) {
          cliente.setUsuario(null);
        }

        response = new ResponseEntity<UsuarioDTO>(dto, HttpStatus.OK);
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
  public ResponseEntity<?> store(@RequestBody UsuarioDTO request) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      UsuarioDTO dto = this.usuarioService.store(request);
      response = new ResponseEntity<UsuarioDTO>(dto,
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
  public ResponseEntity<?> update(@RequestBody UsuarioDTO request) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      UsuarioDTO dto = this.usuarioService.update(request);

      if (dto == null) {
        response = new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
      }
      else {
        response = new ResponseEntity<UsuarioDTO>(dto,
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
      UsuarioDTO dto = this.usuarioService.delete(id);

      if (dto == null) {
        response = new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
      }
      else {
        response = new ResponseEntity<String>("{\"message\" : \"Usuario eliminado correctamente\"}",
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
  //daniel
//Se agrego este endpoint para obtener el role por userName y usarlo para el menu dinamico 
  @GetMapping(path="/findByUserName/{userName}",
          produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findByUserName(@PathVariable(name="userName") String userName) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.info("findByUserName");
    logger.info(Constants.Logger.Method.INITIALIZE);

    try {
      UsuarioDTO usuarioDTO = this.usuarioService.findByUserName(userName);

      if (usuarioDTO == null) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        response = new ResponseEntity<UsuarioDTO>(usuarioDTO, HttpStatus.OK);
      }
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
      response = new ResponseEntity<String>("{\"error\" : \"" + ex.getMessage() + "\"}",
              HttpStatus.INTERNAL_SERVER_ERROR);
    }
    finally {
      logger.info(" > " + DateUtil.timeElapsed(System.currentTimeMillis() - startProcess) + " took");
      logger.info(Constants.Logger.Method.FINALIZE);
    }

    return response;
  }
  
}
