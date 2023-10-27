package com.edu.blooming.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.blooming.domain.LessonVO;
import com.edu.blooming.persistence.LessonDAO;

@Service
public class LessonServiceImple implements LessonService {
  private static final Logger logger = LoggerFactory.getLogger(LessonServiceImple.class);

  @Autowired
  private LessonDAO lessonDAO;

  /*
   * @param : LessonVO -> lectureId를 controller에서 채워져서 와야한다.
   */
  @Override
  public int create(LessonVO vo) {
    logger.info("create() 호출 vo : " + vo.toString());
    return lessonDAO.insert(vo);
  }

  /*
   * @param : LessonVO -> lectureId를 controller에서 채워져서 와야한다.
   */
  @Override
  public int update(LessonVO vo) {
    logger.info("update() 호출 vo : " + vo.toString());
    return lessonDAO.update(vo);
  }

  // 강의 영상 조회
  @Override
  public List<LessonVO> getByLectureId(int lectureId) {
    logger.info("getByLectureId() 호출 lectureId : " + lectureId);
    return lessonDAO.selectByLectureId(lectureId);
  }

  @Override
  public LessonVO getByLessonId(int lessonId) {
    logger.info("getByLessonId() 호출 lessonId : " + lessonId);
    return lessonDAO.selectByLessonId(lessonId);
  }
}
