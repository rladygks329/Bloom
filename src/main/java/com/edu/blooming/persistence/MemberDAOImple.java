package com.edu.blooming.persistence;

import java.util.HashMap;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.edu.blooming.domain.MemberVO;

@Repository
public class MemberDAOImple implements MemberDAO {

  private static final Logger logger = LoggerFactory.getLogger(MemberDAOImple.class);

  private static final String NAMESPACE = "com.edu.blooming.MemberMapper";

  @Autowired
  private SqlSession sqlSession;

  @Override
  public int insert(MemberVO vo) {
    logger.info("insert() 호출");
    return sqlSession.insert(NAMESPACE + ".insert", vo);
  }

  @Override
  public boolean checkEmail(String memberEmail) {
    logger.info("emailCheck() 호출");
    int result = sqlSession.selectOne(NAMESPACE + ".check_email_duplicated", memberEmail);
    return result == 1;
  }

  @Override
  public boolean checkNickname(String memberNickname) {
    logger.info("checkNickname() 호출");
    int result = sqlSession.selectOne(NAMESPACE + ".check_nickname_duplicated", memberNickname);
    return result == 1;
  }

  @Override
  public MemberVO login(MemberVO loginVo) {
    logger.info("memberLogin() 호출");
    logger.info(loginVo.toString());
    return sqlSession.selectOne(NAMESPACE + ".login", loginVo);
  }

  @Override
  public int updatePassword(int memberId, String memberPassword) {
    logger.info("updatePassword 호출");
    HashMap<String, Object> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("memberPassword", memberPassword);

    return sqlSession.update(NAMESPACE + ".update_password", args);
  }

  @Override
  public int updateNickname(int memberId, String memberNickname) {
    logger.info("updateNickname 호출");
    logger.info("memberId = " + memberId + " memberNickname = " + memberNickname);
    HashMap<String, Object> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("memberNickname", memberNickname);

    return sqlSession.update(NAMESPACE + ".update_nickname", args);
  }

  @Override
  public int updateIntroduce(int memberId, String introduce) {
    logger.info("updateIntroduce 호출");
    HashMap<String, Object> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("memberIntroduce", introduce);

    return sqlSession.update(NAMESPACE + ".update_introduce", args);
  }

  @Override
  public int updateProfileUrl(int memberId, String memberProfileUrl) {
    logger.info("updateProfileUrl 호출");
    HashMap<String, Object> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("memberProfileUrl", memberProfileUrl);

    return sqlSession.update(NAMESPACE + ".update_profileUrl", args);
  }

  @Override
  public MemberVO selectInstructor(int memberId) {
    logger.info("selectInstructor() 호출");
    return sqlSession.selectOne(NAMESPACE + ".select_by_instructor_id", memberId);
  }

  @Override
  public boolean checkEmailAuth(String memberEmail) {
    logger.info("checkEmailAuth() 호출");
    int result = sqlSession.selectOne(NAMESPACE + ".check_email_auth", memberEmail);
    return result == 1;
  }

  @Override
  public int updateEmailKey(String memberEmail, String memberEmailKey) {
    logger.info("updateEmailKey() 호출");
    HashMap<String, Object> args = new HashMap<>();
    args.put("memberEmail", memberEmail);
    args.put("memberEmailKey", memberEmailKey);

    return sqlSession.update(NAMESPACE + ".update_email_key", args);
  }

  @Override
  public int updateEmailAuth(String memberEmail, String memberEmailKey) {
    logger.info("upateEmailAuth() 호출");
    HashMap<String, Object> args = new HashMap<>();
    args.put("memberEmail", memberEmail);
    args.put("memberEmailKey", memberEmailKey);
    return sqlSession.update(NAMESPACE + ".update_email_auth", args);
  }

}


