package com.ms365.middleware.usuarios.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import com.ms365.middleware.usuarios.common.constants.Constants;
import com.ms365.middleware.usuarios.domain.ClienteDomain;
import com.ms365.middleware.usuarios.dto.ClienteDTO;
import com.ms365.middleware.usuarios.utilities.DateUtil;

public class ClienteAssembler {
  public ClienteAssembler() {}

  public static ClienteDTO buildDtoDomain(ClienteDomain domain) throws Exception {
    ClienteDTO dto = null;

    if (domain != null) {
      dto = new ClienteDTO();
      dto.setId(domain.getId());
      dto.setUsuario(UsuarioAssembler.buildDtoDomain(domain.getUsuario()));
      dto.setTipoDocumento(TipoDocumentoAssembler.buildDtoDomain(domain.getTipoDocumento()));
      dto.setNombres(domain.getNombres());
      dto.setApellidos(domain.getApellidos());
      dto.setDireccion(domain.getDireccion());
      dto.setTelefono1(domain.getTelefono1());
      dto.setTelefono2(domain.getTelefono2());
      dto.setEmail(domain.getEmail());
      dto.setPaginaWeb(domain.getPaginaWeb());
      dto.setNumeroDoc(domain.getNumeroDoc());
      dto.setFechaRegistro(DateUtil.format(Constants.Format.DateTime.DATE_TIME,
                                              domain.getFechaRegistro()));
      dto.setFechaModificacion(domain.getFechaModificacion() == null ? null :
                             DateUtil.format(Constants.Format.DateTime.DATE_TIME,
                                             domain.getFechaModificacion()));
    }

    return dto;
  }

  public static List<ClienteDTO> buildDtoDomainCollection(List<ClienteDomain> listDomain) throws Exception {
    List<ClienteDTO> listDTO = null;

    if (listDomain != null) {
      listDTO = new ArrayList<ClienteDTO>();

      for (ClienteDomain domain : listDomain) {
        listDTO.add(ClienteAssembler.buildDtoDomain(domain));
      }
    }

    return listDTO;
  }

  public static List<ClienteDTO> buildDtoDomainCollection(Set<ClienteDomain> setDomain) throws Exception {
    List<ClienteDTO> listDTO = null;

    if (setDomain != null) {
      listDTO = new ArrayList<ClienteDTO>();

      for (ClienteDomain domain : setDomain) {
        listDTO.add(ClienteAssembler.buildDtoDomain(domain));
      }
    }

    return listDTO;
  }

  public static Page<ClienteDTO> buildDtoDomainCollection(Page<ClienteDomain> pageDomain) throws Exception {
    List<ClienteDTO> listDTO = new ArrayList<ClienteDTO>();

    for (ClienteDomain domain : pageDomain) {
      listDTO.add(ClienteAssembler.buildDtoDomain(domain));
    }

    Page<ClienteDTO> pageDTO = new PageImpl<ClienteDTO>(listDTO,
                                                        pageDomain.getPageable(),
                                                        pageDomain.getTotalElements());


    return pageDTO;
  }

}
