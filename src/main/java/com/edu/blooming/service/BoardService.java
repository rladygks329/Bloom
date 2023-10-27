package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.util.PageCriteria;

public interface BoardService {

  int create(BoardVO vo);

  List<BoardVO> read(PageCriteria criteria);

  int getTotalCounts();

  List<BoardVO> read(int boardId);
}
