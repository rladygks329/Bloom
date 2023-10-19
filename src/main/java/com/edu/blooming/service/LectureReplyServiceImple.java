package com.edu.blooming.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.LectureReplyVO;
import com.edu.blooming.persistence.LectureDAO;
import com.edu.blooming.persistence.LectureReplyDAO;

@Service
public class LectureReplyServiceImple implements LectureReplyService {
  private final static Logger logger = LoggerFactory.getLogger(LectureReplyServiceImple.class);

  @Autowired
  private LectureReplyDAO lectureReplyDAO;

  @Autowired
  private LectureDAO lectureDAO;


  @Transactional
  @Override
  public int create(int memberId, LectureReplyVO vo) {
    logger.info("create() 호출 : memberId :" + memberId + " vo : " + vo.toString());

    lectureReplyDAO.insert(vo);
    lectureDAO.updateReplyCount(vo.getLectureId(), 1);
    lectureDAO.updateReplyTotalScore(vo.getLectureId(), vo.getLectureReplyScore());

    return 1;
  }

  @Transactional
  @Override
  public int update(LectureReplyVO vo) {
    logger.info("update() 호출 : vo : " + vo.toString());
    // 기존 리뷰 불러오기
    LectureReplyVO oldVo = lectureReplyDAO.selectByLectureReplyId(vo.getLectureReplyId());

    // 변동량 만큼 업데이트
    int amount = vo.getLectureReplyScore() - oldVo.getLectureReplyScore();
    lectureDAO.updateReplyTotalScore(vo.getLectureId(), amount);

    lectureReplyDAO.update(vo);
    return 0;
  }

  @Transactional
  @Override
  public int delete(int lectureReplyId) {
    logger.info("delete() 호출 : lectureReplyId : " + lectureReplyId);
    LectureReplyVO vo = lectureReplyDAO.selectByLectureReplyId(lectureReplyId);
    lectureReplyDAO.delete(lectureReplyId);
    lectureDAO.updateReplyTotalScore(vo.getLectureId(), -1 * vo.getLectureReplyScore());
    return 0;
  }

  @Override
  public List<LectureReplyVO> getReplies(int lectureId) {
    logger.info("getReplies() 호출 : lectureId: " + lectureId);
    return lectureReplyDAO.selectByLectureId(lectureId);
  }

}
