package com.edu.blooming.domain;

import java.util.Date;

public class BoardVO {

  private int boardId;
  private int memberId;
  private String authorNickname;
  private String boardTitle;
  private String boardContent;
  private int boardViewCount;
  private int boardReplyCount;
  private int boardLikeCount;
  private Date boardDateCreated;

  public BoardVO() {}

  public BoardVO(int boardId, int memberId, String authorNickname, String boardTitle,
      String boardContent, int boardViewCount, int boardReplyCount, int boardLikeCount,
      Date boardDateCreated) {
    this.boardId = boardId;
    this.memberId = memberId;
    this.authorNickname = authorNickname;
    this.boardTitle = boardTitle;
    this.boardContent = boardContent;
    this.boardViewCount = boardViewCount;
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

  public String getAuthorNickname() {
    return authorNickname;
  }

  public void setAuthorNickname(String authorNickname) {
    this.authorNickname = authorNickname;
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
    return "BoardVO [boardId=" + boardId + ", memberId=" + memberId + ", authorNickname="
        + authorNickname + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
        + ", boardViewCount=" + boardViewCount + ", boardReplyCount=" + boardReplyCount
        + ", boardLikeCount=" + boardLikeCount + ", boardDateCreated=" + boardDateCreated + "]";
  }

}
