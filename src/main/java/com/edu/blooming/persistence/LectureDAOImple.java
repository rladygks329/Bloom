package com.edu.blooming.persistence;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.util.PageCriteria;

@Repository
public class LectureDAOImple implements LectureDAO {

  private static final Logger logger = LoggerFactory.getLogger(LectureDAOImple.class);
  private static final String NAMESPACE = "com.edu.blooming.LectureMapper";

  @Autowired
  private SqlSession sqlSession;

  @Override
  public int insert(LectureVO vo) {
    logger.info("insert() 호출 : vo = " + vo);
    return sqlSession.insert(NAMESPACE + ".insert", vo);
  }

  @Override
  public int update(LectureVO vo) {
    logger.info("update() 호출 : vo = " + vo);
    return sqlSession.insert(NAMESPACE + ".update", vo);
  }

  @Override
  public int updateLikeCount(int lectureId, int amount) {
    logger.info("updateLikeCount() 호출 : lectureId = " + lectureId + " amount : " + amount);
    HashMap<String, Integer> args = new HashMap<>();
    args.put("lectureId", lectureId);
    args.put("amount", amount);
    return sqlSession.insert(NAMESPACE + ".update_like", args);
  }

  @Override
  public int updateSalesCount(int lectureId, int amount) {
    logger.info("updateSalesCount() 호출 : lectureId = " + lectureId + " amount : " + amount);
    HashMap<String, Integer> args = new HashMap<>();
    args.put("lectureId", lectureId);
    args.put("amount", amount);
    return sqlSession.insert(NAMESPACE + ".update_sales_count", args);
  }

  @Override
  public int updateReplyCount(int lectureId, int amount) {
    logger.info("updateReplyCount() 호출 : lectureId = " + lectureId + " amount : " + amount);
    HashMap<String, Integer> args = new HashMap<>();
    args.put("lectureId", lectureId);
    args.put("amount", amount);
    return sqlSession.insert(NAMESPACE + ".update_reply_count", args);
  }

  @Override
  public int getLectureCount() {
    logger.info("getLectureCount() 호출");
    return sqlSession.selectOne(NAMESPACE + ".total_count");
  }

  @Override
  public int getLectureCount(int memberId) {
    logger.info("getLectureCount() 호출 : memberId : " + memberId);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);

    return sqlSession.selectOne(NAMESPACE + ".member_lecture_count", args);
  }

  @Override
  public List<LectureVO> select(PageCriteria criteria) {
    logger.info("select() 호출 : criteria : " + criteria);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("start", criteria.getStart());
    args.put("end", criteria.getEnd());

    return sqlSession.selectList(NAMESPACE + ".paing_select", args);
  }

  @Override
  public List<LectureVO> select(PageCriteria criteria, int memberId) {
    logger.info("select() 호출 : criteria : " + criteria + " memberId = " + memberId);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("start", criteria.getStart());
    args.put("end", criteria.getEnd());
    args.put("memberId", memberId);

    return sqlSession.selectList(NAMESPACE + ".paging_select_by_member_id", args);
  }

}