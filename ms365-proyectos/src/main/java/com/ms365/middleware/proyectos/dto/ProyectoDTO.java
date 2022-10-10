package com.ms365.middleware.proyectos.dto;

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
public class ProyectoDTO implements Serializable {
  private static final long serialVersionUID = -3915106155870664737L;

	private Integer id;

	private UsuarioDTO usuario;

	private String proyecto;

	private String descripcion;

	private Boolean estado;

	@JsonProperty("fecha_expiracion")
	private String fechaExpiracion;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("updated_at")
	private String updatedAt;

  private List<ProyectoArchivoDTO> archivos = new ArrayList<ProyectoArchivoDTO>();

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + ProyectoDTO.class.getSimpleName() + ":");
    sb.append("id").append("=").append(this.id).append(", ");
    sb.append("usuario").append("=").append(this.usuario).append(", ");
    sb.append("proyecto").append("=").append(this.proyecto).append(", ");
    sb.append("descripcion").append("=").append(this.descripcion).append(", ");
    sb.append("estado").append("=").append(this.estado).append(", ");
    sb.append("fechaExpiracion").append("=").append(this.fechaExpiracion).append(", ");
    sb.append("createdAt").append("=").append(this.createdAt).append(", ");
    sb.append("updatedAt").append("=").append(this.updatedAt);
    sb.append("}");

    return sb.toString();
  }

}
