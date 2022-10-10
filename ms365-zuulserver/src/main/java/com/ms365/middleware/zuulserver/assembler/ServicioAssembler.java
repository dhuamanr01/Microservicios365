package com.ms365.middleware.zuulserver.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.ms365.middleware.zuulserver.domain.ServicioDomain;
import com.ms365.middleware.zuulserver.dto.ServicioDTO;

public class ServicioAssembler {
  public ServicioAssembler() {}

  public static ServicioDTO buildDtoDomain(ServicioDomain domain) throws Exception {
    ServicioDTO dto = null;

    if (domain != null) {
      dto = new ServicioDTO();
      dto.setId(domain.getId());
      dto.setMetodo(domain.getMetodo());
      dto.setOrden(domain.getOrden());
      dto.setUrl(domain.getUrl());
    }

    return dto;
  }

  public static List<ServicioDTO> buildDtoDomainCollection(List<ServicioDomain> listDomain) throws Exception {
    List<ServicioDTO> listDTO = null;

    if (listDomain != null) {
      listDTO = new ArrayList<ServicioDTO>();

      for (ServicioDomain domain : listDomain) {
        listDTO.add(ServicioAssembler.buildDtoDomain(domain));
      }
    }

    return listDTO;
  }

  public static List<ServicioDTO> buildDtoDomainCollection(Set<ServicioDomain> setDomain) throws Exception {
    List<ServicioDTO> listDTO = null;

    if (setDomain != null) {
      listDTO = new ArrayList<ServicioDTO>();

      for (ServicioDomain domain : setDomain) {
        listDTO.add(ServicioAssembler.buildDtoDomain(domain));
      }
    }

    return listDTO;
  }

  public static Page<ServicioDTO> buildDtoDomainCollection(Page<ServicioDomain> pageDomain) throws Exception {
    List<ServicioDTO> listDTO = new ArrayList<ServicioDTO>();

    for (ServicioDomain domain : pageDomain) {
      listDTO.add(ServicioAssembler.buildDtoDomain(domain));
    }

    Page<ServicioDTO> pageDTO = new PageImpl<ServicioDTO>(listDTO,
                                                  pageDomain.getPageable(),
                                                  pageDomain.getTotalElements());


    return pageDTO;
  }

}
