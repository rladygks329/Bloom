package com.edu.blooming.exception;

public class AlreadyExistException extends RuntimeException {

  // 장바구니, 좋아요, 결제 시 발생하는 중복을 처리하기 위한 도메인 에러
  public AlreadyExistException(String msg) {
    super(msg);
  }

}
