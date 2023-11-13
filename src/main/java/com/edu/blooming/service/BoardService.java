package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.util.PageCriteria;

public interface BoardService {

  int create(BoardVO vo);

  List<BoardVO> read(PageCriteria criteria);

  List<BoardVO> readByNickname(PageCriteria criteria, String keyword);

  List<BoardVO> readByTitleOrContent(PageCriteria criteria, String keyword);

  int getTotalCounts();

  int getTotalCountsByTitleOrContent(String keyword);

  int getTotalCountsByNickname(String keyword);

  BoardVO read(int boardId);

  int update(BoardVO vo);

  int updateViewCount(int boardId);

  int likeBoard(int boardId, int memberId);

  int dislikeBoard(int boardId, int memberId);

  boolean checkIsLike(int memberId, int boardId);

  int deleteOrUpdate(BoardVO vo);

}
