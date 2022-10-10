package com.ms365.middleware.zuulserver.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ms365.middleware.zuulserver.domain.ServicioDomain;

@Repository
public interface ServicioRepository extends JpaRepository<ServicioDomain, Integer>{
  public List<ServicioDomain> findAll(Sort sort);

  @Query(value = "SELECT s FROM ServicioDomain s WHERE s.id <> 100 AND upper(s.metodo) = upper(:metodo)")
  public List<ServicioDomain> findByMetodo(@Param(value="metodo") String metodo, Sort sort);
}
