package com.edu.blooming.service;

import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.persistence.MemberDAO;

@Service
public class MemberServiceImple implements MemberService {
  private static final Logger logger = LoggerFactory.getLogger(MemberServiceImple.class);

  @Autowired
  private MemberDAO dao;

  @Override
  public int create(MemberVO vo) {
    logger.info("create()호출: vo = " + vo.toString());
    return dao.insert(vo);
  } // end create()

  @Override
  public int checkEmail(String email) throws Exception {
    logger.info("emailCheck() 호출: email = " + email);
    return dao.checkEmail(email);
  }

  @Override
  public MemberVO login(MemberVO member) throws Exception {
    logger.info("memberLogin() 호출");
    logger.info("vo값 = " + member.toString());
    return dao.login(member);
  }

  @Override
  public void logout(HttpSession session) {
    // TODO Auto-generated method stub
  }

  @Override
  public int updatePassword(int memberId, String memberPassword) {
    logger.info("updatePassword 호출");
    return dao.updatePassword(memberId, memberPassword);
  }

  @Override
  public int checkNickname(String nickname) throws Exception {
    logger.info("checkNickname() 호출: nickname = " + nickname);
    return dao.checkEmail(nickname);
  }

} // end MemberService


