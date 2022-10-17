package com.ms365.middleware.zuulserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms365.middleware.zuulserver.domain.UsuarioDomain;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioDomain, Integer> {
  public List<UsuarioDomain> findAll();

  public Page<UsuarioDomain> findAll(Pageable pageable);

  public Optional<UsuarioDomain> findById(Integer id);
  
  public Optional<UsuarioDomain> findByUserName(String username);//daniel
  
}
