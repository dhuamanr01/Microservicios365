package com.ms365.middleware.usuarios.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ms365.middleware.usuarios.common.constants.Constants;
import com.ms365.middleware.usuarios.utilities.DateUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"clientes"})
@Entity
@Table(name="tipos_documentos")
public class TipoDocumentoDomain implements Serializable {
  private static final long serialVersionUID = 3894008995313578648L;

  @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="descripcion", nullable=false, length=20)
	private String descripcion;

	@Temporal(TemporalType.TIMESTAMP)
  @Column(name="created_at", nullable=false)
  private Date createdAt;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name="updated_at", nullable=true)
  private Date updatedAt;

  @OneToMany(mappedBy="tipoDocumento", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
  private Set<ClienteDomain> clientes = new HashSet<ClienteDomain>(0);

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + TipoDocumentoDomain.class.getSimpleName() + ":");
    sb.append("id").append("=").append(this.id).append(", ");
    sb.append("descripcion").append("=").append(this.descripcion).append(", ");
    sb.append("createdAt").append("=").append(DateUtil.format(Constants.Format.DateTime.DATE_TIME, this.createdAt)).append(", ");
    sb.append("updatedAt").append("=").append(updatedAt == null ? "null" : DateUtil.format(Constants.Format.DateTime.DATE_TIME, this.updatedAt));
    sb.append("}");

    return sb.toString();
  }

}
