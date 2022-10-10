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
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ms365.middleware.usuarios.domain.RoleDomain;
import com.ms365.middleware.usuarios.domain.UsuarioDomain;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Ignore
public class RoleRepositoryTest {
  private static Logger logger = LoggerFactory.getLogger(RoleRepositoryTest.class);

  @Autowired(required=true)
  private RoleRepository roleRepository;

  public RoleRepositoryTest() {}

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
      List<RoleDomain> list = this.roleRepository.findAll();

      for (RoleDomain dom : list) {
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
      Optional<RoleDomain> opt = this.roleRepository.findById(new Integer(1));

      if (opt.isPresent()) {
        RoleDomain dom = opt.get();
        logger.info("   >> " + dom);

        for (UsuarioDomain usr : dom.getUsuarios()) {
          logger.info("    >> " + usr);
        }
      }
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }

    logger.info("  << @testFindById");
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
