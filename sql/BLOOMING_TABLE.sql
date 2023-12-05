-- 강의 목록
CREATE TABLE LECTURE
(
    LECTURE_ID NUMBER PRIMARY KEY,
    MEMBER_ID NUMBER NOT NULL, -- 강사 ID
    LECTURE_TITLE VARCHAR2(50 CHAR) NOT NULL,
    LECTURE_DESCRIPTION VARCHAR2(500 CHAR) NOT NULL,
    LECTURE_PRICE NUMBER NOT NULL,
    LECTURE_VIDEO_PROCESSING_LEVEL NUMBER DEFAULT(0) NOT NULL,
    LECTURE_SALES_COUNT NUMBER DEFAULT(0) NOT NULL,
    LECTURE_TOTAL_SCORE NUMBER DEFAULT(0) NOT NULL,
    LECTURE_REPLY_COUNT NUMBER DEFAULT(0) NOT NULL,
    LECTURE_LIKE_COUNT NUMBER DEFAULT(0) NOT NULL,
    LECTURE_THUMBNAIL_URL VARCHAR2(200 CHAR) NOT NULL,
    LECTURE_DATE_CREATED TIMESTAMP DEFAULT(SYSDATE) NOT NULL
);

CREATE SEQUENCE LECTURE_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 100000
    NOCYCLE
    NOCACHE
    NOORDER;
    
-- 강의 동영상
CREATE TABLE LESSON
(
    LESSON_ID NUMBER PRIMARY KEY,
    LECTURE_ID NUMBER NOT NULL,
    LESSON_NAME VARCHAR2(50 CHAR) NOT NULL,
    LESSON_URL VARCHAR2(100 CHAR) NOT NULL,
    LESSON_INDEX NUMBER NOT NULL,
    LESSON_VIDEO_PROCESSING_LEVEL NUMBER DEFAULT(1) NOT NULL
);

CREATE TABLE VIDEO_PROCESSING_LEVEL
(
    VIDEO_PROCESSING_LEVEL NUMBER PRIMARY KEY,
    DISCRIPTION VARCHAR2(20 CHAR)
)

INSERT INTO VIDEO_PROCESSING_LEVEL
VALUES(0, 'NOT READY');

INSERT INTO VIDEO_PROCESSING_LEVEL
VALUES(1, 'COMPLETED');

CREATE SEQUENCE LESSON_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 100000
    NOCYCLE
    NOCACHE
    NOORDER;

CREATE TABLE LECTURE_LIKE
(
    LECTURE_ID NUMBER NOT NULL,
    MEMBER_ID NUMBER NOT NULL,
    LECTURE_LIKE_DATE_CREATED TIMESTAMP DEFAULT(SYSDATE) NOT NULL
);
ALTER TABLE LECTURE_LIKE ADD CONSTRAINT lecture_like_uq UNIQUE (LECTURE_ID, MEMBER_ID);

CREATE TABLE CART
(
    MEMBER_ID NUMBER NOT NULL,
    LECTURE_ID NUMBER NOT NULL
);
ALTER TABLE CART ADD CONSTRAINT cart_uq UNIQUE (LECTURE_ID, MEMBER_ID);

CREATE TABLE PURCHASE
(
    LECTURE_ID NUMBER NOT NULL,
    MEMBER_ID NUMBER NOT NULL,
    PURCHASE_DATE_CREATED TIMESTAMP DEFAULT(SYSDATE) NOT NULL,
    PURCHASE_PRICE NUMBER NOT NULL
 );
 ALTER TABLE PURCHASE ADD CONSTRAINT purchase_uq UNIQUE (LECTURE_ID, MEMBER_ID);

 CREATE TABLE LECTURE_REPLY
(
    LECTURE_REPLY_ID NUMBER PRIMARY KEY,
    MEMBER_ID NUMBER NOT NULL,
    LECTURE_ID NUMBER NOT NULL,
    LECTURE_REPLY_CONTENT VARCHAR2(200 CHAR) NOT NULL,
    LECTURE_REPLY_SCORE NUMBER NOT NULL
);
ALTER TABLE LECTURE_REPLY ADD CONSTRAINT lecture_reply_uq UNIQUE (LECTURE_ID, MEMBER_ID);

CREATE SEQUENCE LECTURE_REPLY_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 100000
    NOCYCLE
    NOCACHE
    NOORDER;

CREATE TABLE BOARD
(
    BOARD_ID NUMBER PRIMARY KEY,
    MEMBER_ID NUMBER NOT NULL, 
    BOARD_TITLE VARCHAR2(50 CHAR) NOT NULL,
    BOARD_CONTENT VARCHAR2(500 CHAR) NOT NULL,
    BOARD_VIEW_COUNT NUMBER DEFAULT(0) NOT NULL,
    BOARD_REPLY_COUNT NUMBER DEFAULT(0) NOT NULL,
    BOARD_LIKE_COUNT NUMBER DEFAULT(0) NOT NULL,  
    BOARD_DATE_CREATED TIMESTAMP DEFAULT(SYSDATE) NOT NULL
);

CREATE SEQUENCE BOARD_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 100000
    NOCYCLE
    NOCACHE
    NOORDER;

CREATE TABLE BOARD_REPLY
(
    BOARD_REPLY_ID NUMBER PRIMARY KEY,
    MEMBER_ID NUMBER NOT NULL,
    BOARD_ID NUMBER NOT NULL,
    BOARD_REPLY_CONTENT VARCHAR2(200 CHAR) NOT NULL,
    BOARD_REPLY_DATE_CREATED TIMESTAMP DEFAULT(SYSDATE) NOT NULL,
    BOARD_REPLY_COMMENT_COUNT NUMBER DEFAULT (0) NOT NULL
);

CREATE SEQUENCE BOARD_REPLY_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 100000
    NOCYCLE
    NOCACHE
    NOORDER;

CREATE TABLE BOARD_COMMENT
 (
    BOARD_COMMENT_ID NUMBER PRIMARY KEY,
    MEMBER_ID NUMBER NOT NULL,
    BOARD_REPLY_ID NUMBER NOT NULL,
    BOARD_COMMENT_CONTENT VARCHAR2(200 CHAR) NOT NULL,
    BOARD_COMMENT_DATE_CREATED TIMESTAMP DEFAULT(SYSDATE) NOT NULL    
   );

  CREATE SEQUENCE BOARD_COMMENT_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 100000
    NOCYCLE
    NOCACHE
    NOORDER;

CREATE TABLE BOARD_LIKE
(
  BOARD_ID NUMBER NOT NULL,
  MEMBER_ID NUMBER NOT NULL
);

CREATE TABLE MEMBER
(
    MEMBER_ID NUMBER PRIMARY KEY, 
    MEMBER_EMAIL VARCHAR2(50 CHAR) NOT NULL, 
    MEMBER_PASSWORD VARCHAR2(10 CHAR) NOT NULL,
    MEMBER_NAME VARCHAR2(10 CHAR) NOT NULL,
    MEMBER_NICKNAME VARCHAR2(10 CHAR) NOT NULL,
    MEMBER_PHONE VARCHAR2(20 CHAR) NOT NULL,
    MEMBER_ADDRESS VARCHAR2(100 CHAR) NOT NULL,
    MEMBER_LEVEL VARCHAR2(20 CHAR) NOT NULL,
    MEMBER_INTRODUCE VARCHAR2(500 CHAR),  
    MEMBER_PROFILE_URL VARCHAR2(200 CHAR)
);

CREATE SEQUENCE MEMBER_SEQ
    INCREMENT BY 1
    START WITH 1
    MINVALUE 1
    MAXVALUE 100000
    NOCYCLE
    NOCACHE
    NOORDER;
