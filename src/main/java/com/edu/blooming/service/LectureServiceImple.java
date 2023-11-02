package com.edu.blooming.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.domain.LessonVO;
import com.edu.blooming.event.VideoUploadedEvent;
import com.edu.blooming.persistence.LectureDAO;
import com.edu.blooming.persistence.LessonDAO;
import com.edu.blooming.util.PageCriteria;

@Service
public class LectureServiceImple implements LectureService {
  private static final Logger logger = LoggerFactory.getLogger(LectureServiceImple.class);

  @Autowired
  ApplicationEventPublisher publisher;

  @Autowired
  private LectureDAO lectureDAO;

  @Autowired
  private LessonDAO lessonDAO;

  @Transactional(value = "transactionManager")
  @Override
  public int create(LectureVO vo, List<LessonVO> lessons) {
    logger.info("create() 호출 : vo = " + vo.toString() + " lessons.size() : " + lessons.size());
    int lectureId = lectureDAO.insert(vo);
    for (LessonVO lesson : lessons) {
      lesson.setLectureId(lectureId);
      lessonDAO.insert(lesson);
      publisher.publishEvent(new VideoUploadedEvent(this, lesson.getLessonUrl()));
    }
    return 1;
  }

  @Override
  public int update(LectureVO vo) {
    logger.info("update() 호출 : vo = " + vo.toString());
    return lectureDAO.update(vo);
  }

  @Override
  public List<LectureVO> read(PageCriteria criteria) {
    logger.info("read() 호출");
    logger.info("start = " + criteria.getStart());
    logger.info("end = " + criteria.getEnd());
    return lectureDAO.select(criteria);
  }

  @Override
  public List<LectureVO> read(PageCriteria criteria, String keyword) {
    logger.info("read() 호출 keyword: " + keyword);
    logger.info("start = " + criteria.getStart());
    logger.info("end = " + criteria.getEnd());
    return lectureDAO.select(criteria, keyword);
  }

  @Override
  public boolean checkIsLike(int memberId, int lectureId) {
    logger.info("checkIsLike() 호출");
    return lectureDAO.selectIsMemberLikeLecture(memberId, lectureId);
  }

  @Override
  public List<LectureVO> findLectureByAuthorId(PageCriteria criteria, int authorId) {
    logger.info("findLectureByAuthorId() 호출");
    logger.info("start = " + criteria.getStart());
    logger.info("end = " + criteria.getEnd());
    return lectureDAO.select(criteria, authorId);
  }

  @Override
  public LectureVO read(int lectureId) {
    logger.info("read() 호출 : lectureId: " + lectureId);
    return lectureDAO.select(lectureId);
  }

  @Transactional(value = "transactionManager")
  @Override
  public int likeLecture(int lectureId, int memberId) {
    logger.info("likeLecture() 호출, LectureId : " + lectureId + " memberId : " + memberId);
    lectureDAO.updateLikeCount(lectureId, 1);
    return lectureDAO.insertLike(memberId, lectureId);
  }

  @Transactional(value = "transactionManager")
  @Override
  public int dislikeLecture(int lectureId, int memberId) {
    logger.info("dislikeLecture() 호출, LectureId : " + lectureId + " memberId : " + memberId);
    lectureDAO.updateLikeCount(lectureId, -1);
    return lectureDAO.deleteLike(memberId, lectureId);
  }

  @Override
  public int getTotalCounts() {
    logger.info("getTotalCounts() 호출");
    return lectureDAO.getLectureCount();
  }

  @Override
  public int getTotalCounts(int authorId) {
    logger.info("getTotalCounts() 호출 : memberId : " + authorId);
    return lectureDAO.getLectureCount(authorId);
  }

  @Override
  public int getTotalCounts(String keyword) {
    logger.info("getTotalCounts() 호출 : keyword : " + keyword);
    return lectureDAO.getLectureCount(keyword);
  }

}
