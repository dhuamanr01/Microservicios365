package com.ms365.middleware.proyectosarchivos.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms365.middleware.proyectosarchivos.domain.ProyectoArchivoDomain;

@Repository
public interface ProyectoArchivoRepository extends JpaRepository<ProyectoArchivoDomain, Integer> {
  public List<ProyectoArchivoDomain> findAll();

  public Page<ProyectoArchivoDomain> findAll(Pageable pageable);

  public Optional<ProyectoArchivoDomain> findById(Integer id);

  public List<ProyectoArchivoDomain> findByProyectoId(Integer proyectoId);

  public List<ProyectoArchivoDomain> findByProyectoIdAndNombre(Integer proyectoId, String nombre);

  public List<ProyectoArchivoDomain> findByProyectoIdAndRuta(Integer proyectoId, String ruta);
}
