package com.edu.blooming.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.blooming.domain.LectureReplyVO;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.persistence.LectureReplyDAO;
import com.edu.blooming.persistence.LessonDAO;
import com.edu.blooming.persistence.MemberDAO;
import com.edu.blooming.persistence.PurchaseDAO;
import com.edu.blooming.util.Constants;

@Service
public class MemberServiceImple implements MemberService {
  private static final Logger logger = LoggerFactory.getLogger(MemberServiceImple.class);

  @Autowired
  private MemberDAO memberDAO;

  @Autowired
  private LessonDAO lessonDAO;

  @Autowired
  private LectureReplyDAO lectureReplyDAO;

  @Autowired
  private PurchaseDAO purchaseDAO;

  @Override
  public int register(MemberVO vo) {
    logger.info("create()호출: vo = " + vo.toString());
    return memberDAO.insert(vo);
  }

  @Override
  public boolean checkEmail(String email) {
    logger.info("emailCheck() 호출: email = " + email);
    return memberDAO.checkEmail(email);
  }

  @Override
  public boolean checkNickname(String nickname) {
    logger.info("checkNickname() 호출: nickname = " + nickname);
    return memberDAO.checkNickname(nickname);
  }

  @Override
  public MemberVO login(MemberVO member) {
    logger.info("memberLogin() 호출");
    logger.info("vo값 = " + member.toString());
    return memberDAO.login(member);
  }

  @Override
  public int updatePassword(int memberId, String password) {
    logger.info("updatePassword 호출");
    return memberDAO.updatePassword(memberId, password);
  }

  @Override
  public Map<String, Object> getInstuctorStatus(int memberId) {
    logger.info("getInstuctorStatus() 호출");
    Map<String, Object> result = new HashMap<>();
    List<LectureReplyVO> replies = lectureReplyDAO.selectByInstructorId(memberId);
    List<Map<String, Object>> status = lessonDAO.selectLessonStatus(memberId);
    List<Map<String, Object>> month_sales =
        purchaseDAO.getMonthlySales(memberId, Constants.MAX_REFUND_LIMIT);
    List<Map<String, Object>> lecture_sales =
        purchaseDAO.getSalesByLecture(memberId, Constants.MAX_REFUND_LIMIT);

    result.put("month_sales", month_sales);
    result.put("lecture_sales", lecture_sales);
    result.put("replies", replies);
    result.put("status", status);
    return result;
  }

  @Override
  public int updateNickname(int memberId, String nickname) {
    logger.info("updateNickname 호출");
    boolean isNicknameDuplicated = memberDAO.checkNickname(nickname);
    if (isNicknameDuplicated) {
      throw new IllegalStateException("중복된 닉네임입니다.");
    }
    return memberDAO.updateNickname(memberId, nickname);
  }

  @Override
  public int updateIntroduce(int memberId, String introduce) {
    logger.info("updateIntroduce 호출");
    logger.info(introduce);
    return memberDAO.updateIntroduce(memberId, introduce);
  }

  @Override
  public int updateProfileUrl(int memberId, String profileUrl) {
    logger.info("updateprofileUrl 호출");
    return memberDAO.updateProfileUrl(memberId, profileUrl);
  }

  @Override
  public int deleteProfileUrl(int memberId) {
    logger.info("deleteProfileUrl 호출");
    return memberDAO.updateProfileUrl(memberId, null);
  }

} // end MemberService


