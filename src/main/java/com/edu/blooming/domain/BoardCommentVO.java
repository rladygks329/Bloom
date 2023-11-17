package com.edu.blooming.domain;

import java.util.Date;

public class BoardCommentVO {
  private int boardCommentId;
  private int memberId;
  private int boardReplyId;
  private String boardCommentContent;
  private Date boardCommentDateCreated;
  private String authorNickname;

  public BoardCommentVO() {}

  public BoardCommentVO(int boardCommentId, int memberId, int boardReplyId,
      String boardCommentContent, Date boardCommentDateCreated, String authorNickname) {
    super();
    this.boardCommentId = boardCommentId;
    this.memberId = memberId;
    this.boardReplyId = boardReplyId;
    this.boardCommentContent = boardCommentContent;
    this.boardCommentDateCreated = boardCommentDateCreated;
    this.authorNickname = authorNickname;
  }

  public int getBoardCommentId() {
    return boardCommentId;
  }

  public void setBoardCommentId(int boardCommentId) {
    this.boardCommentId = boardCommentId;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public int getBoardReplyId() {
    return boardReplyId;
  }

  public void setBoardReplyId(int boardReplyId) {
    this.boardReplyId = boardReplyId;
  }

  public String getBoardCommentContent() {
    return boardCommentContent;
  }

  public void setBoardCommentContent(String boardCommentContent) {
    this.boardCommentContent = boardCommentContent;
  }

  public Date getBoardCommentDateCreated() {
    return boardCommentDateCreated;
  }

  public void setBoardCommentDateCreated(Date boardCommentDateCreated) {
    this.boardCommentDateCreated = boardCommentDateCreated;
  }

  public String getAuthorNickname() {
    return authorNickname;
  }

  public void setAuthorNickname(String authorNickname) {
    this.authorNickname = authorNickname;
  }

  @Override
  public String toString() {
    return "BoardCommentVO [boardCommentId=" + boardCommentId + ", memberId=" + memberId
        + ", boardReplyId=" + boardReplyId + ", boardCommentContent=" + boardCommentContent
        + ", boardCommentDateCreated=" + boardCommentDateCreated + ", authorNickname="
        + authorNickname + "]";
  }

}

