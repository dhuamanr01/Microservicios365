package com.ms365.middleware.usuarios.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ms365.middleware.usuarios.assembler.ClienteAssembler;
import com.ms365.middleware.usuarios.domain.ClienteDomain;
import com.ms365.middleware.usuarios.dto.ClienteDTO;
import com.ms365.middleware.usuarios.repository.ClienteRepository;

@Service
public class ClienteService {
  @SuppressWarnings("unused")
  private static Logger logger = LoggerFactory.getLogger(ClienteService.class);

  @Autowired
  private ClienteRepository clienteRepository;

  public ClienteService() {
    super();
  }

  public void finalize(){}

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public List<ClienteDTO> findAll() throws Exception {
    List<ClienteDomain> listDomain = this.clienteRepository.findAll();
    List<ClienteDTO> listDTO = ClienteAssembler.buildDtoDomainCollection(listDomain);

    return listDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public Page<ClienteDTO> findAll(Pageable pageable) throws Exception {
    Page<ClienteDomain> pageDomain = this.clienteRepository.findAll(pageable);
    Page<ClienteDTO> pageDTO = ClienteAssembler.buildDtoDomainCollection(pageDomain);

    return pageDTO;
  }

  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public ClienteDTO findById(Integer id) throws Exception {
    ClienteDTO dto = null;
    Optional<ClienteDomain> opt = this.clienteRepository.findById(id);

    if (opt.isPresent()) {
      ClienteDomain dom = opt.get();
      dto = ClienteAssembler.buildDtoDomain(dom);
    }

    return dto;
  }
}
