package com.edu.blooming.persistence;

import java.util.List;
import com.edu.blooming.domain.LessonVO;

public interface LessonDAO {
  int insert(LessonVO vo);

  int update(LessonVO vo);

  int delete(int lessonId);

  List<LessonVO> selectByLectureId(int lectureId);

  LessonVO selectByLessonId(int lessonId);
}
