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
  public int add(int memberId, int lectureId) {
    logger.info("add() 실행 memberId : " + memberId + " lectureId : " + lectureId);
    return cartDAO.insert(memberId, lectureId);
  }

  @Override
  public int remove(int memberId, int lectureId) {
    logger.info("remove() 실행 memberId : " + memberId + " lectureId : " + lectureId);
    return cartDAO.delete(memberId, lectureId);
  }

  @Override
  public boolean isExist(int memberId, int lectureId) {
    logger.info("isExist() 실행 memberId : " + memberId + " lectureId : " + lectureId);
    int result = cartDAO.selectExist(memberId, lectureId);
    return result == 1;
  }

  @Override
  public List<LectureVO> getItems(int memberId) {
    logger.info("getItems() 실행 memberId : " + memberId);
    return cartDAO.select(memberId);
  }

  @Override
  public int calcTotal(int memberId) {
    logger.info("calcTotal() 실행 memberId : " + memberId);
    return cartDAO.calcTotal(memberId);
  }

}
