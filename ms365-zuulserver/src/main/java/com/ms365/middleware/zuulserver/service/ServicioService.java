package com.ms365.middleware.zuulserver.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.ms365.middleware.zuulserver.assembler.RoleAssembler;
import com.ms365.middleware.zuulserver.assembler.ServicioAssembler;
import com.ms365.middleware.zuulserver.domain.ServicioDomain;
import com.ms365.middleware.zuulserver.dto.ServicioDTO;
import com.ms365.middleware.zuulserver.repository.ServicioRepository;

@Service
public class ServicioService {
  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger(ServicioService.class);

  @Autowired
  private ServicioRepository servicioRepository;

  public ServicioService() {
    super();
  }

  public void finalize(){}

  @Transactional(Transactional.TxType.SUPPORTS)
  public List<ServicioDTO> findAll() throws Exception {
    List<ServicioDomain> listDomain = this.servicioRepository.findAll(Sort.by(Sort.Direction.ASC, "metodo", "orden"));

    List<ServicioDTO> listDTO = new ArrayList<ServicioDTO>();
    for (ServicioDomain dom : listDomain) {
      ServicioDTO dto = ServicioAssembler.buildDtoDomain(dom);
      dto.setRoles(RoleAssembler.buildDtoDomainCollection(dom.getRoles()));
      listDTO.add(dto);
    }

    return listDTO;
  }

  @Transactional(Transactional.TxType.SUPPORTS)
  public List<ServicioDTO> findByMetodo(String metodo, Sort sort) throws Exception {
    List<ServicioDomain> listDomain = this.servicioRepository.findByMetodo(metodo, Sort.by(Sort.Direction.ASC, "orden"));

    List<ServicioDTO> listDTO = new ArrayList<ServicioDTO>();
    for (ServicioDomain dom : listDomain) {
      ServicioDTO dto = ServicioAssembler.buildDtoDomain(dom);
      dto.setRoles(RoleAssembler.buildDtoDomainCollection(dom.getRoles()));
      listDTO.add(dto);
    }

    return listDTO;
  }
}
