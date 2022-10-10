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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Ignore
public class ClienteServiceTest {
  private static Logger logger = LoggerFactory.getLogger(ClienteServiceTest.class);

  @Autowired(required=true)
  private ClienteService clienteService;

  public ClienteServiceTest() {}

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
      List<ClienteDTO> list = this.clienteService.findAll();

      for (ClienteDTO dto : list) {
        logger.info("   >> " + dto);
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
