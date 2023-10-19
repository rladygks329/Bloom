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
import com.edu.blooming.domain.LectureReplyVO;
import com.edu.blooming.persistence.LectureReplyDAO;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@WebAppConfiguration
public class LectureReplyDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(LectureReplyDAOTest.class);

  @Autowired
  private LectureReplyDAO dao;

  @Test
  public void testDAO() {
    // testInsert();
    // testUpdate();
    // testSelectByLectureId();
    // testSelectByLectureReplyId();
    // testDelete();
  }

  private void testInsert() {
    LectureReplyVO vo = new LectureReplyVO(-1, 1, 1, "강의가 좋네요", 5);
    int result = dao.insert(vo);
    logger.info(result + "행 삽입");
  }

  private void testUpdate() {
    LectureReplyVO vo = new LectureReplyVO(5, 1, 1, "변경된 수강평", 3);
    int result = dao.update(vo);
    logger.info(result + "행 삽입");
  }

  private void testSelectByLectureId() {
    List<LectureReplyVO> list = dao.selectByLectureId(1);
    for (LectureReplyVO vo : list) {
      logger.info(vo.toString());
    }
  }

  private void testSelectByLectureReplyId() {
    LectureReplyVO vo = dao.selectByLectureReplyId(5);
    logger.info(vo.toString());
  }

  private void testDelete() {
    int result = dao.delete(6);
    logger.info(result + "행 삭제");
  }

}

