package com.edu.blooming;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.persistence.CartDAO;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@WebAppConfiguration
public class CartDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(CartDAOTest.class);

  @Autowired
  private CartDAO dao;

  @Test
  public void testDAO() {
    // testInsert();
    // testDelete();
    // testSelect();
    // testCalcTotal();
  }

  private void testInsert() {
    int result = dao.insert(1, 2);
    logger.info(result + "행 추가");
  }

  private void testDelete() {
    int result = dao.delete(1, 1);
    logger.info(result + "행 삭제");
  }

  private void testSelect() {
    List<LectureVO> list = dao.select(1);
    for (LectureVO vo : list) {
      logger.info(vo.toString());
    }
  }

  private void testCalcTotal() {
    int result = dao.calcTotal(1);
    logger.info("result : " + result);
  }

}
