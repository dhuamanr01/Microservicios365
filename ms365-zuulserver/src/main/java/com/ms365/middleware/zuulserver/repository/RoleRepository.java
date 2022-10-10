package com.ms365.middleware.zuulserver.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ms365.middleware.zuulserver.domain.RoleDomain;

@Repository
public interface RoleRepository extends JpaRepository<RoleDomain, Integer> {
  public List<RoleDomain> findAll();

  public Page<RoleDomain> findAll(Pageable pageable);

  public Optional<RoleDomain> findById(Integer id);
}
