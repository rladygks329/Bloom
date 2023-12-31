package com.edu.blooming.persistence;

import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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
    sqlSession.insert(NAMESPACE + ".insert", vo);
    return vo.getLectureId();
  }

  @Override
  public int update(LectureVO vo) {
    logger.info("update() 호출 : vo = " + vo);
    return sqlSession.update(NAMESPACE + ".update", vo);
  }

  @Override
  public int updateLikeCount(int lectureId, int amount) {
    logger.info("updateLikeCount() 호출 : lectureId = " + lectureId + " amount : " + amount);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("lectureId", lectureId);
    args.put("amount", amount);

    return sqlSession.update(NAMESPACE + ".update_like", args);
  }

  @Override
  public int updateSalesCount(int lectureId, int amount) {
    logger.info("updateSalesCount() 호출 : lectureId = " + lectureId + " amount : " + amount);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("lectureId", lectureId);
    args.put("amount", amount);

    return sqlSession.update(NAMESPACE + ".update_sales_count", args);
  }

  @Override
  public int updateReplyCount(int lectureId, int amount) {
    logger.info("updateReplyCount() 호출 : lectureId = " + lectureId + " amount : " + amount);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("lectureId", lectureId);
    args.put("amount", amount);

    return sqlSession.update(NAMESPACE + ".update_reply_count", args);
  }

  @Override
  public int updateReplyTotalScore(int lectureId, int amount) {
    logger.info("updateReplyTotalScore() 호출 : lectureId = " + lectureId + " amount : " + amount);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("lectureId", lectureId);
    args.put("amount", amount);

    return sqlSession.update(NAMESPACE + ".update_total_score", args);
  }

  @Override
  public int updateVideoProcessingLevel(int lectureId, int level) {
    logger.info("updateVideoProcessingLevel() 호출 : lectureId = " + lectureId + " level : " + level);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("lectureId", lectureId);
    args.put("level", level);

    return sqlSession.update(NAMESPACE + ".update_video_processing_level", args);
  }

  @Override
  public int getLectureCount() {
    logger.info("getLectureCount() 호출");
    return sqlSession.selectOne(NAMESPACE + ".total_count");
  }

  @Override
  public int getLectureCountByKeyword(String keyword) {
    logger.info("getLectureCountByKeyword() 호출 : keyword : " + keyword);

    HashMap<String, Object> args = new HashMap<>();
    args.put("keyword", "%" + keyword + "%");

    return sqlSession.selectOne(NAMESPACE + ".keyword_lecture_count", args);
  }

  @Override
  public int getLectureCountByMemberName(String memberName) {
    logger.info("getLectureCountByMemberName() 호출 : memberName : " + memberName);

    HashMap<String, Object> args = new HashMap<>();
    args.put("memberName", memberName);

    return sqlSession.selectOne(NAMESPACE + ".member_lecture_count", args);
  }

  @Override
  public LectureVO select(int lectureId) {
    logger.info("select() 호출 : lectureId : " + lectureId);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("lectureId", lectureId);

    return sqlSession.selectOne(NAMESPACE + ".select_by_lecture_id", args);
  }

  @Override
  public List<LectureVO> select(PageCriteria criteria, int orderType) {
    logger.info("select() 호출 : criteria : " + criteria);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("start", criteria.getStart());
    args.put("end", criteria.getEnd());
    args.put("orderType", LectureVO.getOrderType(orderType));

    return sqlSession.selectList(NAMESPACE + ".paing_select", args);
  }

  @Override
  public List<LectureVO> select(PageCriteria criteria, String keyword, int orderType) {
    logger.info("select() 호출 : criteria : " + criteria.toString() + " keyword : " + keyword);

    HashMap<String, Object> args = new HashMap<>();
    args.put("start", criteria.getStart());
    args.put("end", criteria.getEnd());
    args.put("keyword", "%" + keyword + "%");
    args.put("orderType", LectureVO.getOrderType(orderType));

    return sqlSession.selectList(NAMESPACE + ".paging_select_by_keyword", args);
  }

  @Override
  public List<LectureVO> selectByAuthorName(PageCriteria criteria, String memberName,
      int orderType) {
    logger.info("selectByAuthorName() 호출 : memberName : " + memberName);

    HashMap<String, Object> args = new HashMap<>();
    args.put("start", criteria.getStart());
    args.put("end", criteria.getEnd());
    args.put("orderType", orderType);
    args.put("memberName", memberName);

    return sqlSession.selectList(NAMESPACE + ".paging_select_by_member_name", args);
  }

  @Override
  public List<LectureVO> selectHotLikeLecture(int month, int rank) {
    logger.info("selectByHotLikes() 호출");

    HashMap<String, Integer> args = new HashMap<>();
    args.put("month", month);
    args.put("rank", rank + 1);

    return sqlSession.selectList(NAMESPACE + ".select_hot_like_lecture", args);
  }

  @Override
  public List<LectureVO> selectHotSaleLecture(int month, int rank) {
    logger.info("selectHotSaleLecture() 호출");

    HashMap<String, Integer> args = new HashMap<>();
    args.put("month", month);
    args.put("rank", rank + 1);

    return sqlSession.selectList(NAMESPACE + ".select_top_sale_lecture", args);
  }

  // --------- lecture like --------
  @Override
  public int insertLike(int memberId, int lectureId) throws DataIntegrityViolationException {
    logger.info("insertLike() 호출 ");

    HashMap<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("lectureId", lectureId);

    return sqlSession.insert(NAMESPACE + ".insert_lecture_like", args);
  }

  @Override
  public int deleteLike(int memberId, int lectureId) {
    logger.info("deleteLike() 호출");

    HashMap<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("lectureId", lectureId);

    return sqlSession.delete(NAMESPACE + ".delete_lecture_like", args);
  }

  @Override
  public List<LectureVO> selectLikedLecture(int memberId) {
    HashMap<String, Integer> args = new HashMap<>();

    args.put("memberId", memberId);
    return sqlSession.selectList(NAMESPACE + ".select_liked_lecture", args);
  }

  @Override
  public boolean selectIsMemberLikeLecture(int memberId, int lectureId) {
    logger
        .info("selectIsMemberLikeLecture() 호출 memberId: " + memberId + " lectureId : " + lectureId);

    HashMap<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);
    args.put("lectureId", lectureId);

    int result = sqlSession.selectOne(NAMESPACE + ".select_is_member_like_lecture", args);
    return result == 1;
  }

}
