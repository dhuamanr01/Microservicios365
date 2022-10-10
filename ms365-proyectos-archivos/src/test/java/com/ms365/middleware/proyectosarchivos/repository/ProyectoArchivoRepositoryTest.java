package com.ms365.middleware.proyectosarchivos.repository;

import java.util.List;

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

import com.ms365.middleware.proyectosarchivos.domain.ProyectoArchivoDomain;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Ignore
public class ProyectoArchivoRepositoryTest {
  private static Logger logger = LoggerFactory.getLogger(ProyectoArchivoRepositoryTest.class);

  @Autowired(required=true)
  private ProyectoArchivoRepository proyectoArchivoRepository;

  public ProyectoArchivoRepositoryTest() {}

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
      List<ProyectoArchivoDomain> list = this.proyectoArchivoRepository.findAll();

      for (ProyectoArchivoDomain dom : list) {
        logger.info("   >> " + dom);
      }
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }

    logger.info("  << @testFindAll");
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
