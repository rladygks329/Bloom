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
import com.edu.blooming.domain.LectureReplyVO;

@Repository
public class LectureReplyDAOImpl implements LectureReplyDAO {

  private static final Logger logger = LoggerFactory.getLogger(LectureReplyDAOImpl.class);
  private static final String NAMESPACE = "com.edu.blooming.LectureReplyMapper";

  @Autowired
  private SqlSession sqlSession;

  @Override
  public int insert(LectureReplyVO vo) throws DataIntegrityViolationException {
    logger.info("insert() 호출,  vo : " + vo.toString());
    return sqlSession.insert(NAMESPACE + ".insert", vo);
  }

  @Override
  public int update(LectureReplyVO vo) {
    logger.info("update() 호출,  vo : " + vo.toString());
    return sqlSession.update(NAMESPACE + ".update", vo);
  }

  @Override
  public int delete(int lectureReplyId) {
    logger.info("delete() 호출,  lectureReplyId : " + lectureReplyId);

    Map<String, Integer> args = new HashMap<>();
    args.put("lectureReplyId", lectureReplyId);

    return sqlSession.delete(NAMESPACE + ".delete", args);
  }

  @Override
  public LectureReplyVO select(int memberId, int lectureId) {
    logger.info("select() 호출,  memberId : " + memberId + "lectureId : " + lectureId);
    Map<String, Integer> args = new HashMap<>();

    args.put("memberId", memberId);
    args.put("lectureId", lectureId);

    return sqlSession.selectOne(NAMESPACE + ".select", args);
  }

  @Override
  public LectureReplyVO selectByLectureReplyId(int lectureReplyId) {
    logger.info("selectByLectureReplyId() 호출,  lectureReplyId : " + lectureReplyId);

    Map<String, Integer> args = new HashMap<>();
    args.put("lectureReplyId", lectureReplyId);

    return sqlSession.selectOne(NAMESPACE + ".select_by_lecture_reply_id", args);
  }

  @Override
  public List<LectureReplyVO> selectByLectureId(int lectureId) {
    logger.info("selectByLectureId() 호출,  lectureId : " + lectureId);

    Map<String, Integer> args = new HashMap<>();
    args.put("lectureId", lectureId);

    return sqlSession.selectList(NAMESPACE + ".select_by_lecture_id", args);
  }

  @Override
  public List<LectureReplyVO> selectByInstructorId(int memberId) {
    logger.info("selectByInstructorId() 호출,  memberId : " + memberId);

    Map<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);

    return sqlSession.selectList(NAMESPACE + ".select_by_instructor_id", args);
  }

  @Override
  public List<LectureReplyVO> selectPageForInfiniteScroll(int memberId, int lectureId,
      int lastReplyId, int pageSize) {
    logger.info("selectPageForInfiniteScroll() 호출,  lectureId : " + lectureId + " lastReplyId: "
        + lastReplyId + " pageSize: " + pageSize);

    Map<String, Integer> args = new HashMap<>();

    args.put("memberId", memberId);
    args.put("lectureId", lectureId);
    args.put("lastLectureReplyId", lastReplyId);
    args.put("pageSize", pageSize);

    return sqlSession.selectList(NAMESPACE + ".select_infinite_scroll", args);
  }

}
