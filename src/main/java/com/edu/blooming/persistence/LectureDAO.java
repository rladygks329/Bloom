package com.edu.blooming.persistence;

import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
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

  /* @formatter:off 
   * @param: month : n month ago
   * @param: rank: start with 1
   * @return : n달 동안 가장 많은 좋아요를 받은 강의 리턴
   * @formatter:on
   */
  List<LectureVO> selectHotLikeLecture(int month, int rank);

  List<LectureVO> select(PageCriteria criteria, String keyword);

  List<LectureVO> select(PageCriteria criteria);

  List<LectureVO> select(PageCriteria criteria, int memberId);

  List<LectureVO> selectByAuthor(int memberId);

  boolean selectIsMemberLikeLecture(int memberId, int lectureId);

  int insertLike(int memberId, int lectureId) throws DataIntegrityViolationException;

  int deleteLike(int memberId, int lectureId);
}
