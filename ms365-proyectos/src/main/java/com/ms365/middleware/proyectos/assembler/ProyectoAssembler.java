package com.ms365.middleware.proyectos.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.ms365.middleware.proyectos.common.constants.Constants;
import com.ms365.middleware.proyectos.domain.ProyectoDomain;
import com.ms365.middleware.proyectos.dto.ProyectoDTO;
import com.ms365.middleware.proyectos.dto.UsuarioDTO;
import com.ms365.middleware.proyectos.utilities.DateUtil;

public class ProyectoAssembler {
  public ProyectoAssembler() {}

  public static ProyectoDTO buildDtoDomain(ProyectoDomain domain) throws Exception {
    ProyectoDTO dto = null;
   
    if (domain != null) {
      dto = new ProyectoDTO();
      dto.setId(domain.getId());
//      dto.setUsuario(domain.getUsuarioId());//daniel
      dto.setProyecto(domain.getProyecto());
      dto.setDescripcion(domain.getDescripcion());
      dto.setEstado(domain.getEstado());
      dto.setFechaExpiracion(DateUtil.format(Constants.Format.DateTime.DATE_TIME,
                                              domain.getFechaExpiracion()));
      dto.setCreatedAt(DateUtil.format(Constants.Format.DateTime.DATE_TIME,
                                              domain.getCreatedAt()));
      dto.setUpdatedAt(domain.getUpdatedAt() == null ? null :
                             DateUtil.format(Constants.Format.DateTime.DATE_TIME,
                                             domain.getUpdatedAt()));
    }

    return dto;
  }
//daniel
  public static List<ProyectoDTO> buildDtoDomainCollection(List<ProyectoDomain> listDomain) throws Exception {
    List<ProyectoDTO> listDTO = null;

    if (listDomain != null) {
      listDTO = new ArrayList<ProyectoDTO>();

      for (ProyectoDomain domain : listDomain) {
//        listDTO.add(ProyectoAssembler.buildDtoDomain(domain, usuarioDTO));//daniel
        listDTO.add(ProyectoAssembler.buildDtoDomain(domain));
      }
    }

    return listDTO;
  }

  public static List<ProyectoDTO> buildDtoDomainCollection(Set<ProyectoDomain> setDomain, UsuarioDTO usuarioDTO) throws Exception {
    List<ProyectoDTO> listDTO = null;

    if (setDomain != null) {
      listDTO = new ArrayList<ProyectoDTO>();

      for (ProyectoDomain domain : setDomain) {
//        listDTO.add(ProyectoAssembler.buildDtoDomain(domain, usuarioDTO));
        listDTO.add(ProyectoAssembler.buildDtoDomain(domain));
      }
    }

    return listDTO;
  }

  public static Page<ProyectoDTO> buildDtoDomainCollection(Page<ProyectoDomain> pageDomain) throws Exception {
    List<ProyectoDTO> listDTO = new ArrayList<ProyectoDTO>();

    for (ProyectoDomain domain : pageDomain) {
      listDTO.add(ProyectoAssembler.buildDtoDomain(domain));
    }

    Page<ProyectoDTO> pageDTO = new PageImpl<ProyectoDTO>(listDTO,
                                                          pageDomain.getPageable(),
                                                          pageDomain.getTotalElements());


    return pageDTO;
  }

}
