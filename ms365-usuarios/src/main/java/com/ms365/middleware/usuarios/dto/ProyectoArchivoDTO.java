package com.ms365.middleware.usuarios.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ProyectoArchivoDTO implements Serializable {
  private static final long serialVersionUID = 2878697413683046647L;

	private Integer id;

	@JsonProperty("titulo")
	private String nombre;

	@JsonProperty("nombre")
	private String ruta;

	@JsonProperty("created_at")
	private String createdAt;

	@JsonProperty("updated_at")
	private String updatedAt;

	private String contenido;

	@Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + ProyectoArchivoDTO.class.getSimpleName() + ":");
    sb.append("id").append("=").append(this.id).append(", ");
    sb.append("nombre").append("=").append(this.nombre).append(", ");
    sb.append("ruta").append("=").append(this.ruta).append(", ");
    sb.append("createdAt").append("=").append(this.createdAt).append(", ");
    sb.append("updatedAt").append("=").append(this.updatedAt);
    sb.append("}");

    return sb.toString();
  }

}
