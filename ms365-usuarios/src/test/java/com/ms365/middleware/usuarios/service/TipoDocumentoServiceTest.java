package com.ms365.middleware.usuarios.service;

import java.util.List;

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

import com.ms365.middleware.usuarios.dto.ClienteDTO;
import com.ms365.middleware.usuarios.dto.TipoDocumentoDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Ignore
public class TipoDocumentoServiceTest {
  private static Logger logger = LoggerFactory.getLogger(TipoDocumentoServiceTest.class);

  @Autowired(required=true)
  private TipoDocumentoService tipoDocumentoService;

  public TipoDocumentoServiceTest() {}

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
  public void testFindAll() {
    logger.info("  >> @testFindAll");

    try {
      List<TipoDocumentoDTO> list = this.tipoDocumentoService.findAll();

      for (TipoDocumentoDTO dto : list) {
        logger.info("   >> " + dto);
      }
    }
    catch (Exception ex) {
      logger.error(ex.getMessage(), ex);
    }

    logger.info("  << @testFindAll");
  }

  @Test
  @Order(2)
  public void testFindById() {
    logger.info("  >> @testFindById");

    try {
      TipoDocumentoDTO dto = this.tipoDocumentoService.findById(new Integer(2), true);
      logger.info("   >> " + dto);

      for (ClienteDTO cli : dto.getClientes()) {
        logger.info("    >> " + cli);
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
