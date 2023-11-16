package com.edu.blooming.persistence;

import java.util.List;
import java.util.Map;
import com.edu.blooming.domain.LessonVO;

public interface LessonDAO {
  /*
   * @param : vo.lessonIndex 반영
   * 
   * @return : 입력된 lesson_id
   */
  int insert(LessonVO vo);

  /*
   * @param : vo.lessonIndex 미반영, 최댓값을 넣어줌
   * 
   * @return : 입력된 lesson_id
   */
  int append(LessonVO vo);

  int update(LessonVO vo);

  int updateVideoProcessingLevel(int lessonId, int level);

  int delete(int lessonId);

  int selectMinVideoProcessingLevel(int lectureId);

  List<LessonVO> selectByLectureId(int lectureId);

  LessonVO selectByLessonId(int lessonId);

  /*
   * key: proccess_rate, lecture_title
   */
  List<Map<String, Object>> selectLessonStatus(int memberId);
}
