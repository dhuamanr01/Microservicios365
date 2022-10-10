package com.ms365.middleware.zuulserver.domain;

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

import com.ms365.middleware.zuulserver.common.constants.Constants;
import com.ms365.middleware.zuulserver.utilities.DateUtil;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude={"usuarios"})
@Entity
@Table(name="roles")
public class RoleDomain implements Serializable {
  private static final long serialVersionUID = -8301257302917630690L;

  @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="nombre", length=30, nullable=false)
	private String nombre;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at", nullable=false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at", nullable=true)
	private Date updatedAt;

	@Column(name="auth", length=30, nullable=false)
  private String auth;

	@OneToMany(mappedBy="role", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
  private Set<UsuarioDomain> usuarios = new HashSet<UsuarioDomain>(0);

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + RoleDomain.class.getSimpleName() + ":");
    sb.append("id").append("=").append(this.id).append(", ");
    sb.append("nombre").append("=").append(this.nombre).append(", ");
    sb.append("auth").append("=").append(this.auth).append(", ");
    sb.append("createdAt").append("=").append(DateUtil.format(Constants.Format.DateTime.DATE_TIME, this.createdAt)).append(", ");
    sb.append("updatedAt").append("=").append(updatedAt == null ? "null" : DateUtil.format(Constants.Format.DateTime.DATE_TIME, this.updatedAt));
    sb.append("}");

    return sb.toString();
  }

}
