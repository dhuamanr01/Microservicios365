package com.ms365.middleware.zuulserver.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class RoleDTO implements Serializable {
  private static final long serialVersionUID = -8301257302917630690L;

	private Integer id;

	private String nombre;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("updated_at")
	private String updatedAt;

	private String auth;

  private List<UsuarioDTO> usuarios = new ArrayList<UsuarioDTO>();

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + RoleDTO.class.getSimpleName() + ":");
    sb.append("id").append("=").append(this.id).append(", ");
    sb.append("nombre").append("=").append(this.nombre).append(", ");
    sb.append("auth").append("=").append(this.auth).append(", ");
    sb.append("createdAt").append("=").append(createdAt).append(", ");
    sb.append("updatedAt").append("=").append(updatedAt);
    sb.append("}");

    return sb.toString();
  }

}
