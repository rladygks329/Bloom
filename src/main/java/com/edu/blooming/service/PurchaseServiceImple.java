package com.edu.blooming.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.exception.AlreadyExistException;
import com.edu.blooming.persistence.CartDAO;
import com.edu.blooming.persistence.LectureDAO;
import com.edu.blooming.persistence.PurchaseDAO;

@Service
public class PurchaseServiceImple implements PurchaseService {
  private static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImple.class);

  @Autowired
  private PurchaseDAO purchaseDAO;

  @Autowired
  private LectureDAO lectureDAO;

  @Autowired
  private CartDAO cartDAO;

  @Transactional(value = "transactionManager")
  @Override
  public int purchase(int memberId) throws AlreadyExistException {
    logger.info("purchase() 호출, memberId: " + memberId);
    List<LectureVO> list = cartDAO.select(memberId);
    if (list.isEmpty()) {
      return 0;
    }

    try {
      for (LectureVO vo : list) {
        purchaseDAO.insert(memberId, vo.getLectureId(), vo.getLecturePrice());
        lectureDAO.updateSalesCount(vo.getLectureId(), 1);
        cartDAO.delete(memberId);
      }
    } catch (DataIntegrityViolationException e) {
      throw new AlreadyExistException("이미 결제하신 항목입니다.");
    }
    return 1;
  }

  @Transactional(value = "transactionManager")
  @Override
  public int refund(int memberId, int lectureId) {
    logger.info("refund() 호출, memberId : " + memberId + " lectureId : " + lectureId);
    purchaseDAO.delete(memberId, lectureId);
    lectureDAO.updateSalesCount(lectureId, -1);
    return 1;
  }

  @Override
  public List<LectureVO> getPurchaseList(int memberId) {
    logger.info("getPurchaseList() 호출, memberId : " + memberId);
    return purchaseDAO.select(memberId);
  }

  @Override
  public boolean checkPurchase(int memberId, int lectureId) {
    logger.info("checkPurchase() 호출, memberId : " + memberId + " lectureId : " + lectureId);
    boolean hasPurchase = purchaseDAO.selectIsMemberBuyLecture(memberId, lectureId);
    boolean isAuthor = lectureDAO.select(lectureId).getMemberId() == memberId;
    return hasPurchase || isAuthor;
  }

}
