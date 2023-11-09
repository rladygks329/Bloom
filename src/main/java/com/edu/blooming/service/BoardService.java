package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.util.PageCriteria;

public interface BoardService {

  int create(BoardVO vo);

  List<BoardVO> read(PageCriteria criteria);

  List<BoardVO> read(PageCriteria criteria, String keyword);

  List<BoardVO> read(PageCriteria criteria, int memberId);

  int getTotalCounts();

  int getTotalCountsByKeyword();

  int getTotalCountsByMemberId();

  BoardVO read(int boardId);

  int update(BoardVO vo);

  // BoardVO readForUpdate(int boardId);

  int updateViewCount(int boardId);

  int likeBoard(int boardId, int memberId);

  int dislikeBoard(int boardId, int memberId);

  boolean checkIsLike(int memberId, int boardId);

}
