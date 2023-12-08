package com.edu.blooming.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.domain.LessonVO;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.event.VideoUploadedEvent;
import com.edu.blooming.exception.AlreadyExistException;
import com.edu.blooming.persistence.CartDAO;
import com.edu.blooming.persistence.LectureDAO;
import com.edu.blooming.persistence.LessonDAO;
import com.edu.blooming.persistence.MemberDAO;
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

  @Autowired
  private MemberDAO memberDAO;

  @Transactional(value = "transactionManager")
  @Override
  public int create(LectureVO vo, List<LessonVO> lessons) {
    logger.info("create() 호출 : vo = " + vo.toString() + " lessons.size() : " + lessons.size());
    int lectureId = lectureDAO.insert(vo);
    for (LessonVO lesson : lessons) {
      lesson.setLectureId(lectureId);
      lessonDAO.append(lesson);
      publisher.publishEvent(new VideoUploadedEvent(this, lesson));
    }
    return 1;
  }

  @Override
  public int update(LectureVO lecture, List<LessonVO> lessons) {
    logger.info("update() 호출 : lecture = " + lecture.toString());
    int lectureId = lecture.getLectureId();

    for (LessonVO lesson : lessons) {
      if (lesson.getLessonId() == 0) {
        lectureDAO.updateVideoProcessingLevel(lectureId, 0);
        lessonDAO.insert(lesson);
        publisher.publishEvent(new VideoUploadedEvent(this, lesson));
      } else {
        lessonDAO.update(lesson);
      }
    }

    List<Integer> oldLessonIds = lessonDAO.selectByLectureId(lectureId).stream()
        .map(LessonVO::getLessonId).collect(Collectors.toList());
    List<Integer> newLessonIds =
        lessons.stream().map(LessonVO::getLessonId).collect(Collectors.toList());
    oldLessonIds.removeAll(newLessonIds);
    for (int lessonId : oldLessonIds) {
      lessonDAO.delete(lessonId);
    }

    return lectureDAO.update(lecture);
  }

  @Override
  public MemberVO getInstructorInfo(int memberId) {
    logger.info("강사 Id: " + memberId);

    /*
     * MemberVO instructor = memberDAO.select(null); return instructor;
     */
    MemberVO vo = new MemberVO();
    vo.setMemberName("임시이름");
    vo.setMemberProfileUrl("임시 사진");
    vo.setMemberIntroduce("임시 설명글");

    return vo;
  }

  @Override
  public List<LectureVO> read(PageCriteria criteria, String keyword, int orderType) {
    logger.info("read() 호출 keyword: " + keyword);
    logger.info("start = " + criteria.getStart());
    logger.info("end = " + criteria.getEnd());

    if (keyword == null || keyword.isBlank()) {
      return lectureDAO.select(criteria, orderType);
    }

    if (keyword.startsWith("writer.")) {
      String memberName = keyword.replaceFirst("^writer.", "");
      return lectureDAO.selectByAuthorName(criteria, memberName, orderType);
    }

    keyword = keyword.replaceFirst("^content.", "");
    return lectureDAO.select(criteria, keyword, orderType);
  }

  @Override
  public LectureVO read(int lectureId) {
    logger.info("read() 호출 : lectureId: " + lectureId);
    return lectureDAO.select(lectureId);
  }

  @Override
  public Map<String, Object> getUserStatus(int memberId, int lectureId) {
    Map<String, Object> result = new HashMap<>();

    result.put("likeStatus", lectureDAO.selectIsMemberLikeLecture(memberId, lectureId));
    result.put("purchaseStatus", purchaseDAO.selectIsMemberBuyLecture(memberId, lectureId));
    result.put("cartStatus", cartDAO.selectExist(memberId, lectureId));

    return result;
  }

  @Override
  public int getTotalCountsByMemberName(String memberName) {
    logger.info("getTotalCountsByMemberName() 호출 : memberName : " + memberName);
    return lectureDAO.getLectureCountByMemberName(memberName);
  }

  @Override
  public int getTotalCountsByKeyword(String keyword) {
    logger.info("getTotalCounts() 호출 : keyword : " + keyword);
    if (keyword == null || keyword.isBlank()) {
      return lectureDAO.getLectureCount();
    }

    if (keyword.startsWith("writer.")) {
      String memberName = keyword.replaceFirst("^writer.", "");
      return lectureDAO.getLectureCountByMemberName(memberName);
    }

    keyword = keyword.replaceFirst("^content.", "");
    return lectureDAO.getLectureCountByKeyword(keyword);
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

  // --- lecture Like ---
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
  public boolean checkIsLike(int memberId, int lectureId) {
    logger.info("checkIsLike() 호출");
    return lectureDAO.selectIsMemberLikeLecture(memberId, lectureId);
  }

}
