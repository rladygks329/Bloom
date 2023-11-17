package com.edu.blooming.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
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
  public int create(MemberVO vo) {
    logger.info("create()호출: vo = " + vo.toString());
    return memberDAO.insert(vo);
  }

  @Override
  public int checkEmail(String email) throws Exception {
    logger.info("emailCheck() 호출: email = " + email);
    return memberDAO.checkEmail(email);
  }

  @Override
  public MemberVO login(MemberVO member) throws Exception {
    logger.info("memberLogin() 호출");
    logger.info("vo값 = " + member.toString());
    return memberDAO.login(member);
  }

  @Override
  public void logout(HttpSession session) {
    // TODO Auto-generated method stub
  }

  @Override
  public int updatePassword(int memberId, String memberPassword) {
    logger.info("updatePassword 호출");
    return memberDAO.updatePassword(memberId, memberPassword);
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
  public int checkNickname(String nickname) throws Exception {
    logger.info("checkNickname() 호출: nickname = " + nickname);
    return dao.checkNickname(nickname);
  }

  @Override
  public int updateNickname(int memberId, String memberNickname) {
    logger.info("updateNickname 호출");
    return dao.updateNickname(memberId, memberNickname);
  }

  @Override
  public int updateIntroduce(int memberId, String memberIntroduce) {
    logger.info("updateIntroduce 호출");
    return dao.updateIntroduce(memberId, memberIntroduce);
  }

} // end MemberService


