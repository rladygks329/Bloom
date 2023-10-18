package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.LectureVO;

public interface CartService {
  int addLectureToCart(int memberId, int lectureId);

  int removeLectureFromCart(int memberId, int lectureId);

  List<LectureVO> getCartLectures(int memberId);

  int calculateTotalPrice(int memberId);

}
