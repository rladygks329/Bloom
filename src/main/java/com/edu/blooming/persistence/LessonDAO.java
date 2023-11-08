package com.edu.blooming.persistence;

import java.util.List;
import com.edu.blooming.domain.LessonVO;

public interface LessonDAO {
  /*
   * @return : 입력된 lesson_id
   */
  int insert(LessonVO vo);

  int update(LessonVO vo);

  int updateLessonName(int lessonId, String name);

  int updateLessonUrl(int lessonId, String url);

  int updateVideoProcessingLevel(int lessonId, int level);

  int delete(int lessonId);

  int selectMinVideoProcessingLevel(int lectureId);

  List<LessonVO> selectByLectureId(int lectureId);

  LessonVO selectByLessonId(int lessonId);
}
