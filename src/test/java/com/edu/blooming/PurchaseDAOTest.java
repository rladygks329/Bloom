package com.edu.blooming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.persistence.LectureDAO;
import com.edu.blooming.persistence.PurchaseDAO;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@TestPropertySource("classpath:application.properties")
@WebAppConfiguration

@Transactional
@Rollback
public class PurchaseDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(PurchaseDAOTest.class);

  @Autowired
  private PurchaseDAO purchaseDAO;

  @Autowired
  private LectureDAO lectureDAO;

  @Test
  public void testInsert() {
    int result = purchaseDAO.insert(1, 1, 1000);
    assertEquals(1, result);
  }

  @Test(expected = DuplicateKeyException.class)
  public void testDuplicationInsert() throws Exception {
    purchaseDAO.insert(1, 1, 1000);
    purchaseDAO.insert(1, 1, 1000);
  }

  @Test
  public void testDelete() {
    purchaseDAO.insert(1, 1, 1000);
    int result = purchaseDAO.delete(1, 1);
    assertEquals(1, result);
  }

  @Test
  public void testSelect() {
    int memberId = 99;
    LectureVO vo =
        new LectureVO(0, 1, "개발자", "강좌 1의 제목", "상세한 강의 설명", 9000, 0, 0, 0, 0, 0, "테스트 파일 경로", null);
    int lectureId = lectureDAO.insert(vo);

    purchaseDAO.insert(memberId, lectureId, 10000);

    List<LectureVO> list = purchaseDAO.select(memberId);
    assertEquals(1, list.size());
  }

  @Test
  public void testSelectCount() {
    purchaseDAO.insert(1, 1, 1000);
    assertTrue(purchaseDAO.selectIsMemberBuyLecture(1, 1));

    purchaseDAO.delete(1, 1);
    assertFalse(purchaseDAO.selectIsMemberBuyLecture(1, 1));
  }

  @Test
  public void testGetSalesByLecture() {
    List<Map<String, Object>> list = purchaseDAO.getSalesByLecture(2, 14);
    for (Map<String, Object> row : list) {
      logger.info(row.toString());
    }
  }

  @Test
  public void testGetMonthlySales() {
    List<Map<String, Object>> list = purchaseDAO.getMonthlySales(2, 14);
    for (Map<String, Object> row : list) {
      logger.info(row.toString());
    }
  }
}
