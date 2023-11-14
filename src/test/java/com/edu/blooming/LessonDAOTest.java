package com.edu.blooming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import java.util.List;
import java.util.Map;
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
import com.edu.blooming.domain.LessonVO;
import com.edu.blooming.persistence.LessonDAO;

@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@TestPropertySource("classpath:application.properties")
@WebAppConfiguration

@Transactional
@Rollback
public class LessonDAOTest {
  private static final Logger logger = LoggerFactory.getLogger(LessonDAOTest.class);

  @Autowired
  private LessonDAO lessonDAO;

  @Test
  public void testInsert() {
    LessonVO vo = new LessonVO(0, 1, -1, "강좌 1의 첫번째 소제목", "강좌 1-1 영상의 영상 경로");
    int lessonId = lessonDAO.insert(vo);
    assertNotEquals(0, lessonId);
  }

  @Test
  public void testUpdate() {
    // given
    LessonVO vo = new LessonVO(0, 1, -1, "강좌 1의 첫번째 소제목", "강좌 1-1 영상의 영상 경로");
    int lessonId = lessonDAO.insert(vo);

    // when
    vo.setLessonName("수정된 제목");
    vo.setLessonUrl("수정된 url");
    int result = lessonDAO.update(vo);

    // expected
    vo = lessonDAO.selectByLessonId(lessonId);
    assertEquals("수정된 제목", vo.getLessonName());
    assertEquals("수정된 url", vo.getLessonUrl());
    assertEquals(0, vo.getLessonVideoProcessingLevel());
    assertEquals(1, result);
  } // end testUpdate();

  @Test
  public void testUpdateName() {
    // given
    LessonVO vo = new LessonVO(0, 1, -1, "강좌 1의 첫번째 소제목", "강좌 1-1 영상의 영상 경로");
    int lessonId = lessonDAO.insert(vo);
    lessonDAO.updateVideoProcessingLevel(lessonId, 1);

    // when
    int result = lessonDAO.updateLessonName(lessonId, "수정된 제목");

    // expected
    vo = lessonDAO.selectByLessonId(lessonId);
    assertEquals("수정된 제목", vo.getLessonName());
    assertEquals("강좌 1-1 영상의 영상 경로", vo.getLessonUrl());
    assertEquals(1, vo.getLessonVideoProcessingLevel());
    assertEquals(1, result);
  } // end testUpdateName();

  @Test
  public void testUpdateUrl() {
    // given
    LessonVO vo = new LessonVO(0, 1, -1, "강좌 1의 첫번째 소제목", "강좌 1-1 영상의 영상 경로");
    int lessonId = lessonDAO.insert(vo);

    // when
    int result = lessonDAO.updateLessonUrl(lessonId, "수정된 url");

    // expected
    vo = lessonDAO.selectByLessonId(lessonId);
    assertEquals("강좌 1의 첫번째 소제목", vo.getLessonName());
    assertEquals("수정된 url", vo.getLessonUrl());
    assertEquals(0, vo.getLessonVideoProcessingLevel());
    assertEquals(1, result);
  } // end testUpdateUrl();

  @Test
  public void testUpdateVideoProcessingLevel() {
    // given
    LessonVO vo = new LessonVO(0, 1, -1, "강좌 1의 첫번째 소제목", "강좌 1-1 영상의 영상 경로");
    int lessonId = lessonDAO.insert(vo);

    // when
    int result = lessonDAO.updateVideoProcessingLevel(lessonId, 1);

    // expected
    vo = lessonDAO.selectByLessonId(lessonId);
    assertEquals(1, vo.getLessonVideoProcessingLevel());
    assertEquals(1, result);
  } // end testUpdateVideoProcessingLevel();

  public void testSelectAllByLectureId() {
    int size = 3;

    // given
    for (int i = 0; i < size; i++) {
      LessonVO vo = new LessonVO(0, 1, -1, "강좌1-" + (i + 1), "강좌 1-" + (i + 1) + "의 url");
      lessonDAO.insert(vo);
    }

    // when
    List<LessonVO> list = lessonDAO.selectByLectureId(1);

    // expected
    assertNotEquals(null, list);
    assertEquals(size, list.size());
  } // end testSelectAllByLectureId()

  @Test
  public void testSelectByLessonId() {
    // given
    LessonVO vo = new LessonVO(0, 1, -1, "강좌 1의 첫번째 소제목", "강좌 1-1 영상의 영상 경로");
    int lessonId = lessonDAO.insert(vo);

    // when
    vo = lessonDAO.selectByLessonId(lessonId);

    // expected
    assertNotEquals(null, vo);
  } // end testSelectByLessonId()

  @Test
  public void testSelectMinVideoProcessingLevel() {
    int size = 3;
    int lessonId = 0;

    // given
    for (int i = 0; i < size; i++) {
      LessonVO vo = new LessonVO(0, 1, -1, "강좌1-" + (i + 1), "강좌 1-" + (i + 1) + "의 url");
      lessonId = lessonDAO.insert(vo);
      lessonDAO.updateVideoProcessingLevel(lessonId, 1);
    }

    // when - 모두 1일때
    int result = lessonDAO.selectMinVideoProcessingLevel(1);
    assertEquals(1, result);

    // when - 하나라도 작은 값이 있을 때
    lessonDAO.updateVideoProcessingLevel(lessonId, 0);
    result = lessonDAO.selectMinVideoProcessingLevel(1);
    assertEquals(0, result);
  } // end testSelectMinVideoProcessingLevel()

  @Test
  public void testSelectStatus() {
    List<Map<String, Object>> result = lessonDAO.selectLessonStatus(2);
    for (Map<String, Object> row : result) {
      logger.info(row.toString());
    }
  } // end testSelectStatus();

  @Test
  public void testDelete() {
    // given
    LessonVO vo = new LessonVO(0, 1, -1, "강좌 1의 첫번째 소제목", "강좌 1-1 영상의 영상 경로");
    int lessonId = lessonDAO.insert(vo);

    // when
    int result = lessonDAO.delete(lessonId);

    // expected
    assertEquals(1, result);
  }
} // end LessonDAOTest
