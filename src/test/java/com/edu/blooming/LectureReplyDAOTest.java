package com.edu.blooming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.LectureReplyVO;
import com.edu.blooming.persistence.LectureReplyDAO;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@TestPropertySource("classpath:application.properties")
@WebAppConfiguration

@Transactional
@Rollback
public class LectureReplyDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(LectureReplyDAOTest.class);

  @Autowired
  private LectureReplyDAO dao;

  @Test
  public void testInsert() {
    LectureReplyVO vo = new LectureReplyVO(-1, 1, 1, null, "강의가 좋네요", "닉네임 1", null, null, 5);
    int result = dao.insert(vo);

    assertEquals(1, result);
  }

  @Test
  public void testUpdate() {
    LectureReplyVO vo = new LectureReplyVO(48, 1, 1, null, "변경된 수강평", "닉네임 2", null, null, 3);
    int result = dao.update(vo);

    assertEquals(1, result);
  }

  @Test
  public void testSelectByLectureId() {
    LectureReplyVO vo = new LectureReplyVO(-1, 1, 1, null, "강의가 좋네요", "닉네임 1", null, null, 5);
    dao.insert(vo);
    List<LectureReplyVO> list = dao.selectByLectureId(1);

    assertEquals(1, list.size());
  }

  @Test
  public void testSelectByLectureReplyId() {
    LectureReplyVO vo = dao.selectByLectureReplyId(48);
    assertNotNull(vo);
  }

  @Test
  public void testSelectByInstructorId() {
    List<LectureReplyVO> list = dao.selectByInstructorId(2);
    for (LectureReplyVO vo : list) {
      logger.info(vo.toString());
    }
    assertNotNull(list);
  }

  @Test
  public void testDelete() {
    int result = dao.delete(48);
    assertEquals(1, result);
  }

  // @Test
  public void testPageForInfiniteScroll() {
    List<LectureReplyVO> list = dao.selectPageForInfiniteScroll(22, 0, 1);
    List<LectureReplyVO> list2 = dao.selectPageForInfiniteScroll(22, 78, 1);
    List<LectureReplyVO> list3 = dao.selectPageForInfiniteScroll(22, 90, 1);

    for (LectureReplyVO vo : list) {
      logger.info("list : " + vo.toString());
    }

    for (LectureReplyVO vo : list) {
      logger.info("list2 : " + vo.toString());
    }

    for (LectureReplyVO vo : list) {
      logger.info("list3 : " + vo.toString());
    }
  }

}

