package com.ms365.middleware.zuulserver.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServicioDTO implements Serializable {
  private static final long serialVersionUID = 2065925241286815070L;

  private Integer id;
  private String metodo;
  private Integer orden;
  private String url;
  private List<RoleDTO> roles = new ArrayList<RoleDTO>();

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + ServicioDTO.class.getSimpleName() + ":");
    sb.append("id").append("=").append(this.id).append(", ");
    sb.append("metodo").append("=").append(this.metodo).append(", ");
    sb.append("orden").append("=").append(this.orden).append(", ");
    sb.append("url").append("=").append(this.url);
    sb.append("}");

    return sb.toString();
  }
}
