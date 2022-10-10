package com.ms365.middleware.proyectosarchivos.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ms365.middleware.proyectosarchivos.common.constants.Constants;
import com.ms365.middleware.proyectosarchivos.dto.ProyectoArchivoDTO;
import com.ms365.middleware.proyectosarchivos.exception.FailledValidationException;
import com.ms365.middleware.proyectosarchivos.service.ProyectoArchivoService;
import com.ms365.middleware.proyectosarchivos.utilities.DateUtil;
import com.ms365.middleware.proyectosarchivos.utilities.NumberUtil;

@RestController
@RequestMapping("/proyecto/archivo")
public class ProyectoArchivoController extends BaseController {
  private static Logger logger = LoggerFactory.getLogger(ProyectoArchivoController.class);

  @Autowired
  private ProyectoArchivoService proyectoArchivoService;

  @Autowired
  public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
    super.httpServletRequest = httpServletRequest;
  }

  @Autowired
  public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
    super.httpServletResponse = httpServletResponse;
  }

  @Value("${application.folder.proyectos}")
  private String folder;

  public ProyectoArchivoController() {
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
      List<ProyectoArchivoDTO> list = this.proyectoArchivoService.findAll();

      if (list.size() == 0) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        response = new ResponseEntity<List<ProyectoArchivoDTO>>(list, HttpStatus.OK);
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
      Page<ProyectoArchivoDTO> page = this.proyectoArchivoService.findAll(pageable);

      if (page == null || page.getContent().size() == 0) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        response = new ResponseEntity<Page<ProyectoArchivoDTO>>(page, HttpStatus.OK);
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
      ProyectoArchivoDTO dto = this.proyectoArchivoService.findById(id);

      if (dto == null) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        response = new ResponseEntity<ProyectoArchivoDTO>(dto, HttpStatus.OK);
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

  @GetMapping(path="/findbyproyectoid/{id}",
              produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> findByProyectoId(@PathVariable(name="id") Integer id) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      List<ProyectoArchivoDTO> list = this.proyectoArchivoService.findByProyectoId(id);

      if (list.size() == 0) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        response = new ResponseEntity<List<ProyectoArchivoDTO>>(list, HttpStatus.OK);
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

  @GetMapping(path="/download/base64/{id}",
              produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> downloadBase64(@PathVariable(name="id") Integer id) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      ProyectoArchivoDTO dto = this.proyectoArchivoService.downloadBase64(id);

      if (dto == null) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        response = new ResponseEntity<ProyectoArchivoDTO>(dto, HttpStatus.OK);
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

  @GetMapping(path="/download/stream/{id}",
              produces="application/octet-stream")
  public ResponseEntity<?> downloadStream(@PathVariable(name="id") Integer id) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      ProyectoArchivoDTO dto = this.proyectoArchivoService.findById(id);

      if (dto == null) {
        response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
      }
      else {
        String folderPath = this.folder + "/Proj" + NumberUtil.format("000000", dto.getProyecto().getId());
        File file = new File(folderPath + "/" + NumberUtil.format("000000", dto.getId()) + ".bin");
        logger.info(" > File: " + dto.getRuta());
        logger.info(" > File: " + file.getAbsolutePath() + " exists?" + file.exists());

        if (!file.exists()) {
          response = new ResponseEntity<Object>(null, HttpStatus.NO_CONTENT);
        }
        else {
          byte[] content = FileUtils.readFileToByteArray(file);
          httpServletResponse.addHeader("content-disposition", "attachment; filename=" + dto.getRuta());
          return new ResponseEntity<byte[]>(content, HttpStatus.OK);
        }

        response = new ResponseEntity<ProyectoArchivoDTO>(dto, HttpStatus.OK);
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

  @PostMapping(path="/base64",
          produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> storeBase64(@RequestBody ProyectoArchivoDTO request) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      ProyectoArchivoDTO dto = this.proyectoArchivoService.storeBase64(request, super.extractHeaders());

      response = new ResponseEntity<ProyectoArchivoDTO>(dto,
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

  @DeleteMapping(path="/{id}",
                 produces=MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<?> delete(@PathVariable(name="id") Integer id) {
    ResponseEntity<?> response = null;
    long startProcess = System.currentTimeMillis();

    logger.trace(Constants.Logger.Method.INITIALIZE);

    try {
      ProyectoArchivoDTO dto = this.proyectoArchivoService.delete(id);

      if (dto == null) {
        response = new ResponseEntity<Object>(null, HttpStatus.NOT_FOUND);
      }
      else {
        response = new ResponseEntity<String>("{\"message\" : \"Archivo eliminado correctamente\"}",
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
