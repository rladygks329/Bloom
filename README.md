## BLOOM

> 해당 프로젝트는 spring으로 구현한 교육용 강의 판매 사이트입니다.
> <br>
> 프로젝트명 의미 - "BLOOM"은 꽃 피우다라는 뜻을 가지고 있습니다. "재능을 꽃 피우는 곳" 이라는 뜻을 담아 이름을 지었습니다.

## 개발환경 세팅 방법

1. <a href="https://www.oracle.com/database/technologies/xe-prior-release-downloads.html">Oracle Database 11g Express Edition Release 11.2.0.2.0</a> 설치
2. sql 폴더에 있는 DDL을 실행
3. <a href="https://ffmpeg.org/download.html"> ffmpeg 링크</a> 에서 OS에 맞는 ffmpeg파일을 받고 설치
4. <a href="https://developers.kakao.com"> 카카오 developer</a> 에서 어플리케이션 추가
5. src/main/resources에 application.properties 파일 추가
6. <a href="https://tomcat.apache.org/download-90.cgi">아파치 톰캣 9.0</a> 설치
7. maven 을 통해서 dependency down
8. sts로 열기 및 실행

## 빌드 명령어

```bash
mvn install -DskipTests
cp target/blooming-1.0.0-BUILD-SNAPSHOT.war blooming.war
```

### application.properties 예제

```properties
#application.properties example - window
uploadPath.img=C:\\Study\\BloomingBucket\\img
uploadPath.video=C:\\Study\\BloomingBucket\\video
ffmpeg.path=C:\\Users\\goott3\\Desktop\\ffmpeg-6.0-full_build\\bin\\ffmpeg.exe
ffprobe.path=C:\\Users\\goott3\\Desktop\\ffmpeg-6.0-full_build\\bin\\ffprobe.exe

database.url=jdbc:oracle:thin:@localhost:1521:xe
database.user={ your id }
database.password={ your password }
pay.kakao.cid={TC0ONETIME(테스트 결제용 가맹점 코드)}
pay.kakao.admin.key={your admin key}
email.username= {your smtp email}
email.password= {your smtp password}
```

## 웹 실행 모습

<details>
  <summary> 로그인 & 회원가입 </summary> 
  <p>Hypertext Markup Language</p>
</details>

<details> 
  <summary> 질문 게시판과 댓글 </summary> 
  <p>Cascading Style Sheets.</p>
</details>

<details> 
  <summary>메인 페이지</summary> 
  <p>
    한 달 동안 ‘좋아요’를 가장 많이 받은 강의를 보여줍니다. <br>
    한 달 동안 결제가 가장 많이 일어난 강의를 보여줍니다.
  </p>
  <img loading="lazy" src="https://github.com/rladygks329/Bloom/assets/64533351/50c0b3ec-da2b-4a85-b081-55fd7a8b9a80">
  <br>
</details>

<details>
  <summary>강의 목록</summary> 
  <p>
    1. 페이지네이션이 적용되었습니다.<br/>
    2. 작성자명으로 검색하거나 제목 + 내용으로 검색할 수 있습니다.<br/>
    3. 다양한 정렬기준 (최신순, 평점 순, 댓글 많은 순, 가격 높은 순, 가격 낮은 순, 구매많은 순)으로 정렬할 수 있습니다.<br/>
  <p>
  <img loading="lazy" src="https://github.com/rladygks329/Bloom/assets/64533351/701fab38-5bd7-4e7d-ba6e-61d91ef43a92">
</details>

<details>
  <summary>수강 평</summary>
   <p>
    1. 클릭을 통해서 평점을 조절할 수 있습니다. <br>
    2. 모달을 사용하여 수정합니다. <br>
    3. 페이지네이션을 적용했습니다. <br>
    4. 자신이 만든 수강평은 맨 위에 보입니다. 
  <p>
  <img loading="lazy" src="https://github.com/rladygks329/Bloom/assets/64533351/b7b3dd58-a764-4d0d-9dd4-aa50acd1ddf5">
</details>

<details>
  <summary>강의 수강 페이지</summary>
   <p>
    1. 화질을 조절 할 수 있습니다. <br>
    2. 커리큘럼 내에 다른 영상으로 전환 할 수 있습니다. <br>
    3. HLS프로토콜을 사용하여 전송합니다.
  <p>
  <img loading="lazy" src="https://github.com/rladygks329/Bloom/assets/64533351/3c3dae1e-7c8f-4962-a8ed-6aee9a8a3c97">
</details>

<details>
  <summary>장바구니와 결제</summary>
   <p>
    1. 상세 페이지에서 장바구니에 추가할 수 있습니다. <br>
    2. 장바구니에서 삭제할 수 있습니다. <br>
    3. 총 합이 계산됩니다. <br>
    4. 카카오 페이를 통해 결제할 수 있습니다.
  <p>
  <img loading="lazy" src="https://github.com/rladygks329/Bloom/assets/64533351/3c4a7e2d-f051-4469-afe5-b69023fc4df7">
</details>

<details>
  <summary>강사 페이지와 강의 수정 및 업로드</summary>
   <p>
    1. 강좌 별 판매량, 월 수익을 확인할 수 있습니다. <br>
    2. 자신에게 달린 수강평을 모아서 볼 수 있습니다. <br>
    3. 강의를 수정할 수 있습니다. <br>
    4. 강의 영상은 비동기로 FFmpeg를 통해 mp4 -> hls 형식으로 변환됩니다.
  <p>
  <img loading="lazy" src="https://github.com/rladygks329/Bloom/assets/64533351/d644ff14-7ec7-426a-ba8d-14b3dc51cedc">
</details>

<details>
  <summary>강의 상세 정보 페이지</summary>
   <p>
    1. 강의 정보, 강사 정보를 확인 할 수 있습니다. <br>
    2. 강사가 만든 다른 강의를 찾아 볼 수 있습니다. <br>
    3. 사용자 상태에 따라서, 장바구니, 강의 보기 등 버튼이 달라집니다.
  <p>
  <img loading="lazy" src="https://github.com/rladygks329/Bloom/assets/64533351/afcd0e28-c84b-4208-87a5-35180b6f28ed">
</details>

## 사용 기술

| 분류                 | Badge                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| -------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| **Front - end**      | <img src="https://img.shields.io/badge/HTML5-E34F26?style=flat-square&amp;logo=html5&amp;logoColor=white"> <img src="https://img.shields.io/badge/css3-1572B6?style=flat-square&logo=css3&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=flat-square&logo=javascript&logoColor=white"> <img src="https://img.shields.io/badge/jQuery-0769AD?style=flat-square&amp;logo=jQuery&amp;logoColor=white"> <img src="https://img.shields.io/badge/bootstrap-7952B3?style=flat-square&logo=bootstrap&logoColor=white"> |
| **Back - end**       | <img src="https://img.shields.io/badge/Spring-6DB33F?style=flat-square&amp;logo=Spring&amp;logoColor=white">                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| **Version Control**  | <img src="https://img.shields.io/badge/git-F05032?style=flat-square&logo=git&logoColor=white">                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| **Video Processing** | <img src="https://img.shields.io/badge/ffmpeg-007808?style=flat-square&logo=ffmpeg&logoColor=white">                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| **DB**               | <img src="https://img.shields.io/badge/ORACLE-F80000?style=flat-square&logo=oracle&logoColor=white">                                                                                                                                                                                                                                                                                                                                                                                                                                             |

## ERD - Diagram

![blooming ERD 최종본](https://github.com/rladygks329/Bloom/assets/64533351/13176e35-26b7-44b3-a819-a6e5b6d03cd2)

## 컨벤션과 협업전략

<details>
  <summary> Git 관련 </summary> 
  <br>
  <p>1. 커밋 메세지는 update, feat 두가지로 시작해야한다. <br> 2. 브렌치명은 feature/기능 형식이여야한다. <br> 3. 머지시 develop에 합친 후 이상이 없으면 main으로 병합한다.<br> 적은 인원 수와 깃 활용 능력을 고려하여 컨벤션은 최대한 간단하게 가져갔습니다. 
  </p>
</details>
<details>
  <summary> 자바 관련</summary> 
  <br>
  <p> 1. 구글 formatter 를 사용한다. <br> 2. restful한 method를 구현하려고 노력한다. <br>
  3. 함수명과 변수명을 적절하게 유지한다. <br> 4. else문을 적게 쓴다. <br>

> 머지 할 때 코드리뷰를 진행하는 방식으로 코드를 계속 고쳐왔습니다.

  </p>
</details>

## 제작인원 및 기간

- **총 제작인원:** <a href="https://github.com/rladygks329">김요한</a>, <a href="https://github.com/cho100323">조학용</a> | 해당 링크를 누르면 깃허브 페이지로 이동합니다.
- **제작 기간:** 2023/10/20 ~ 2023/12/17
