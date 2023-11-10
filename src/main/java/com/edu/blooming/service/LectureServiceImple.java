package com.edu.blooming.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.domain.LessonVO;
import com.edu.blooming.event.VideoUploadedEvent;
import com.edu.blooming.exception.AlreadyExistException;
import com.edu.blooming.persistence.CartDAO;
import com.edu.blooming.persistence.LectureDAO;
import com.edu.blooming.persistence.LessonDAO;
import com.edu.blooming.persistence.PurchaseDAO;
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

  @Autowired
  private CartDAO cartDAO;

  @Autowired
  private PurchaseDAO purchaseDAO;

  @Transactional(value = "transactionManager")
  @Override
  public int create(LectureVO vo, List<LessonVO> lessons) {
    logger.info("create() 호출 : vo = " + vo.toString() + " lessons.size() : " + lessons.size());
    int lectureId = lectureDAO.insert(vo);
    for (LessonVO lesson : lessons) {
      lesson.setLectureId(lectureId);
      int lessonId = lessonDAO.insert(lesson);
      publisher
          .publishEvent(new VideoUploadedEvent(this, lesson.getLessonUrl(), lectureId, lessonId));
    }
    return 1;
  }

  @Override
  public int update(LectureVO vo) {
    logger.info("update() 호출 : vo = " + vo.toString());
    return lectureDAO.update(vo);
  }

  @Override
  public List<LectureVO> read(PageCriteria criteria, int orderType) {
    logger.info("read() 호출");
    logger.info("start = " + criteria.getStart());
    logger.info("end = " + criteria.getEnd());
    return lectureDAO.select(criteria, orderType);
  }

  @Override
  public List<LectureVO> read(PageCriteria criteria, String keyword, int orderType) {
    logger.info("read() 호출 keyword: " + keyword);
    logger.info("start = " + criteria.getStart());
    logger.info("end = " + criteria.getEnd());
    return lectureDAO.select(criteria, keyword, orderType);
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

  @Override
  public Map<String, Object> getUserStatus(int memberId, int lectureId) {
    Map<String, Object> result = new HashMap<>();

    result.put("isLike", lectureDAO.selectIsMemberLikeLecture(memberId, lectureId));
    result.put("isPurchase", purchaseDAO.selectIsMemberBuyLecture(memberId, lectureId));
    result.put("isCart", cartDAO.selectExist(memberId, lectureId) == 1);

    return result;
  }

  @Transactional(value = "transactionManager")
  @Override
  public int likeLecture(int memberId, int lectureId) throws AlreadyExistException {
    logger.info("likeLecture() 호출, LectureId : " + lectureId + " memberId : " + memberId);
    int result = 0;

    try {
      result = lectureDAO.insertLike(memberId, lectureId);
    } catch (DataIntegrityViolationException e) {
      throw new AlreadyExistException("이미 좋아요를 누르셨습니다.");
    }
    lectureDAO.updateLikeCount(lectureId, 1);
    logger.info("result : " + result);
    return result;
  }

  @Transactional(value = "transactionManager")
  @Override
  public int dislikeLecture(int memberId, int lectureId) {
    logger.info("dislikeLecture() 호출, LectureId : " + lectureId + " memberId : " + memberId);
    int result = lectureDAO.deleteLike(memberId, lectureId);
    if (result == 1) {
      lectureDAO.updateLikeCount(lectureId, -1);
    }
    return result;
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

  @Override
  public List<LectureVO> readHotLikeLectures(int month, int rank) {
    logger.info("readHotLikeLectures() 호출");
    return lectureDAO.selectHotLikeLecture(month, rank);
  }

  @Override
  public List<LectureVO> readHotSaleLectures(int month, int rank) {
    logger.info("readHotSaleLectures() 호출");
    return lectureDAO.selectHotSaleLecture(month, rank);
  }

}
