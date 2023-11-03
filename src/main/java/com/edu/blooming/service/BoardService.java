package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.util.PageCriteria;

public interface BoardService {

  int create(BoardVO vo);

  List<BoardVO> read(PageCriteria criteria);

  int getTotalCounts();

  List<BoardVO> read(int boardId);

  int update(BoardVO vo);

  BoardVO readForUpdate(int boardId);

  int updateViewCount(int boardId);

  int likeBoard(int boardId, int memberId);

  int dislikeBoard(int boardId, int memberId);


}
