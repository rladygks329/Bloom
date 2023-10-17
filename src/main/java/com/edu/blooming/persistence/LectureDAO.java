package com.edu.blooming.persistence;

import java.util.List;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.util.PageCriteria;

public interface LectureDAO {
  int insert(LectureVO vo);

  int update(LectureVO vo);

  int updateLikeCount(int lectureId, int amount);

  int updateSalesCount(int lectureId, int amount);

  int updateReplyCount(int lectureId, int amount);

  int getLectureCount();

  int getLectureCount(int memberId);

  List<LectureVO> select(PageCriteria criteria);

  List<LectureVO> select(PageCriteria criteria, int memberId);
}
