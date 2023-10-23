package com.edu.blooming.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.persistence.CartDAO;

@Service
public class CartServiceImple implements CartService {
  private static final Logger logger = LoggerFactory.getLogger(CartServiceImple.class);

  @Autowired
  private CartDAO cartDAO;

  @Override
  public int addLectureToCart(int memberId, int lectureId) {
    logger.info("addLectureToCart() 실행 memberId : " + memberId + " lectureId : " + lectureId);
    return cartDAO.insert(memberId, lectureId);
  }

  @Override
  public int removeLectureFromCart(int memberId, int lectureId) {
    logger.info("removeLectureFromCart() 실행 memberId : " + memberId + " lectureId : " + lectureId);
    return cartDAO.delete(memberId, lectureId);
  }

  @Override
  public List<LectureVO> getCartLectures(int memberId) {
    logger.info("getCartLectures() 실행 memberId : " + memberId);
    return cartDAO.select(memberId);
  }

  @Override
  public int calculateTotalPrice(int memberId) {
    logger.info("calculateTotalPrice() 실행 memberId : " + memberId);
    return cartDAO.calcTotal(memberId);
  }

}
