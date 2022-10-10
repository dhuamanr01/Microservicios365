package com.ms365.middleware.usuarios.service;

import java.util.Date;
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


import com.ms365.middleware.usuarios.assembler.ClienteAssembler;
import com.ms365.middleware.usuarios.assembler.UsuarioAssembler;
import com.ms365.middleware.usuarios.domain.RoleDomain;
import com.ms365.middleware.usuarios.domain.UsuarioDomain;
import com.ms365.middleware.usuarios.dto.ProyectoDTO;
import com.ms365.middleware.usuarios.dto.UsuarioDTO;
import com.ms365.middleware.usuarios.exception.FailledValidationException;
import com.ms365.middleware.usuarios.repository.RoleRepository;
import com.ms365.middleware.usuarios.repository.UsuarioRepository;

@Service
public class UsuarioService {
  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger(UsuarioService.class);

  @Autowired
  private UsuarioRepository usuarioRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ProyectoService proyectoService;

  @Autowired
  private ProyectoArchivoService proyectoArchivoService;

  public UsuarioService() {
    super();
  }

  public void finalize(){}

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public List<UsuarioDTO> findAll() throws Exception {
    List<UsuarioDomain> listDomain = this.usuarioRepository.findAll();
    List<UsuarioDTO> listDTO = UsuarioAssembler.buildDtoDomainCollection(listDomain);

    return listDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public Page<UsuarioDTO> findAll(Pageable pageable) throws Exception {
//    Page<UsuarioDomain> pageDomain = this.usuarioRepository.findAll(pageable); //daniel
    Page<UsuarioDomain> pageDomain = this.usuarioRepository.findAllByOrderById(pageable);
    Page<UsuarioDTO> pageDTO = UsuarioAssembler.buildDtoDomainCollection(pageDomain);

    return pageDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public UsuarioDTO findById(Integer id, Map<String, String> headers) throws Exception {
    UsuarioDTO dto = null;
    Optional<UsuarioDomain> opt = this.usuarioRepository.findById(id);

    if (opt.isPresent()) {
      UsuarioDomain dom = opt.get();
      dto = UsuarioAssembler.buildDtoDomain(dom);

      if (headers.containsKey("alldependencies") && headers.get("alldependencies").equals("true")) {
        dto.setClientes(ClienteAssembler.buildDtoDomainCollection(dom.getClientes()));
        dto.setProyectos(this.proyectoService.findByUsuario(headers, id));

        for (ProyectoDTO proyectoDTO : dto.getProyectos()) {
          proyectoDTO.setArchivos(this.proyectoArchivoService.findByProyecto(headers, proyectoDTO.getId()));
        }
      }
    }

    return dto;
  }

  //logear por username
  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public UsuarioDTO findByUserName(String userName) throws Exception {
    UsuarioDTO dto = null;
    Optional<UsuarioDomain> opt = this.usuarioRepository.findByUserName(userName);

    if (opt.isPresent()) {
      UsuarioDomain dom = opt.get();
      dto = UsuarioAssembler.buildDtoDomain(dom);

//      if (headers.containsKey("alldependencies") && headers.get("alldependencies").equals("true")) {
//        dto.setClientes(ClienteAssembler.buildDtoDomainCollection(dom.getClientes()));
//        dto.setProyectos(this.proyectoService.findByUsuario(headers, id));
//
//        for (ProyectoDTO proyectoDTO : dto.getProyectos()) {
//          proyectoDTO.setArchivos(this.proyectoArchivoService.findByProyecto(headers, proyectoDTO.getId()));
//        }
//      }
    }

    return dto;
  }

  
  
  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public UsuarioDTO store(UsuarioDTO dto) throws Exception {
    if (dto == null) {
      throw new FailledValidationException("datos son obligatorios");
    }
    else if (dto.getUserName() == null || dto.getUserName().isEmpty()) {
      throw new FailledValidationException("[user_name] es obligatorio");
    }
    else if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
      throw new FailledValidationException("[password] es obligatorio");
    }
    else if (dto.getRole() == null) {
      throw new FailledValidationException("[role] es obligatorio");
    }
    else if (dto.getRole().getId() == null) {
      throw new FailledValidationException("[role.id] es obligatorio");
    }

    List<UsuarioDomain> list = this.usuarioRepository.findByUserNameAndEstado(dto.getUserName(), new Boolean(true));
    if (list != null && list.size() > 0) {
      throw new FailledValidationException("[user_name] ya se encuentra registrado");
    }

    Optional<RoleDomain> optRole = this.roleRepository.findById(dto.getRole().getId());
    if (!optRole.isPresent()) {
      throw new FailledValidationException("[role.id] no se encuentra");
    }

    UsuarioDomain domain = new UsuarioDomain();
    domain.setRole(optRole.get());
    domain.setUserName(dto.getUserName());
    domain.setPassword(dto.getPassword());
    domain.setEstado(new Boolean(true));
    domain.setCreatedAt(new Date());
    domain.setUpdatedAt(null);
    domain = this.usuarioRepository.save(domain);

    return UsuarioAssembler.buildDtoDomain(domain);
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public UsuarioDTO update(UsuarioDTO dto) throws Exception {
    if (dto == null) {
      throw new FailledValidationException("datos son obligatorios");
    }
    else if (dto.getId() == null) {
      throw new FailledValidationException("[id] es obligatorio");
    }

    UsuarioDomain domain = null;
    Optional<UsuarioDomain> optUser = this.usuarioRepository.findById(dto.getId());

    if (optUser.isPresent()) {
      domain = optUser.get();

      if (!domain.getEstado().booleanValue()) {
        throw new FailledValidationException("[usuario] eliminado anteriormente");
      }

      if (dto.getUserName() != null && !dto.getUserName().isEmpty()) {
        if (!dto.getUserName().equals(domain.getUserName())) {
          List<UsuarioDomain> list = this.usuarioRepository.findByUserNameAndIdNotAndEstado(dto.getUserName(),
                                                                                            dto.getId(),
                                                                                            new Boolean(true));

          if (list != null && list.size() > 0) {
            throw new FailledValidationException("[user_name] ya se encuentra registrado");
          }
          domain.setUserName(dto.getUserName());
        }
      }

      if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
        if (!dto.getPassword().equals(domain.getPassword())) {
          domain.setPassword(dto.getPassword());
        }
      }

      if (dto.getRole() != null && dto.getRole().getId() != null) {
        if (dto.getRole().getId().intValue() != domain.getRole().getId().intValue()) {
          Optional<RoleDomain> optRole = this.roleRepository.findById(dto.getRole().getId());

          if (!optRole.isPresent()) {
            throw new FailledValidationException("[role.id] no se encuentra");
          }

          domain.setRole(optRole.get());
        }
      }

      domain.setUpdatedAt(new Date());
      domain = this.usuarioRepository.save(domain);
    }

    return UsuarioAssembler.buildDtoDomain(domain);
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public UsuarioDTO delete(Integer id) throws Exception {
    UsuarioDomain domain = null;
    Optional<UsuarioDomain> optUser = this.usuarioRepository.findById(id);

    if (optUser.isPresent()) {
      domain = optUser.get();

      if (!domain.getEstado().booleanValue()) {
        domain = null;
      }
      else {
        domain.setEstado(new Boolean(false));
        domain.setUpdatedAt(new Date());
        domain = this.usuarioRepository.save(domain);
      }
    }

    return UsuarioAssembler.buildDtoDomain(domain);
  }
  
  
  
  
  
}
