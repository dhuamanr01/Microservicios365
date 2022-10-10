package com.ms365.middleware.usuarios.dto;

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
public class TipoDocumentoDTO implements Serializable {
  private static final long serialVersionUID = 3894008995313578648L;

	private Integer id;

	private String descripcion;

	@JsonProperty("created_at")
  private String createdAt;

  @JsonProperty("updated_at")
  private String updatedAt;

  private List<ClienteDTO> clientes = new ArrayList<ClienteDTO>();

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + TipoDocumentoDTO.class.getSimpleName() + ":");
    sb.append("id").append("=").append(this.id).append(", ");
    sb.append("descripcion").append("=").append(this.descripcion).append(", ");
    sb.append("createdAt").append("=").append(this.createdAt).append(", ");
    sb.append("updatedAt").append("=").append(this.updatedAt);
    sb.append("}");

    return sb.toString();
  }

}
