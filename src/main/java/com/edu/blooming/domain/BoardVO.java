package com.edu.blooming.domain;

import java.util.Date;

public class BoardVO {

  private int boardId;
  private int memberId;
  private int boardParentId;
  private String boardTitle;
  private String boardContent;
  private int boardViewCount;
  private int boardAnswerCount;
  private int boardReplyCount;
  private int boardLikeCount;
  private Date boardDateCreated;

  public BoardVO() {}

  public BoardVO(int boardId, int memberId, int boardParentId, String boardTitle,
      String boardContent, int boardViewCount, int boardAnswerCount, int boardReplyCount,
      int boardLikeCount, Date boardDateCreated) {
    this.boardId = boardId;
    this.memberId = memberId;
    this.boardParentId = boardParentId;
    this.boardTitle = boardTitle;
    this.boardContent = boardContent;
    this.boardViewCount = boardViewCount;
    this.boardAnswerCount = boardAnswerCount;
    this.boardReplyCount = boardReplyCount;
    this.boardLikeCount = boardLikeCount;
    this.boardDateCreated = boardDateCreated;
  }

  public int getBoardId() {
    return boardId;
  }

  public void setBoardId(int boardId) {
    this.boardId = boardId;
  }

  public int getMemberId() {
    return memberId;
  }

  public void setMemberId(int memberId) {
    this.memberId = memberId;
  }

  public int getBoardParentId() {
    return boardParentId;
  }

  public void setBoardParentId(int boardParentId) {
    this.boardParentId = boardParentId;
  }

  public String getBoardTitle() {
    return boardTitle;
  }

  public void setBoardTitle(String boardTitle) {
    this.boardTitle = boardTitle;
  }

  public String getBoardContent() {
    return boardContent;
  }

  public void setBoardContent(String boardContent) {
    this.boardContent = boardContent;
  }

  public int getBoardViewCount() {
    return boardViewCount;
  }

  public void setBoardViewCount(int boardViewCount) {
    this.boardViewCount = boardViewCount;
  }

  public int getBoardAnswerCount() {
    return boardAnswerCount;
  }

  public void setBoardAnswerCount(int boardAnswerCount) {
    this.boardAnswerCount = boardAnswerCount;
  }

  public int getBoardReplyCount() {
    return boardReplyCount;
  }

  public void setBoardReplyCount(int boardReplyCount) {
    this.boardReplyCount = boardReplyCount;
  }

  public int getBoardLikeCount() {
    return boardLikeCount;
  }

  public void setBoardLikeCount(int boardLikeCount) {
    this.boardLikeCount = boardLikeCount;
  }

  public Date getBoardDateCreated() {
    return boardDateCreated;
  }

  public void setBoardDateCreated(Date boardDateCreated) {
    this.boardDateCreated = boardDateCreated;
  }

  @Override
  public String toString() {
    return "BoardVO [boardId=" + boardId + ", memberId=" + memberId + ", boardParentId="
        + boardParentId + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
        + ", boardViewCount=" + boardViewCount + ", boardAnswerCount=" + boardAnswerCount
        + ", boardReplyCount=" + boardReplyCount + ", boardLikeCount=" + boardLikeCount
        + ", boardDateCreated=" + boardDateCreated + "]";
  }

}
