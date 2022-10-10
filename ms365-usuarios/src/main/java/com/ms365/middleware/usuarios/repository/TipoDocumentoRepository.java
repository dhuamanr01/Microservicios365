package com.ms365.middleware.usuarios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms365.middleware.usuarios.domain.TipoDocumentoDomain;

@Repository
public interface TipoDocumentoRepository extends JpaRepository<TipoDocumentoDomain, Integer> {
  public List<TipoDocumentoDomain> findAll();

  public Page<TipoDocumentoDomain> findAll(Pageable pageable);

  public Optional<TipoDocumentoDomain> findById(Integer id);
}
