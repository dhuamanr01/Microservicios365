package com.ms365.middleware.zuulserver.security;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class AuthenticationRequest implements Serializable {
  private static final long serialVersionUID = -2896730571205828745L;

  private String user;
  private String password;

  public AuthenticationRequest() {
    super();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + AuthenticationRequest.class.getSimpleName() + ":");
    sb.append("user").append("=").append(this.user).append(", ");
    sb.append("password").append("=").append(this.password);
    sb.append("}");

    return sb.toString();
  }
}
