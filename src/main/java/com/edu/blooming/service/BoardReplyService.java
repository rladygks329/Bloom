package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.BoardReplyVO;

public interface BoardReplyService {
  int create(int memberId, BoardReplyVO vo);

  int update(int boardReplyId, String boardReplyContent);

  int delete(int boardReplyId, int boardId);

  List<BoardReplyVO> getReplies(int boardId);

}
