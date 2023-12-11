package com.edu.blooming.service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.LectureReplyVO;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.persistence.LectureReplyDAO;
import com.edu.blooming.persistence.LessonDAO;
import com.edu.blooming.persistence.MemberDAO;
import com.edu.blooming.persistence.PurchaseDAO;
import com.edu.blooming.util.Constants;
import com.edu.blooming.util.MailHandler;
import com.edu.blooming.util.TempKey;

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

  @Autowired
  private JavaMailSender mailSender;

  @Value("${email.username}")
  private String sendEmailAddress;

  @Transactional(value = "transactionManager")
  @Override
  public int register(MemberVO vo) throws MessagingException, UnsupportedEncodingException {
    logger.info("create()호출: vo = " + vo.toString());
    String emailKey = new TempKey().getKey(6, false);
    String memberEmail = vo.getMemberEmail();

    int result = memberDAO.insert(vo);
    memberDAO.updateEmailKey(memberEmail, emailKey);

    // 회원가입 완료하면 인증을 위한 이메일 발송
    MailHandler sendMail = new MailHandler(mailSender);
    sendMail.setSubject("[RunninGo 이메일 인증메일 입니다.]"); // 메일제목
    sendMail.setText("<h1>Blooming 메일인증</h1>" + "<br>Blooming에 오신것을 환영합니다!"
        + "<br>아래 인증 번호를 입력해주세요." + "<br>인증번호 : " + emailKey);
    sendMail.setFrom(sendEmailAddress, "Blooming");
    sendMail.setTo(vo.getMemberEmail());
    sendMail.send();

    return result;
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

  @Override
  public boolean checkEmailAuth(String email) {
    logger.info("checkEmailAuth() 호출: email = " + email);
    return memberDAO.checkEmailAuth(email);
  }

  @Override
  public int updateEmailKey(String email, String emailKey) {
    logger.info("updateEmailKey 호출");
    return memberDAO.updateEmailKey(email, emailKey);
  }

  @Override
  public int updateEmailAuth(String email, String emailKey) {
    logger.info("updateEmailAuth 호출");
    return memberDAO.updateEmailAuth(email, emailKey);
  }

} // end MemberService


