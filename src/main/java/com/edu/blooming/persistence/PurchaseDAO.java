package com.edu.blooming.persistence;

import java.util.List;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import com.edu.blooming.domain.LectureVO;

public interface PurchaseDAO {
  int insert(int memberId, int lectureId, int price) throws DataIntegrityViolationException;

  int delete(int memberId, int lectureId);

  List<LectureVO> select(int memberId);

  boolean selectIsMemberBuyLecture(int memberId, int lectureId);

  /* @formatter:off
   * @param: day 환불 가능한 기간, memberId: 강사의 memberId
   * @return 강좌별로 얼마나 팔린지 조회
   * @key: lecture_title, sales
   * @formatter:on
   */
  List<Map<String, Object>> getSalesByLecture(int memberId, int day);

  /* @formatter:off
   * @param : day 환불 가능한 기간, memberId: 강사의 memberId
   * @return: 해당 년도에 얼마나 팔린지 조회
   * @key: total_income, month
   * @formatter:on
   */
  List<Map<String, Object>> getMonthlySales(int memberId, int day);
}
