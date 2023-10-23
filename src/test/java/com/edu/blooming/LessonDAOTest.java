package com.edu.blooming;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.edu.blooming.domain.LessonVO;
import com.edu.blooming.persistence.LessonDAO;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@TestPropertySource("classpath:database.properties")
@WebAppConfiguration
public class LessonDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(LessonDAOTest.class);

  @Autowired
  private LessonDAO dao;

  @Test
  public void testDAO() {
    // testInsert();
    // testSelectAllByLectureId();
    // testSelectByLessonId();
    // testUpdate();
    // testDelete();
  }

  private void testInsert() {
    LessonVO vo = new LessonVO(0, 1, "강좌 1의 첫번째 소제목", "강좌 1-1 영상의 영상 경로");
    int result = dao.insert(vo);
    logger.info(result + "행 삽입");
  } // end testInsert()

  private void testSelectAllByLectureId() {
    List<LessonVO> list = dao.selectByLectureId(1);
    logger.info(list.size() + "");
    for (LessonVO vo : list) {
      logger.info(vo.toString());
    }
  } // end testSelectAllByLectureId()

  private void testSelectByLessonId() {
    LessonVO vo = dao.selectByLessonId(1);
    logger.info("vo : " + vo);
  } // end testSelectAllByLessonId()

  private void testUpdate() {
    LessonVO vo = new LessonVO(1, 1, "강좌 1의 수정된 첫번째 소제목", "강좌 1-1 영상의 영상 경로");
    int result = dao.update(vo);
    logger.info(result + "행 수정");
  }

  private void testDelete() {
    int result = dao.delete(1);
    logger.info(result + "행 삭제");
  }
} // end LessonDAOTest
