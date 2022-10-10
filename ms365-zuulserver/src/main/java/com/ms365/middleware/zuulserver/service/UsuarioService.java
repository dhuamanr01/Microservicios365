package com.ms365.middleware.zuulserver.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ms365.middleware.zuulserver.assembler.UsuarioAssembler;
import com.ms365.middleware.zuulserver.domain.UsuarioDomain;
import com.ms365.middleware.zuulserver.dto.UsuarioDTO;
import com.ms365.middleware.zuulserver.repository.UsuarioRepository;

@Service
public class UsuarioService {
  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger(UsuarioService.class);

  @Autowired
  private UsuarioRepository usuarioRepository;

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
    Page<UsuarioDomain> pageDomain = this.usuarioRepository.findAll(pageable);
    Page<UsuarioDTO> pageDTO = UsuarioAssembler.buildDtoDomainCollection(pageDomain);

    return pageDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public UsuarioDTO findById(Integer id) throws Exception {
    UsuarioDTO dto = null;
    Optional<UsuarioDomain> opt = this.usuarioRepository.findById(id);

    if (opt.isPresent()) {
      UsuarioDomain dom = opt.get();
      dto = UsuarioAssembler.buildDtoDomain(dom);
    }

    return dto;
  }
  //daniel
  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public UsuarioDTO findByUserName(String userName) throws Exception {
    UsuarioDTO dto = null;
    Optional<UsuarioDomain> opt = this.usuarioRepository.findByUserName(userName);

    if (opt.isPresent()) {
      UsuarioDomain dom = opt.get();
      dto = UsuarioAssembler.buildDtoDomain(dom);
    }

    return dto;
  }
  
}
