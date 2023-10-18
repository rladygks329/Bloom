package com.edu.blooming;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.edu.blooming.persistence.PurchaseDAO;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@WebAppConfiguration
public class PurchaseDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(PurchaseDAOTest.class);

  @Autowired
  private PurchaseDAO dao;

  @Test
  public void testDAO() {
    // testInsert();
    // testDelete();
  }

  private void testInsert() {
    int result = dao.insert(1, 1, 2000);
    logger.info(result + "행 추가");
  }

  private void testDelete() {
    int result = dao.delete(1, 1);
    logger.info(result + "행 삭제");
  }

}
