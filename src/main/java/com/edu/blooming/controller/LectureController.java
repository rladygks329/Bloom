package com.edu.blooming.controller;

import static com.edu.blooming.util.Utils.parseInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.domain.LectureVOBuilder;
import com.edu.blooming.domain.LessonVO;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.service.LectureService;
import com.edu.blooming.service.LessonService;
import com.edu.blooming.util.PageCriteria;
import com.edu.blooming.util.PageMaker;

@Controller
@RequestMapping(value = "/lecture")
public class LectureController {
  private static final Logger logger = LoggerFactory.getLogger(LectureController.class);
  private static final Map<String, Integer> ORDER_TYPE_MAP = new HashMap<>();
  static {
    ORDER_TYPE_MAP.put("price-desc", LectureVO.ORDER_TYPE_PRICE_DESC);
    ORDER_TYPE_MAP.put("price-asc", LectureVO.ORDER_TYPE_PRICE_ASC);
    ORDER_TYPE_MAP.put("famous", LectureVO.ORDER_TYPE_LIKE_COUNT_DESC);
    ORDER_TYPE_MAP.put("sales", LectureVO.ORDER_TYPE_SALES_COUNT_DESC);
  }

  @Autowired
  private LectureService lectureService;

  @Autowired
  private LessonService lessonService;

  @GetMapping("/list")
  public void lectureGET(Model model, String page, String numsPerPage, String keyword,
      String order) {
    logger.info("lectureGET() 호출");

    int orderType = ORDER_TYPE_MAP.getOrDefault(order, LectureVO.ORDER_TYPE_DEFAULT);
    PageCriteria criteria = new PageCriteria(parseInt(page, 1), parseInt(numsPerPage, 3));

    List<LectureVO> list;
    PageMaker pageMaker = new PageMaker();
    pageMaker.setCriteria(criteria);

    if (keyword != null) {
      list = lectureService.read(criteria, keyword, orderType);
      pageMaker.setTotalCount(lectureService.getTotalCounts(keyword));
      model.addAttribute("keyword", keyword);
    } else {
      list = lectureService.read(criteria, orderType);
      pageMaker.setTotalCount(lectureService.getTotalCounts());
    }
    pageMaker.setPageData();

    model.addAttribute("order", order);
    model.addAttribute("lectureList", list);
    model.addAttribute("pageMaker", pageMaker);
  }

  @GetMapping("/detail")
  public String lectureDetailGET(HttpServletRequest request, Model model, int lectureId) {
    logger.info("lectureDetailGET() 호출 lectureId : lectureId");

    LectureVO lecture = lectureService.read(lectureId);
    // 찾는 강의가 없는 경우
    if (lecture == null) {
      model.addAttribute("msg", "찾으시는 강의가 존재하지 않습니다.");
      model.addAttribute("url", "list");
      return "alert";
    }

    List<LessonVO> lessons = lessonService.getByLectureId(lectureId);
    model.addAttribute("like", false);
    model.addAttribute("cart", false);
    model.addAttribute("purchase", false);
    model.addAttribute("lessons", lessons);
    model.addAttribute("lectureId", lectureId);
    model.addAttribute("lecture", lecture);

    HttpSession session = request.getSession();
    if (session.getAttribute("loginVo") != null) {
      // 로그인한 상태라면 좋아요, 결제 유무, 장바구니에 있는지 검사한 후 정보 넣기
      int memberId = ((MemberVO) session.getAttribute("loginVo")).getMemberId();
      Map<String, Object> status = lectureService.getUserStatus(memberId, lectureId);

      model.addAttribute("memberId", memberId);
      model.addAttribute("like", status.get("isLike"));
      model.addAttribute("cart", status.get("isCart"));
      model.addAttribute("purchase", status.get("isPurchase"));
    }

    return "/lecture/detail";
  }

  @GetMapping("/upload")
  public String lectureUploadGET(Model model, HttpServletRequest request) {
    logger.info("lectureUploadGET() 호출");

    int memberId = (int) request.getAttribute("memberId");
    String memberLevel = (String) request.getAttribute("memberLevel");

    if (!memberLevel.equals("instructor")) {
      model.addAttribute("msg", "강사만이 업로드할 수 있습니다.");
      model.addAttribute("url", "list");
      return "alert";
    }

    model.addAttribute("memberId", memberId);

    return "/lecture/upload";
  }

  // @formatter:off
  @PostMapping("/upload")
  public String lectureUploadPOST(String lectureTitle, Integer memberId, String lectureDescription, int lecturePrice,
      String lectureThumbnailUrl, String[] lectureVideosURL, String[] lectureVideosTitle ) {
    logger.info("lectureUploadPOST() 호출");
    
    for(String s: lectureVideosURL) {
      logger.info("lectureVideosURL : " + s);
    }
    
    List<LessonVO> lessons = new ArrayList<>();
    for(int i=0; i<lectureVideosURL.length; i++) {
      LessonVO lesson = new LessonVO(-1, -1, -1, -1, lectureVideosTitle[i], lectureVideosURL[i]);
      lessons.add(lesson);
    }
    
    LectureVO lecture = new LectureVOBuilder()
        .memberId(memberId)
        .lectureTitle(lectureTitle)
        .lectureDescription(lectureDescription)
        .lecturePrice(lecturePrice)
        .lectureThumbnailUrl(lectureThumbnailUrl)
        .build();
    
    logger.info("vo : " + lecture.toString());
    int result = lectureService.create(lecture, lessons);
    return "redirect:/lecture/list";
  }
  // @formatter:on

  @GetMapping("/modify")
  public String getModify(HttpServletRequest request, Model model, String target) {
    HttpSession session = request.getSession();
    int memberId = ((MemberVO) session.getAttribute("loginVo")).getMemberId();
    int lectureId = 0;

    try {
      lectureId = Integer.parseInt(target);
    } catch (NumberFormatException e) {
      model.addAttribute("msg", "잘못된 요청입니다.");
      model.addAttribute("url", "list");
      return "alert";
    }

    LectureVO vo = lectureService.read(lectureId);
    if (vo == null) {
      model.addAttribute("msg", "해당하는 강의가 없습니다.");
      model.addAttribute("url", "list");
      return "alert";
    }

    if (vo.getMemberId() != memberId) {
      model.addAttribute("msg", "자신이 만든 강의만 수정 가능 합니다.");
      model.addAttribute("url", "list");
      return "alert";
    }

    List<LessonVO> list = lessonService.getByLectureId(lectureId);
    model.addAttribute("lecture", vo);
    model.addAttribute("lessons", list);
    return "/lecture/modify";
  }

  @PostMapping("/modify")
  public String postModify(String lectureTitle, String lectureDescription, int lecturePrice,
      String lectureThumbnailUrl, String[] lessonName, String[] lessonUrl, int[] lessonId) {
    logger.info("modify post 실행");
    logger.info("lectureTitle: " + lectureTitle + "lectureDesc: " + lectureDescription + " price: "
        + lecturePrice);
    logger.info("lectureThumbnailUrl: " + lectureThumbnailUrl);
    List<LessonVO> lessons = new ArrayList<>();

    for (int i = 0; i < lessonName.length; i++) {
      LessonVO lesson = new LessonVO(lessonId[i], -1, -1, -1, lessonName[i], lessonUrl[i]);
      logger.info(lesson.toString());
      lessons.add(lesson);
    }

    return "redirect:/member/mypage";
  }

  @GetMapping("/{lectureId}/course")
  public String getCourse(Model model, @PathVariable("lectureId") int lectureId) {
    List<LessonVO> list = lessonService.getByLectureId(lectureId);

    if (list.size() == 0) {
      model.addAttribute("msg", "찾으시는 강의가 존재하지 않습니다.");
      model.addAttribute("url", "list");
      return "alert";
    }

    model.addAttribute("lessons", list);
    model.addAttribute("head", list.get(0));
    model.addAttribute("headURL", list.get(0).getLessonUrl().split("\\.")[0]);
    return "/lecture/course";
  }

  /// @formatter:off
  @PostMapping("/like/{lectureId}/{memberId}")
  public ResponseEntity<Integer> likeLecture(
      @PathVariable("lectureId") int lectureId,
      @PathVariable("memberId") int memberId) {
    lectureService.likeLecture(memberId, lectureId);
    int result = lectureService.read(lectureId).getLectureLikeCount();
    return new ResponseEntity<Integer>(result, HttpStatus.OK);
  }

  @DeleteMapping("/like/{lectureId}/{memberId}")
  public ResponseEntity<Integer> dislikeLecture(
      @PathVariable("lectureId") int lectureId,
      @PathVariable("memberId") int memberId) {
    lectureService.dislikeLecture(memberId,lectureId);
    int result = lectureService.read(lectureId).getLectureLikeCount();
    return new ResponseEntity<Integer>(result, HttpStatus.OK);
  }
  
}
