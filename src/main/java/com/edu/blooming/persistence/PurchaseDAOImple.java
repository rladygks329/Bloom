package com.edu.blooming.persistence;

import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PurchaseDAOImple implements PurchaseDAO {
  private static final Logger logger = LoggerFactory.getLogger(PurchaseDAOImple.class);
  private static final String NAMESPACE = "com.edu.blooming.PurchaseMapper";

  @Autowired
  private SqlSession sqlSession;

  @Override
  public int insert(int memberId, int lectureId, int price) {
    logger.info("insert() 호출 : memberId = " + memberId + " lectureId = " + lectureId);

    Map<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("lectureId", lectureId);
    args.put("purchasePrice", price);

    return sqlSession.insert(NAMESPACE + ".insert", args);
  }

  @Override
  public int delete(int memberId, int lectureId) {
    logger.info("delete() 호출 : memberId = " + memberId + " lectureId = " + lectureId);

    Map<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("lectureId", lectureId);

    return sqlSession.insert(NAMESPACE + ".delete", args);
  }

}
