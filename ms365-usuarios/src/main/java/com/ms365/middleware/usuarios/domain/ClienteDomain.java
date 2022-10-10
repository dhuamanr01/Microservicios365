package com.ms365.middleware.usuarios.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ms365.middleware.usuarios.common.constants.Constants;
import com.ms365.middleware.usuarios.utilities.DateUtil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name="clientes")
public class ClienteDomain implements Serializable {
  private static final long serialVersionUID = 5525276390417233555L;

  @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

  @ManyToOne(fetch=FetchType.EAGER, optional=true)
  @JoinColumn(name="usuario_id", referencedColumnName="id", nullable=true)
  private UsuarioDomain usuario;

  @ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="tipo_documento_id", referencedColumnName="id", nullable=false)
	private TipoDocumentoDomain tipoDocumento;

	@Column(name="nombres", nullable=false, length=50)
	private String nombres;

	@Column(name="apellidos", nullable=false, length=50)
	private String apellidos;

	@Column(name="direccion", nullable=false, length=255)
	private String direccion;

	@Column(name="telefono1", nullable=false, length=12)
	private String telefono1;

	@Column(name="telefono2", nullable=false, length=12)
	private String telefono2;

	@Column(name="email", nullable=false, length=60)
	private String email;

	@Column(name="pagina_web", nullable=false, length=60)
	private String paginaWeb;

	@Column(name="numero_doc", nullable=false, length=20)
	private String numeroDoc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_registro", nullable=false)
	private Date fechaRegistro;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_modificacion", nullable=true)
	private Date fechaModificacion;

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + ClienteDomain.class.getSimpleName() + ":");
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
    sb.append("fechaRegistro").append("=").append(DateUtil.format(Constants.Format.DateTime.DATE_TIME, this.fechaRegistro)).append(", ");
    sb.append("fechaModificacion").append("=").append(fechaModificacion == null ? "null" : DateUtil.format(Constants.Format.DateTime.DATE_TIME, this.fechaModificacion));
    sb.append("}");

    return sb.toString();
  }

}
