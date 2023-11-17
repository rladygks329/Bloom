package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.util.PageCriteria;

public interface BoardService {

  int create(BoardVO vo);

  List<BoardVO> read(PageCriteria criteria, String option, String keyword);

  BoardVO read(int boardId);

  int getTotalCounts(String option, String keyword);

  int update(BoardVO vo);

  int updateViewCount(int boardId);

  int delete(BoardVO vo);

  int likeBoard(int boardId, int memberId);

  int dislikeBoard(int boardId, int memberId);

  boolean checkIsLike(int memberId, int boardId);
}
