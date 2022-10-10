package com.ms365.middleware.proyectos.service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ms365.middleware.proyectos.assembler.ProyectoAssembler;
import com.ms365.middleware.proyectos.common.constants.Constants;
import com.ms365.middleware.proyectos.domain.ProyectoDomain;
import com.ms365.middleware.proyectos.dto.ProyectoDTO;
import com.ms365.middleware.proyectos.dto.UsuarioDTO;
import com.ms365.middleware.proyectos.exception.FailledValidationException;
import com.ms365.middleware.proyectos.repository.ProyectoRepository;
import com.ms365.middleware.proyectos.utilities.DateUtil;

@Service
public class ProyectoService {
  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger(ProyectoService.class);

  @Autowired
  private ProyectoRepository proyectoRepository;

  @Autowired
  private ProyectoArchivoService proyectoArchivoService;

  @Autowired
  private UsuarioService usuarioService;

  public ProyectoService() {
    super();
  }

  public void finalize(){}

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public List<ProyectoDTO> findAll() throws Exception {
	  
//	UsuarioDTO usuarioDTO = this.usuarioService.findByUsuario(headers, dto.getUsuario().getId()); 
	
    List<ProyectoDomain> listDomain = this.proyectoRepository.findAll();
    logger.info("log-info-get0-user: "+listDomain.get(0).getUsuarioId());
    
 
//  List<ProyectoDTO> listDTO = ProyectoAssembler.buildDtoDomainCollection(listDomain, usuarioDTO);
    List<ProyectoDTO> listDTO = ProyectoAssembler.buildDtoDomainCollection(listDomain);
    return listDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public Page<ProyectoDTO> findAll(Pageable pageable) throws Exception {
	  
//	  UsuarioDTO usuarioDTO = this.usuarioService.findByUsuario(headers, dto.getUsuario().getId()); 

	  
    Page<ProyectoDomain> pageDomain = this.proyectoRepository.findAll(pageable);
    Page<ProyectoDTO> pageDTO = ProyectoAssembler.buildDtoDomainCollection(pageDomain);

    return pageDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public ProyectoDTO findById(Integer id, Map<String, String> headers) throws Exception {
    ProyectoDTO dto = null;
    Optional<ProyectoDomain> opt = this.proyectoRepository.findById(id);

    if (opt.isPresent()) {
      ProyectoDomain dom = opt.get();
      dto = ProyectoAssembler.buildDtoDomain(dom);

      if (headers.containsKey("alldependencies") && headers.get("alldependencies").equals("true")) {
        dto.setArchivos(this.proyectoArchivoService.findByProyecto(headers, id));
      }
    }

    return dto;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public List<ProyectoDTO> findByUsuarioId(Integer usuarioId) throws Exception {
    List<ProyectoDomain> listDomain = this.proyectoRepository.findByUsuarioId(usuarioId);
    List<ProyectoDTO> listDTO = ProyectoAssembler.buildDtoDomainCollection(listDomain);

    return listDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public ProyectoDTO store(ProyectoDTO dto, Map<String, String> headers) throws Exception {
    if (dto == null) {
      throw new FailledValidationException("datos son obligatorios");
    }
    else if (dto.getProyecto() == null || dto.getProyecto().isEmpty()) {
      throw new FailledValidationException("[proyecto] es obligatorio");
    }
    else if (dto.getDescripcion() == null || dto.getDescripcion().isEmpty()) {
      throw new FailledValidationException("[descripcion] es obligatorio");
    }
    else if (dto.getFechaExpiracion() == null || dto.getFechaExpiracion().isEmpty()) {
      throw new FailledValidationException("[fecha_expiracion] es obligatorio");
    }
    else if (!DateUtil.validateDateFormat(dto.getFechaExpiracion())) {
      throw new FailledValidationException("[fecha_expiracion] con formato invalido");
    }
    else if (!DateUtil.validateDateValue(dto.getFechaExpiracion())) {
      throw new FailledValidationException("[fecha_expiracion] invalido");
    }
    else if (Integer.parseInt(DateUtil.format(Constants.Format.Date.YEAR_MONTH_DAY, DateUtil.getDate(dto.getFechaExpiracion()))) <=
             Integer.parseInt(DateUtil.format(Constants.Format.Date.YEAR_MONTH_DAY))) {
      throw new FailledValidationException("[fecha_expiracion] no puede ser anterior o igual al dia de hoy");
    }
    else if (dto.getUsuario() == null) {
      throw new FailledValidationException("[usuario] es obligatorio");
    }
    else if (dto.getUsuario().getId() == null) {
      throw new FailledValidationException("[usuario.id] es obligatorio");
    }

//    List<ProyectoDomain> list = this.proyectoRepository.findByProyectoAndEstado(dto.getProyecto().toUpperCase(), new Boolean(true));
    List<ProyectoDomain> list = this.proyectoRepository.findByProyectoAndEstado(dto.getProyecto(), new Boolean(true));
    
    if (list != null && list.size() > 0) {
      throw new FailledValidationException("[proyecto] ya se encuentra registrado");
    }

    UsuarioDTO usuarioDTO = this.usuarioService.findByUsuario(headers, dto.getUsuario().getId());
    if (usuarioDTO == null) {
      throw new FailledValidationException("[usuario.id] no se encuentra");
    }
    else if (!usuarioDTO.getEstado().booleanValue()) {
      throw new FailledValidationException("[usuario] eliminado anteriormente");
    }

    ProyectoDomain domain = new ProyectoDomain();
    domain.setUsuarioId(usuarioDTO.getId());
    domain.setProyecto(dto.getProyecto());
//    domain.setProyecto(dto.getProyecto().toUpperCase());
    domain.setDescripcion(dto.getDescripcion());
    domain.setFechaExpiracion(DateUtil.getDate(dto.getFechaExpiracion()));
    domain.setEstado(new Boolean(true));
    domain.setCreatedAt(new Date());
    domain.setUpdatedAt(null);
    domain = this.proyectoRepository.save(domain);

    ProyectoDTO proyectoDTO = ProyectoAssembler.buildDtoDomain(domain);
    proyectoDTO.setUsuario(usuarioDTO);

    return proyectoDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public ProyectoDTO update(ProyectoDTO dto, Map<String, String> headers) throws Exception {
    UsuarioDTO usuarioDTO = null;

    if (dto == null) {
      throw new FailledValidationException("datos son obligatorios");
    }
    else if (dto.getId() == null) {
      throw new FailledValidationException("[id] es obligatorio");
    }

    ProyectoDomain domain = null;
    Optional<ProyectoDomain> optProj = this.proyectoRepository.findById(dto.getId());

    if (optProj.isPresent()) {
      domain = optProj.get();

      if (!domain.getEstado().booleanValue()) {
        throw new FailledValidationException("[proyecto] eliminado anteriormente");
      }

      if (dto.getProyecto() != null && !dto.getProyecto().isEmpty()) {
        if (!dto.getProyecto().toUpperCase().equals(domain.getProyecto().toUpperCase())) {
//          List<ProyectoDomain> list = this.proyectoRepository.findByProyectoAndIdNotAndEstado(dto.getProyecto().toUpperCase(),
//                                                                                              dto.getId(),
//                                                                                              new Boolean(true));
          List<ProyectoDomain> list = this.proyectoRepository.findByProyectoAndIdNotAndEstado(dto.getProyecto(),
                  dto.getId(),
                  new Boolean(true));

          if (list != null && list.size() > 0) {
            throw new FailledValidationException("[proyecto] ya se encuentra registrado");
          }
//          domain.setProyecto(dto.getProyecto().toUpperCase());//daniel
          domain.setProyecto(dto.getProyecto());
        }
      }

      if (dto.getDescripcion() != null && !dto.getDescripcion().isEmpty()) {
        if (!dto.getDescripcion().equals(domain.getDescripcion())) {
          domain.setDescripcion(dto.getDescripcion());
        }
      }

      if (dto.getFechaExpiracion() != null && !dto.getFechaExpiracion().isEmpty()) {
        if (!DateUtil.validateDateFormat(dto.getFechaExpiracion())) {
          throw new FailledValidationException("[fecha_expiracion] con formato invalido");
        }
        else if (!DateUtil.validateDateValue(dto.getFechaExpiracion())) {
          throw new FailledValidationException("[fecha_expiracion] invalido");
        }
        else if (Integer.parseInt(DateUtil.format(Constants.Format.Date.YEAR_MONTH_DAY, DateUtil.getDate(dto.getFechaExpiracion()))) <=
                 Integer.parseInt(DateUtil.format(Constants.Format.Date.YEAR_MONTH_DAY))) {
          throw new FailledValidationException("[fecha_expiracion] no puede ser anterior o igual al dia de hoy");
        }
        domain.setFechaExpiracion(DateUtil.getDate(dto.getFechaExpiracion()));
      }

      if (dto.getUsuario() != null && dto.getUsuario().getId() != null) {
        if (dto.getUsuario().getId().intValue() != domain.getUsuarioId().intValue()) {
          usuarioDTO = this.usuarioService.findByUsuario(headers, dto.getUsuario().getId());

          if (usuarioDTO == null) {
            throw new FailledValidationException("[usuario.id] no se encuentra");
          }
          else if (usuarioDTO.getEstado().booleanValue()) {
            throw new FailledValidationException("[usuario] eliminado anteriormente");
          }

          domain.setUsuarioId(usuarioDTO.getId());
        }
        else {
          usuarioDTO = this.usuarioService.findByUsuario(headers, domain.getUsuarioId());
        }
      }
      else {
        usuarioDTO = this.usuarioService.findByUsuario(headers, domain.getUsuarioId());
      }

      domain.setUpdatedAt(new Date());
      domain = this.proyectoRepository.save(domain);
    }

    ProyectoDTO proyectoDTO = ProyectoAssembler.buildDtoDomain(domain);
    proyectoDTO.setUsuario(usuarioDTO);

    return proyectoDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public ProyectoDTO delete(Integer id) throws Exception {
    ProyectoDomain domain = null;
    Optional<ProyectoDomain> optProj = this.proyectoRepository.findById(id);

    if (optProj.isPresent()) {
      domain = optProj.get();

      if (!domain.getEstado().booleanValue()) {
        domain = null;
      }
      else {
        domain.setEstado(new Boolean(false));
        domain.setUpdatedAt(new Date());
        domain = this.proyectoRepository.save(domain);
      }
    }

    return ProyectoAssembler.buildDtoDomain(domain);
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public int disableExpiration() throws Exception {
    int updated = this.proyectoRepository.updateExpiration();
    return updated;
  }
}
