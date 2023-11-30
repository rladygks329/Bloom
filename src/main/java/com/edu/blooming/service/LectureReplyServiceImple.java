package com.edu.blooming.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.LectureReplyVO;
import com.edu.blooming.exception.AlreadyExistException;
import com.edu.blooming.persistence.LectureDAO;
import com.edu.blooming.persistence.LectureReplyDAO;

@Service
public class LectureReplyServiceImple implements LectureReplyService {
  private final static Logger logger = LoggerFactory.getLogger(LectureReplyServiceImple.class);

  @Autowired
  private LectureReplyDAO lectureReplyDAO;

  @Autowired
  private LectureDAO lectureDAO;


  @Transactional(value = "transactionManager")
  @Override
  public int create(int memberId, LectureReplyVO vo) throws AlreadyExistException {
    logger.info("create() 호출 : memberId :" + memberId + " vo : " + vo.toString());

    try {
      lectureReplyDAO.insert(vo);
      lectureDAO.updateReplyCount(vo.getLectureId(), 1);
      lectureDAO.updateReplyTotalScore(vo.getLectureId(), vo.getLectureReplyScore());
    } catch (DataIntegrityViolationException e) {
      throw new AlreadyExistException("댓글은 하나만 작성할 수 있습니다.");
    }
    return vo.getLectureReplyId();
  }

  @Override
  public LectureReplyVO select(int memberId, int lectureId) {
    logger.info("select() 호출 : memberId :" + memberId + " lectureId : " + lectureId);
    return lectureReplyDAO.select(memberId, lectureId);
  }

  @Transactional(value = "transactionManager")
  @Override
  public int update(LectureReplyVO vo) {
    logger.info("update() 호출 : vo : " + vo.toString());
    // 기존 리뷰 불러오기
    LectureReplyVO oldVo = lectureReplyDAO.selectByLectureReplyId(vo.getLectureReplyId());

    // 변동량 만큼 업데이트
    int amount = vo.getLectureReplyScore() - oldVo.getLectureReplyScore();
    lectureDAO.updateReplyTotalScore(vo.getLectureId(), amount);

    lectureReplyDAO.update(vo);
    return 1;
  }

  @Transactional(value = "transactionManager")
  @Override
  public int delete(int lectureReplyId) {
    logger.info("delete() 호출 : lectureReplyId : " + lectureReplyId);
    LectureReplyVO vo = lectureReplyDAO.selectByLectureReplyId(lectureReplyId);
    lectureReplyDAO.delete(lectureReplyId);
    lectureDAO.updateReplyTotalScore(vo.getLectureId(), -1 * vo.getLectureReplyScore());
    lectureDAO.updateReplyCount(vo.getLectureId(), -1);
    return 1;
  }

  @Override
  public Map<String, Object> getReplies(int lectureId, int memberId, int pageSize,
      int lastReplyId) {
    logger.info("getReplies() 호출" + "lectureId: " + lectureId + "memberId: " + memberId
        + " pageSize: " + pageSize + " lastReplyId: " + lastReplyId);
    Map<String, Object> result = new HashMap<>();
    LectureReplyVO myComment = lectureReplyDAO.select(memberId, lectureId);
    boolean hasPrevComment = (myComment != null);
    List<LectureReplyVO> list =
        lectureReplyDAO.selectPageForInfiniteScroll(memberId, lectureId, lastReplyId, pageSize);

    result.put("myComment", myComment);
    result.put("hasPrevComment", hasPrevComment);
    result.put("list", list);
    return result;
  }

}
