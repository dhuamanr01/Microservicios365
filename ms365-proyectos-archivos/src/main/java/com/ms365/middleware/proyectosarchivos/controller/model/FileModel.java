package com.ms365.middleware.proyectosarchivos.controller.model;

import java.io.Serializable;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import lombok.Data;

@Data
public class FileModel implements Serializable {
  private static final long serialVersionUID = -8706641931467189640L;
  private CommonsMultipartFile file;

  public FileModel() {
    super();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("{" + FileModel.class.getSimpleName() + ":");
    sb.append("file").append("=").append(this.file);
    if (this.file != null) {
      sb.append(",");
      sb.append("original").append("=").append(this.file.getOriginalFilename()).append(",");
      sb.append("size").append("=").append(this.file.getSize()).append(",");
      sb.append("name").append("=").append(this.file.getName());
    }
    sb.append("}");

    return sb.toString();
  }
}
