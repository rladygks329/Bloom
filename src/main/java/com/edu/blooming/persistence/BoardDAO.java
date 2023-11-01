package com.edu.blooming.persistence;

import java.util.List;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.util.PageCriteria;

public interface BoardDAO {

  int insert(BoardVO vo);

  List<BoardVO> select(int boardId);

  List<BoardVO> select(PageCriteria criteria);

  int getTotalCounts();

  int updateReplyCount(int boardId, int amount);

  boolean checkParentId(int boardId);

  int deleteQuestion(int boardId);

  int update(BoardVO vo);

  BoardVO selectForUpdate(int boardId);
}
