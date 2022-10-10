package com.ms365.middleware.proyectos.domain;

import java.io.Serializable;
//import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ms365.middleware.proyectos.common.constants.Constants;
import com.ms365.middleware.proyectos.utilities.DateUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(schema="ms_proyecto", name="proyectos")
public class ProyectoDomain implements Serializable {
  private static final long serialVersionUID = -3915106155870664737L;

  @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

  @Column(name="usuario_id", nullable=false)
  private Integer usuarioId;

	@Column(name="proyecto", nullable=false, length=100)
	private String proyecto;

	@Column(name="descripcion", nullable=false, length=200)
	private String descripcion;

  @Column(name="estado", nullable=false)
	private Boolean estado;

  @Temporal(TemporalType.TIMESTAMP)
	@Column(name="fecha_expiracion", nullable=false)
	private Date fechaExpiracion;

  @Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at", nullable=false)
	private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at", nullable=true)
	private Date updatedAt;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + ProyectoDomain.class.getSimpleName() + ":");
    sb.append("id").append("=").append(this.id).append(", ");
    sb.append("usuarioId").append("=").append(this.usuarioId).append(", ");
    sb.append("proyecto").append("=").append(this.proyecto).append(", ");
    sb.append("descripcion").append("=").append(this.descripcion).append(", ");
    sb.append("estado").append("=").append(this.estado).append(", ");
    sb.append("fechaExpiracion").append("=").append(DateUtil.format(Constants.Format.DateTime.DATE_TIME, this.fechaExpiracion)).append(", ");
    sb.append("createdAt").append("=").append(DateUtil.format(Constants.Format.DateTime.DATE_TIME, this.createdAt)).append(", ");
    sb.append("updatedAt").append("=").append(updatedAt == null ? "null" : DateUtil.format(Constants.Format.DateTime.DATE_TIME, this.updatedAt));
    sb.append("}");

    return sb.toString();
  }

}
