package com.ms365.middleware.usuarios.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms365.middleware.usuarios.domain.ClienteDomain;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteDomain, Integer> {
  public List<ClienteDomain> findAll();

  public Page<ClienteDomain> findAll(Pageable pageable);

  public Optional<ClienteDomain> findById(Integer id);
}
