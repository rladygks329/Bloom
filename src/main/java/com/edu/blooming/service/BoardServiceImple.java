package com.edu.blooming.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.persistence.BoardDAO;
import com.edu.blooming.util.PageCriteria;

@Service
public class BoardServiceImple implements BoardService {
  private static final Logger logger = LoggerFactory.getLogger(BoardServiceImple.class);
  @Autowired
  private BoardDAO boardDAO;

  @Override
  public int create(BoardVO vo) {
    // TODO Auto-generated method stub
    return 0;
  }

  @Override
  public List<BoardVO> read(PageCriteria criteria) {
    logger.info("read() 호출");
    logger.info("start = " + criteria.getStart());
    logger.info("end = " + criteria.getEnd());
    return boardDAO.select(criteria);
  }

  @Override
  public int getTotalCounts() {
    logger.info("getTotalCounts() 호출");
    return boardDAO.getTotalCounts();
  }

  @Override
  public List<BoardVO> read(int boardId) {
    logger.info("read() 호출: boardId = " + boardId);
    return boardDAO.select(boardId);
  }

  @Override
  @Transactional
  public boolean checkParentId(int boardId) {
    logger.info("checkQuestion() 호출 : boardId = " + boardId);
    return boardDAO.checkParentId(boardId);
  }

  @Override
  @Transactional
  public int delete(int boardId) {
    logger.info("delete() 호출: boardId = " + boardId);
    return boardDAO.deleteQuestion(boardId);
  }



}


