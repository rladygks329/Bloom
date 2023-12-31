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

  int getLectureCountByKeyword(String keyword);

  int getLectureCountByMemberName(String memberName);

  LectureVO select(int lectureId);

  List<LectureVO> select(PageCriteria criteria, int orderType);

  List<LectureVO> select(PageCriteria criteria, String keyword, int orderType);

  List<LectureVO> selectByAuthorName(PageCriteria criteria, String memberName, int orderType);

  /* @formatter:off 
   * @param: month : n month ago
   * @param: rank: start with 1
   * @return : n달 동안 가장 많은 좋아요를 받은 강의 리턴
   * @formatter:on
   */
  List<LectureVO> selectHotLikeLecture(int month, int rank);

  /* @formatter:off 
   * @param: month : n month ago
   * @param: rank: start with 1
   * @return : n달 동안 가장 많은 팔린 강의 리턴
   * @formatter:on
   */
  List<LectureVO> selectHotSaleLecture(int month, int rank);

  List<LectureVO> selectLikedLecture(int memberId);

  int insertLike(int memberId, int lectureId) throws DataIntegrityViolationException;

  int deleteLike(int memberId, int lectureId);

  boolean selectIsMemberLikeLecture(int memberId, int lectureId);

}
