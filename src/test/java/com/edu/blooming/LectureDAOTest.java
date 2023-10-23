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
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.persistence.LectureDAO;
import com.edu.blooming.util.PageCriteria;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@TestPropertySource("classpath:database.properties")
@WebAppConfiguration
public class LectureDAOTest {

  private static final Logger logger = LoggerFactory.getLogger(LectureDAOTest.class);

  @Autowired
  private LectureDAO dao;

  @Test
  public void testDAO() {
    // testInsert();
    // testUpdate();
    // testUpdateReplyCount();
    // testUpdateSalesCount();
    // testUpdateLikeCount();
    // testUpdateTotalScore();
    // testGetLectureCount();
    // testGetLectureCountByMemberId();
    // testSelect();
    // testSelectByMemberId();
    // testSelectIsMemberLikeLecture();
    // testSelectByLectureId();
    // testInsertLectureLike();
    // testDeleteLectureLike();
  }

  private void testInsert() {
    LectureVO vo = new LectureVO(0, 1, "강좌 1의 제목", 9000, 0, 0, 0, 0, "테스트 파일 경로", null);
    int result = dao.insert(vo);
    logger.info("lectureId: " + result);
  } // end testInsert()

  private void testUpdate() {
    LectureVO vo = new LectureVO(1, 0, "강좌 1의 수정된 제목", 8000, 0, 0, 0, 0, "수정된 테스트 파일 경로", null);
    int result = dao.update(vo);
    logger.info(result + "행 변경");
  } // end testUpdate()

  private void testUpdateReplyCount() {
    int result = dao.updateReplyCount(1, 1);
    logger.info(result + "행 변경");
  } // end testUpdateReplyCount()

  private void testUpdateSalesCount() {
    int result = dao.updateSalesCount(1, 1);
    logger.info(result + "행 변경");
  } // end testUpdateSalesCount()

  private void testUpdateLikeCount() {
    int result = dao.updateLikeCount(1, 1);
    logger.info(result + "행 변경");
  } // end testUpdateLikeCount()

  private void testUpdateTotalScore() {
    int result = dao.updateReplyTotalScore(1, 1);
    logger.info(result + "행 변경");
  }// end testUpdateLikeCount()

  private void testGetLectureCount() {
    int result = dao.getLectureCount();
    logger.info(result + "행 존재");
  } // end testGetLectureCount()

  private void testGetLectureCountByMemberId() {
    int result = dao.getLectureCount(1);
    logger.info(result + "행 존재");
  } // end testGetLectureCountByMemberId()

  private void testSelectByLectureId() {
    int lectureId = 1;
    LectureVO vo = dao.select(lectureId);
    logger.info("vo = " + vo);
  } // end testSelectByLectureId()

  private void testSelect() {
    PageCriteria criteria = new PageCriteria();
    List<LectureVO> list = dao.select(criteria);
    for (LectureVO vo : list) {
      logger.info("vo : " + vo.toString());
    }
  } // end testSelect()

  private void testSelectByMemberId() {
    PageCriteria criteria = new PageCriteria();
    List<LectureVO> list = dao.select(criteria, 1);
    for (LectureVO vo : list) {
      logger.info("vo : " + vo.toString());
    }
  } // end testSelectByMemberId()

  private void testSelectIsMemberLikeLecture() {
    int memberId = 1;
    int lectureId = 1;
    boolean result = dao.selectIsMemberLikeLecture(memberId, lectureId);
    logger.info("result = " + result);
  }// end testSelectIsMemberLikeLecture()

  private void testInsertLectureLike() {
    int memberId = 1;
    int lectureId = 2;

    int result = dao.insertLike(memberId, lectureId);
    logger.info(result + "행 삽입");
  } // end testInsertLectureLike()

  private void testDeleteLectureLike() {
    int memberId = 1;
    int lectureId = 2;

    int result = dao.deleteLike(memberId, lectureId);
    logger.info(result + "행 삭제");
  } // end testDeleteLectureLike()

} // end LectureDAOTest
