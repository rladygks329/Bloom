package com.edu.blooming.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.edu.blooming.domain.LessonVO;

@Repository
public class LessonDAOImple implements LessonDAO {
  private static final Logger logger = LoggerFactory.getLogger(LessonDAOImple.class);
  private static final String NAMESPACE = "com.edu.blooming.LessonMapper";

  @Autowired
  private SqlSession sqlSession;

  @Override
  public int insert(LessonVO vo) {
    logger.info("insert() 호출 : vo = " + vo);
    sqlSession.insert(NAMESPACE + ".insert", vo);
    return vo.getLessonId();
  }

  @Override
  public int append(LessonVO vo) {
    logger.info("append() 호출: vo = " + vo);
    sqlSession.insert(NAMESPACE + ".append", vo);
    return vo.getLessonId();
  }

  @Override
  public int update(LessonVO vo) {
    logger.info("update() 호출 : vo = " + vo);
    return sqlSession.update(NAMESPACE + ".update", vo);
  }

  @Override
  public int updateVideoProcessingLevel(int lessonId, int level) {
    logger.info("updateVideoProcessingLevel() 호출 : lessonId: " + lessonId + " level : " + level);
    Map<String, Integer> args = new HashMap<>();
    args.put("lessonId", lessonId);
    args.put("level", level);
    return sqlSession.update(NAMESPACE + ".update_video_processing_level", args);
  }

  @Override
  public int delete(int lessonId) {
    logger.info("delete() 호출 : lessonId = " + lessonId);
    Map<String, Integer> args = new HashMap<>();
    args.put("lessonId", lessonId);
    return sqlSession.delete(NAMESPACE + ".delete", args);
  }

  @Override
  public int selectMinVideoProcessingLevel(int lectureId) {
    logger.info("selectMinVideoProcessingLevel() 호출 : lectureId = " + lectureId);
    Map<String, Integer> args = new HashMap<>();
    args.put("lectureId", lectureId);
    return sqlSession.selectOne(NAMESPACE + ".select_min_video_processing_level", args);
  }

  @Override
  public List<LessonVO> selectByLectureId(int lectureId) {
    logger.info("selectByLectureId() 호출 : lectureId = " + lectureId);
    Map<String, Integer> args = new HashMap<>();
    args.put("lectureId", lectureId);
    return sqlSession.selectList(NAMESPACE + ".select_by_lecture_id", args);
  }

  @Override
  public LessonVO selectByLessonId(int lessonId) {
    logger.info("selectByLessonId() 호출 : lessonId = " + lessonId);
    Map<String, Integer> args = new HashMap<>();
    args.put("lessonId", lessonId);
    return sqlSession.selectOne(NAMESPACE + ".select_by_lesson_id", args);
  }

  @Override
  public List<Map<String, Object>> selectLessonStatus(int memberId) {
    logger.info("selectLessonStatus() 호출 : memberId : " + memberId);
    Map<String, Integer> args = new HashMap<>();
    args.put("memberId", memberId);
    return sqlSession.selectList(NAMESPACE + ".select_lesson_status", args);
  }

}
