package com.edu.blooming.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;
import com.edu.blooming.domain.LectureVO;

@Repository
public class CartDAOImple implements CartDAO {
  private static final Logger logger = LoggerFactory.getLogger(CartDAOImple.class);
  private static final String NAMESPACE = "com.edu.blooming.CartMapper";

  @Autowired
  private SqlSession sqlSession;

  @Override
  public int insert(int memberId, int lectureId) throws DataIntegrityViolationException {
    logger.info("insert() 호출 : memberId = " + memberId + " lectureId = " + lectureId);

    Map<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("lectureId", lectureId);

    return sqlSession.insert(NAMESPACE + ".insert", args);
  }

  @Override
  public int delete(int memberId) {
    logger.info("delete() 호출 : memberId = " + memberId);

    Map<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);

    return sqlSession.delete(NAMESPACE + ".delete_by_member_id", args);
  }

  @Override
  public int delete(int memberId, int lectureId) {
    logger.info("delete() 호출 : memberId = " + memberId + " lectureId = " + lectureId);

    Map<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("lectureId", lectureId);

    return sqlSession.delete(NAMESPACE + ".delete", args);
  }

  @Override
  public boolean selectExist(int memberId, int lectureId) {
    logger.info("selectExist() 호출 : memberId = " + memberId + " lectureId = " + lectureId);

    Map<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("lectureId", lectureId);

    int result = sqlSession.selectOne(NAMESPACE + ".select_exist", args);
    return (result == 1);
  }

  @Override
  public List<LectureVO> select(int memberId) {
    logger.info("select() 호출 : memberId = " + memberId);

    Map<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);

    return sqlSession.selectList(NAMESPACE + ".select", args);
  }

  @Override
  public int calcTotal(int memberId) {
    logger.info("calcTotal() 호출 : memberId = " + memberId);

    Map<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);

    return sqlSession.selectOne(NAMESPACE + ".select_total", args);
  }

}
