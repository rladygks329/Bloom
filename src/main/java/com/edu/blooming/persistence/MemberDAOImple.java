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
  } // end insert()

  @Override
  public boolean checkEmail(String memberEmail) {
    logger.info("emailCheck() 호출");
    int result = sqlSession.selectOne(NAMESPACE + ".check_email_duplicated", memberEmail);
    return result == 1;
  } // end emailCheck()

  @Override
  public MemberVO login(MemberVO loginVo) {
    logger.info("memberLogin() 호출");
    logger.info(loginVo.toString());
    return sqlSession.selectOne(NAMESPACE + ".login", loginVo);
  }

  @Override
  public MemberVO select(String memberEmail) {
    logger.info("select() 호출 : memberId = " + memberEmail);
    return null;
  }

  @Override
  public int update(String memberEmail, MemberVO vo) {
    logger.info("update() 호출 : memberId = " + memberEmail);
    return 0;
  }

  @Override
  public int delete(String memberEmail) {
    logger.info("delete() 호출  memberId = " + memberEmail);
    return 0;
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
  public boolean checkNickname(String nickname) {
    logger.info("checkNickname() 호출");
    int result = sqlSession.selectOne(NAMESPACE + ".check_nickname_duplicated", nickname);
    return result == 1;
  }

  @Override
  public int updateNickname(int memberId, String nickname) {
    logger.info("updateNickname 호출");
    HashMap<String, Object> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("memberNickname", nickname);

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

}


