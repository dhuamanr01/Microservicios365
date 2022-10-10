package com.ms365.middleware.zuulserver.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.ms365.middleware.zuulserver.common.constants.Constants;
import com.ms365.middleware.zuulserver.domain.RoleDomain;
import com.ms365.middleware.zuulserver.dto.RoleDTO;
import com.ms365.middleware.zuulserver.utilities.DateUtil;

public class RoleAssembler {
  public RoleAssembler() {}

  public static RoleDTO buildDtoDomain(RoleDomain domain) throws Exception {
    RoleDTO dto = null;

    if (domain != null) {
      dto = new RoleDTO();
      dto.setId(domain.getId());
      dto.setNombre(domain.getNombre());
      dto.setAuth(domain.getAuth());
      dto.setCreatedAt(DateUtil.format(Constants.Format.DateTime.DATE_TIME,
                                              domain.getCreatedAt()));
      dto.setUpdatedAt(domain.getUpdatedAt() == null ? null :
                             DateUtil.format(Constants.Format.DateTime.DATE_TIME,
                                             domain.getUpdatedAt()));
    }

    return dto;
  }

  public static List<RoleDTO> buildDtoDomainCollection(List<RoleDomain> listDomain) throws Exception {
    List<RoleDTO> listDTO = null;

    if (listDomain != null) {
      listDTO = new ArrayList<RoleDTO>();

      for (RoleDomain domain : listDomain) {
        listDTO.add(RoleAssembler.buildDtoDomain(domain));
      }
    }

    return listDTO;
  }

  public static List<RoleDTO> buildDtoDomainCollection(Set<RoleDomain> setDomain) throws Exception {
    List<RoleDTO> listDTO = null;

    if (setDomain != null) {
      listDTO = new ArrayList<RoleDTO>();

      for (RoleDomain domain : setDomain) {
        listDTO.add(RoleAssembler.buildDtoDomain(domain));
      }
    }

    return listDTO;
  }

  public static Page<RoleDTO> buildDtoDomainCollection(Page<RoleDomain> pageDomain) throws Exception {
    List<RoleDTO> listDTO = new ArrayList<RoleDTO>();

    for (RoleDomain domain : pageDomain) {
      listDTO.add(RoleAssembler.buildDtoDomain(domain));
    }

    Page<RoleDTO> pageDTO = new PageImpl<RoleDTO>(listDTO,
                                                  pageDomain.getPageable(),
                                                  pageDomain.getTotalElements());


    return pageDTO;
  }

}
