package com.edu.blooming;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.domain.LectureVOBuilder;
import com.edu.blooming.persistence.CartDAO;
import com.edu.blooming.persistence.LectureDAO;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@TestPropertySource("classpath:database.properties")
@WebAppConfiguration

@Transactional
@Rollback
public class CartDAOTest {

  @Autowired
  private CartDAO cartDAO;

  @Autowired
  private LectureDAO lectureDAO;

  @Test
  public void testInsert() {
    int result = cartDAO.insert(1, 1);
    assertEquals(result, 1);
  }

  @Test(expected = DuplicateKeyException.class)
  public void testDuplicationInsert() throws Exception {
    cartDAO.insert(1, 1);
    cartDAO.insert(1, 1);
  }

  @Test
  public void testDelete() {
    cartDAO.insert(1, 1);
    int result = cartDAO.delete(1, 1);
    assertEquals(result, 1);
  }

  @Test
  public void testDeleteAll() {
    cartDAO.insert(1, 1);
    cartDAO.insert(1, 2);
    int result = cartDAO.delete(1);
    assertEquals(result, 2);
  }

  @Test
  public void testSelectExist() {
    cartDAO.insert(1, 1);
    int result = cartDAO.selectExist(1, 1);
    assertEquals(result, 1);

    cartDAO.delete(1, 1);
    result = cartDAO.selectExist(1, 1);
    assertEquals(result, 0);
  }

  @Test
  public void testSelect() {
    int size = 3;

    for (int i = 0; i < size; i++) {
      // @formatter:off
      int lectureId = lectureDAO.insert(
          new LectureVOBuilder()
          .lectureTitle("테스트용 강의" + (i+1))
          .lectureDescription("테스트 설명"+ (i+1))
          .lectureThumbnailUrl("test_url")
          .lecturePrice(2000)
          .build()
      );
      //@formatter:on
      cartDAO.insert(1, lectureId);
    }

    List<LectureVO> list = cartDAO.select(1);
    assertEquals(list.size(), size);
  }

  @Test
  public void testCalcTotal() {
    int size = 3;
    int price = 2000;

    for (int i = 0; i < size; i++) {
      int lectureId = lectureDAO.insert(
      // @formatter:off
          new LectureVOBuilder()
          .lectureTitle("테스트용 강의" + (i+1))
          .lectureDescription("테스트 설명"+ (i+1))
          .lectureThumbnailUrl("test_url")
          .lecturePrice(price)
          .build()
        //@formatter:on
      );
      cartDAO.insert(1, lectureId);
    }

    int total = cartDAO.calcTotal(1);
    assertEquals(total, price * size);
  }

}
