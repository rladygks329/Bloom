package com.edu.blooming.persistence;

import java.util.List;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.util.PageCriteria;

public interface LectureDAO {
  /*
   * @return : 입력된 lectureId
   */
  int insert(LectureVO vo);

  int update(LectureVO vo);

  int updateLikeCount(int lectureId, int amount);

  int updateSalesCount(int lectureId, int amount);

  int updateReplyCount(int lectureId, int amount);

  int updateReplyTotalScore(int lectureId, int amount);

  int updateVideoProcessingLevel(int lectureId, int level);

  int getLectureCount();

  int getLectureCount(int memberId);

  int getLectureCount(String keyword);

  LectureVO select(int lectureId);

  List<LectureVO> select(PageCriteria criteria, String keyword);

  List<LectureVO> select(PageCriteria criteria);

  List<LectureVO> select(PageCriteria criteria, int memberId);

  List<LectureVO> selectByAuthor(int memberId);

  boolean selectIsMemberLikeLecture(int memberId, int lectureId);

  int insertLike(int memberId, int lectureId);

  int deleteLike(int memberId, int lectureId);
}
