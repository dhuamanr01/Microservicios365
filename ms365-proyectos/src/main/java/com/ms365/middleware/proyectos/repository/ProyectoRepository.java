package com.ms365.middleware.proyectos.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ms365.middleware.proyectos.domain.ProyectoDomain;
import com.ms365.middleware.proyectos.dto.ProyectoDTO;

@Repository
public interface ProyectoRepository extends JpaRepository<ProyectoDomain, Integer> {
//  public List<ProyectoDomain> findAll(ProyectoDTO dto, Map<String, String> headers);
  public List<ProyectoDomain> findAll();

//  public Page<ProyectoDomain> findAll(ProyectoDTO dto, Map<String, String> headers, Pageable pageable);
  public Page<ProyectoDomain> findAll(Pageable pageable);

  public Optional<ProyectoDomain> findById(Integer id);

  public List<ProyectoDomain> findByUsuarioId(Integer usuarioId);

  public List<ProyectoDomain> findByProyectoAndEstado(String proyecto, Boolean estado);

  public List<ProyectoDomain> findByProyectoAndIdNotAndEstado(String proyecto, Integer id, Boolean estado);

  @Modifying
  @Query("UPDATE ProyectoDomain set estado = 'f', updatedAt = now() " +
         "where estado = 't' and to_char(fechaExpiracion, 'YYYYMMDDHHMISS') < to_char(now(), 'YYYYMMDDHHMISS')")
  int updateExpiration();
}
