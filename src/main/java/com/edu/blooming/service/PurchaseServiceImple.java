package com.edu.blooming.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.persistence.LectureDAO;
import com.edu.blooming.persistence.PurchaseDAO;

@Service
public class PurchaseServiceImple implements PurchaseService {
  private static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImple.class);

  @Autowired
  private PurchaseDAO purchaseDAO;

  @Autowired
  private LectureDAO lectureDAO;

  @Transactional
  @Override
  public int purchase(int memberId, int lectureId) {
    logger.info("purchase() 호출, memberId : " + memberId + " lectureId : " + lectureId);
    LectureVO vo = lectureDAO.select(lectureId);
    purchaseDAO.insert(memberId, lectureId, vo.getLecturePrice());
    lectureDAO.updateSalesCount(lectureId, 1);
    return 1;
  }

  @Transactional
  @Override
  public int purchase(int memberId, List<Integer> lectureIds) {
    logger
        .info("purchase(List) 호출, memberId : " + memberId + " list.size() : " + lectureIds.size());
    for (int lectureId : lectureIds) {
      LectureVO vo = lectureDAO.select(lectureId);
      purchaseDAO.insert(memberId, lectureId, vo.getLecturePrice());
      lectureDAO.updateSalesCount(lectureId, 1);
    }
    return 1;
  }

  @Transactional
  @Override
  public int refund(int memberId, int lectureId) {
    logger.info("refund() 호출, memberId : " + memberId + " lectureId : " + lectureId);
    purchaseDAO.delete(memberId, lectureId);
    lectureDAO.updateSalesCount(lectureId, -1);
    return 1;
  }

}
