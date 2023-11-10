package com.edu.blooming.service;

import java.util.List;
import com.edu.blooming.domain.BoardCommentVO;

public interface BoardCommentService {
  int create(int memberId, BoardCommentVO vo);

  int update(int boardCommentId, String boardCommentContent);

  int delete(int boardCommentId, int replyId);

  List<BoardCommentVO> getComments(int replyId);

}
