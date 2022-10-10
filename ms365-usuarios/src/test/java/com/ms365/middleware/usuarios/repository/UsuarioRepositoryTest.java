package com.ms365.middleware.usuarios.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Order;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ms365.middleware.usuarios.domain.ClienteDomain;
import com.ms365.middleware.usuarios.domain.UsuarioDomain;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Ignore
public class UsuarioRepositoryTest {
  private static Logger logger = LoggerFactory.getLogger(UsuarioRepositoryTest.class);

  @Autowired(required=true)
  private UsuarioRepository usuarioRepository;

  public UsuarioRepositoryTest() {}

  @BeforeClass
  public static void setUpClass() {
    logger.info(">> @BeforeClass");
  }

  @Before
  public void setUp() {
    logger.info(" >> @Before");
  }

  @Test
  @Order(1)
  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public void testFindAll() {
    logger.info("  >> @testFindAll");

    try {
      List<UsuarioDomain> list = this.usuarioRepository.findAll();

      for (UsuarioDomain dom : list) {
        logger.info("   >> " + dom);
      }
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }

    logger.info("  << @testFindAll");
  }

  @Test
  @Order(2)
  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public void testFindById() {
    logger.info("  >> @testFindById");

    try {
      Optional<UsuarioDomain> opt = this.usuarioRepository.findById(new Integer(3));

      if (opt.isPresent()) {
        UsuarioDomain dom = opt.get();
        logger.info("   >> " + dom);

        for (ClienteDomain cli : dom.getClientes()) {
          logger.info("    >> " + cli);
        }
      }
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }

    logger.info("  << @testFindById");
  }

  @Test
  @Order(3)
  @Transactional(Transactional.TxType.REQUIRES_NEW)
  public void testFindAllPage() {
    logger.info("  >> @testFindAllPage");

    try {
      Pageable pageable = PageRequest.of(1 - 1, 3);
      Page<UsuarioDomain> page = this.usuarioRepository.findAll(pageable);
      logger.info("   >> " + page);

      for (UsuarioDomain dom : page) {
        logger.info("   >> " + dom);
      }

      pageable = PageRequest.of(2 - 1, 3);
      page = this.usuarioRepository.findAll(pageable);
      logger.info("   >> " + page);

      for (UsuarioDomain dom : page) {
        logger.info("   >> " + dom);
      }
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }

    logger.info("  << @testFindAllPage");
  }

  @After
  public void tearDown() {
    logger.info(" << @Before");
  }

  @AfterClass
  public static void tearDownClass() {
    logger.info("<< @BeforeClass");
  }
}
