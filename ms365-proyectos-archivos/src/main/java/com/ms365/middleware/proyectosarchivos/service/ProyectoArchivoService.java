package com.ms365.middleware.proyectosarchivos.service;

import java.io.File;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ms365.middleware.proyectosarchivos.assembler.ProyectoArchivoAssembler;
import com.ms365.middleware.proyectosarchivos.domain.ProyectoArchivoDomain;
import com.ms365.middleware.proyectosarchivos.dto.ProyectoArchivoDTO;
import com.ms365.middleware.proyectosarchivos.dto.ProyectoDTO;
import com.ms365.middleware.proyectosarchivos.exception.FailledValidationException;
import com.ms365.middleware.proyectosarchivos.repository.ProyectoArchivoRepository;
import com.ms365.middleware.proyectosarchivos.utilities.NumberUtil;

@Service
public class ProyectoArchivoService {
  private static Logger logger = LoggerFactory.getLogger(ProyectoArchivoService.class);

  @Autowired
  private ProyectoArchivoRepository proyectoArchivoRepository;

  @Autowired
  private ProyectoService proyectoService;

  @Value("${application.folder.proyectos}")
  private String folder;

  public ProyectoArchivoService() {
    super();
  }

  public void finalize(){}

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public List<ProyectoArchivoDTO> findAll() throws Exception {
    List<ProyectoArchivoDomain> listDomain = this.proyectoArchivoRepository.findAll();
    List<ProyectoArchivoDTO> listDTO = ProyectoArchivoAssembler.buildDtoDomainCollection(listDomain);

    return listDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public Page<ProyectoArchivoDTO> findAll(Pageable pageable) throws Exception {
    Page<ProyectoArchivoDomain> pageDomain = this.proyectoArchivoRepository.findAll(pageable);
    Page<ProyectoArchivoDTO> pageDTO = ProyectoArchivoAssembler.buildDtoDomainCollection(pageDomain);

    return pageDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public ProyectoArchivoDTO findById(Integer id) throws Exception {
    ProyectoArchivoDTO dto = null;
    Optional<ProyectoArchivoDomain> opt = this.proyectoArchivoRepository.findById(id);

    if (opt.isPresent()) {
      ProyectoArchivoDomain dom = opt.get();
      dto = ProyectoArchivoAssembler.buildDtoDomain(dom);

      ProyectoDTO proyectoDTO = new ProyectoDTO();
      proyectoDTO.setId(dom.getProyectoId());
      dto.setProyecto(proyectoDTO);
    }

    return dto;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public List<ProyectoArchivoDTO> findByProyectoId(Integer proyectoId) throws Exception {
    List<ProyectoArchivoDomain> listDomain = this.proyectoArchivoRepository.findByProyectoId(proyectoId);
    List<ProyectoArchivoDTO> listDTO = ProyectoArchivoAssembler.buildDtoDomainCollection(listDomain);

    return listDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public ProyectoArchivoDTO downloadBase64(Integer id) throws Exception {
    ProyectoArchivoDTO dto = null;
    Optional<ProyectoArchivoDomain> opt = this.proyectoArchivoRepository.findById(id);

    if (opt.isPresent()) {
      ProyectoArchivoDomain dom = opt.get();
      dto = ProyectoArchivoAssembler.buildDtoDomain(dom);

      String folderPath = this.folder + "/Proj" + NumberUtil.format("000000", dom.getProyectoId());
      File file = new File(folderPath + "/" + NumberUtil.format("000000", dto.getId()) + ".bin");
      logger.info(" > File: " + dto.getRuta());
      logger.info(" > File: " + file.getAbsolutePath() + " exists?" + file.exists());

      if (!file.exists()) {
        dto.setContenido("No existe archivo");
      }
      else {
        byte[] content = FileUtils.readFileToByteArray(file);
        dto.setContenido(Base64.getEncoder().encodeToString(content));
      }
    }

    return dto;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public ProyectoArchivoDTO storeBase64(ProyectoArchivoDTO dto, Map<String, String> headers) throws Exception {
    if (dto == null) {
      throw new FailledValidationException("datos son obligatorios");
    }
    else if (dto.getNombre() == null || dto.getNombre().isEmpty()) {
      throw new FailledValidationException("[titulo] es obligatorio");
    }
    else if (dto.getRuta() == null || dto.getRuta().isEmpty()) {
      throw new FailledValidationException("[nombre] es obligatorio");
    }
    else if (dto.getContenido() == null || dto.getContenido().isEmpty()) {
      throw new FailledValidationException("[contenido] es obligatorio");
    }
    else if (dto.getProyecto() == null) {
      throw new FailledValidationException("[proyecto] es obligatorio");
    }
    else if (dto.getProyecto().getId() == null) {
      throw new FailledValidationException("[proyecto.id] es obligatorio");
    }

    ProyectoDTO proyectoDTO = this.proyectoService.findById(headers, dto.getProyecto().getId());
    if (proyectoDTO == null) {
      throw new FailledValidationException("[proyecto.id] no se encuentra");
    }
    else if (!proyectoDTO.getEstado().booleanValue()) {
      throw new FailledValidationException("[proyecto] eliminado anteriormente");
    }

    List<ProyectoArchivoDomain> list = this.proyectoArchivoRepository.findByProyectoIdAndNombre(dto.getProyecto().getId(),
                                                                                                dto.getNombre().toUpperCase());
    if (list != null && list.size() > 0) {
      throw new FailledValidationException("[titulo] ya se encuentra registrado para el proyecto");
    }

    list = this.proyectoArchivoRepository.findByProyectoIdAndRuta(dto.getProyecto().getId(),
                                                                  dto.getRuta());
    if (list != null && list.size() > 0) {
      throw new FailledValidationException("[nombre] ya se encuentra registrado para el proyecto");
    }

    ProyectoArchivoDomain domain = new ProyectoArchivoDomain();
    domain.setProyectoId(dto.getProyecto().getId());
    domain.setNombre(dto.getNombre().toUpperCase());
    domain.setRuta(dto.getRuta());
    domain.setCreatedAt(new Date());
    domain.setUpdatedAt(null);
    domain = this.proyectoArchivoRepository.save(domain);

    String folderPath = this.folder + "/Proj" + NumberUtil.format("000000", dto.getProyecto().getId());
    File folder = new File(folderPath);
    logger.info(" > Folder: " + folder.getAbsolutePath() + " exists?" + folder.exists());
    if (!folder.exists()) {
      FileUtils.forceMkdir(folder);
      logger.info(" > Folder created: " + folder.getAbsolutePath());
    }

    File file = new File(folderPath + "/" + NumberUtil.format("000000", domain.getId()) + ".bin");
    byte[] decodedBytes = Base64.getDecoder().decode(dto.getContenido());
    FileUtils.writeByteArrayToFile(file, decodedBytes);
    logger.info(" > File: " + dto.getRuta());
    logger.info(" > File save: " + file.getAbsolutePath());

    return ProyectoArchivoAssembler.buildDtoDomain(domain);
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public ProyectoArchivoDTO delete(Integer id) throws Exception {
    ProyectoArchivoDomain domain = null;
    Optional<ProyectoArchivoDomain> optArch = this.proyectoArchivoRepository.findById(id);

    if (optArch.isPresent()) {
      domain = optArch.get();

      String folderPath = this.folder + "/Proj" + NumberUtil.format("000000", domain.getProyectoId());
      File file = new File(folderPath + "/" + NumberUtil.format("000000", domain.getId()) + ".bin");
      logger.info(" > File: " + domain.getRuta());
      logger.info(" > File: " + file.getAbsolutePath() + " exists?" + file.exists());

      if (file.exists()) {
        file.delete();
      }

      this.proyectoArchivoRepository.delete(domain);
    }

    return ProyectoArchivoAssembler.buildDtoDomain(domain);
  }
}
