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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="usuarios")
public class UsuarioDomain implements Serializable {
  private static final long serialVersionUID = -4872705658825231581L;

  @Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="rol_id", referencedColumnName="id", nullable=false)
	private RoleDomain role;

	@Column(name="username", nullable=false, length=30)
	private String userName;

	@Column(name="password", nullable=false, length=60)
	private String password;

	@Column(name="estado", nullable=false)
	private Boolean estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at", nullable=false)
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at", nullable=true)
	private Date updatedAt;

  @OneToMany(mappedBy="usuario", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
  private Set<ClienteDomain> clientes = new HashSet<ClienteDomain>(0);

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + UsuarioDomain.class.getSimpleName() + ":");
    sb.append("id").append("=").append(this.id).append(", ");
    sb.append("role").append("=").append(this.role).append(", ");
    sb.append("userName").append("=").append(this.userName).append(", ");
    sb.append("password").append("=").append(this.password).append(", ");
    sb.append("estado").append("=").append(this.estado).append(", ");
    sb.append("createdAt").append("=").append(DateUtil.format(Constants.Format.DateTime.DATE_TIME, this.createdAt)).append(", ");
    sb.append("updatedAt").append("=").append(updatedAt == null ? "null" : DateUtil.format(Constants.Format.DateTime.DATE_TIME, this.updatedAt));
    sb.append("}");

    return sb.toString();
  }

}