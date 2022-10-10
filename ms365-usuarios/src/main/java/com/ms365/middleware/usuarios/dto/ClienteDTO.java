package com.ms365.middleware.usuarios.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
public class ClienteDTO implements Serializable {
  private static final long serialVersionUID = 5525276390417233555L;

	private Integer id;

  private UsuarioDTO usuario;

	private TipoDocumentoDTO tipoDocumento;

	private String nombres;

	private String apellidos;

	private String direccion;

	private String telefono1;

	private String telefono2;

	private String email;

	private String paginaWeb;

	private String numeroDoc;

	@JsonProperty("fecha_registro")
	private String fechaRegistro;

	@JsonProperty("fecha_modificacion")
	private String fechaModificacion;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + ClienteDTO.class.getSimpleName() + ":");
    sb.append("id").append("=").append(this.id).append(", ");
    sb.append("usuario").append("=").append(this.usuario).append(", ");
    sb.append("tipoDocumento").append("=").append(this.tipoDocumento).append(", ");
    sb.append("nombres").append("=").append(this.nombres).append(", ");
    sb.append("apellidos").append("=").append(this.apellidos).append(", ");
    sb.append("direccion").append("=").append(this.direccion).append(", ");
    sb.append("telefono1").append("=").append(this.telefono1).append(", ");
    sb.append("telefono2").append("=").append(this.telefono2).append(", ");
    sb.append("email").append("=").append(this.email).append(", ");
    sb.append("paginaWeb").append("=").append(this.paginaWeb).append(", ");
    sb.append("numeroDoc").append("=").append(this.numeroDoc).append(", ");
    sb.append("fechaRegistro").append("=").append(this.fechaRegistro).append(", ");
    sb.append("fechaModificacion").append("=").append(this.fechaModificacion);
    sb.append("}");

    return sb.toString();
  }

}
