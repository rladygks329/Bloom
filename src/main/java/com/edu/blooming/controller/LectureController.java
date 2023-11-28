package com.edu.blooming.controller;

import static com.edu.blooming.util.Utils.parseInt;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.edu.blooming.domain.LectureVO;
import com.edu.blooming.domain.LessonVO;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.service.LectureService;
import com.edu.blooming.service.LessonService;
import com.edu.blooming.service.PurchaseService;
import com.edu.blooming.util.PageCriteria;
import com.edu.blooming.util.PageMaker;
import com.edu.blooming.util.Utils;

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

  @Autowired
  private PurchaseService purchaseService;

  @GetMapping("/list")
  public void lectureGET(Model model, String page, String numsPerPage, String keyword,
      String order) {
    logger.info("lectureGET() 호출");

    int orderType = ORDER_TYPE_MAP.getOrDefault(order, LectureVO.ORDER_TYPE_DEFAULT);
    PageCriteria criteria = new PageCriteria(parseInt(page, 1), parseInt(numsPerPage, 3));

    List<LectureVO> list = lectureService.read(criteria, keyword, orderType);
    PageMaker pageMaker = new PageMaker();
    pageMaker.setCriteria(criteria);
    pageMaker.setTotalCount(lectureService.getTotalCountsByKeyword(keyword));
    pageMaker.setPageData();

    model.addAttribute("keyword", keyword);
    model.addAttribute("order", order);
    model.addAttribute("lectureList", list);
    model.addAttribute("pageMaker", pageMaker);
  }

  @GetMapping("/detail")
  public String lectureDetailGET(HttpSession session, Model model, int lectureId) {
    logger.info("lectureDetailGET() 호출 lectureId : lectureId");

    LectureVO lecture = lectureService.read(lectureId);
    if (lecture == null) {
      model.addAttribute("msg", "찾으시는 강의가 존재하지 않습니다.");
      model.addAttribute("url", "list");
      return "alert";
    }

    List<LessonVO> lessons = lessonService.getByLectureId(lectureId);
    model.addAttribute("likeStatus", false);
    model.addAttribute("cartStatus", false);
    model.addAttribute("purchaseStatus", false);
    model.addAttribute("lessons", lessons);
    model.addAttribute("lectureId", lectureId);
    model.addAttribute("lecture", lecture);

    // 로그인한 상태라면 좋아요, 결제 유무, 장바구니에 있는지 검사한 후 정보 넣기
    if (session.getAttribute("loginVo") != null) {
      int memberId = ((MemberVO) session.getAttribute("loginVo")).getMemberId();
      Map<String, Object> status = lectureService.getUserStatus(memberId, lectureId);
      model.addAttribute("memberId", memberId);
      model.addAllAttributes(status);
    }
    return "/lecture/detail";
  }

  @GetMapping("/upload")
  public String lectureUploadGET(Model model, HttpServletRequest request) {
    logger.info("lectureUploadGET() 호출");

    int memberId = (int) request.getAttribute("memberId");
    model.addAttribute("memberId", memberId);
    model.addAttribute("postURL", "/blooming/lecture/upload");
    return "/lecture/modify";
  }

  // @formatter:off
  @PostMapping("/upload")
  public String lectureUploadPOST(LectureVO lecture, String[] lessonName, String[] lessonUrl, int[] lessonId, int[] lessonIndex) {
    logger.info("lectureUploadPOST() 호출 lecture: " + lecture.toString());
    
    // String 배열로 나누어진 lesson을 List<Lesson>으로 변경
    List<LessonVO> lessons = IntStream.range(0, lessonName.length)
                                      .mapToObj(i -> new LessonVO(lessonId[i], -1, -1, -1, lessonName[i], lessonUrl[i]))
                                      .collect(Collectors.toList());
    lectureService.create(lecture, lessons);
    return "redirect:/lecture/list";
  }
  // @formatter:on

  @GetMapping("/modify")
  public String getModify(HttpServletRequest request, Model model, String target) {
    int memberId = (int) request.getAttribute("memberId");
    int lectureId = Utils.parseInt(target, -1);

    if (lectureId == -1) {
      model.addAttribute("msg", "잘못된 요청입니다.");
      model.addAttribute("url", "/blooming/lecture/list");
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
    model.addAttribute("memberId", memberId);
    model.addAttribute("lecture", vo);
    model.addAttribute("lessons", list);
    model.addAttribute("postURL", "/blooming/lecture/modify");
    return "/lecture/modify";
  }

  // @formatter:off
  @PostMapping("/modify")
  public String postModify(LectureVO lecture, String[] lessonName, String[] lessonUrl, int[] lessonId, int[] lessonIndex) {
    logger.info("modify post 실행");
    
    // String 배열로 나누어진 lesson을 List<Lesson>으로 변경
    List<LessonVO> lessons = IntStream.range(0, lessonName.length)
                                      .mapToObj(i -> new LessonVO(lessonId[i], lecture.getLectureId(), -1, lessonIndex[i], lessonName[i], lessonUrl[i]))
                                      .collect(Collectors.toList());
    lectureService.update(lecture, lessons);
    return "redirect:/member/instructor-page";
  }
  //@formatter:on

  @GetMapping("/{lectureId}/course")
  public String getCourse(Model model, HttpServletRequest request,
      @PathVariable("lectureId") int lectureId) {

    // check purchase
    int memberId = (int) request.getAttribute("memberId");
    if (!purchaseService.checkPurchase(memberId, lectureId)) {
      model.addAttribute("msg", "강의를 구매하셔야 보실 수 있습니다.");
      model.addAttribute("url", "/blooming/lecture/list");
      return "alert";
    }

    List<LessonVO> lessons = lessonService.getByLectureId(lectureId);
    if (lessons.isEmpty()) {
      model.addAttribute("msg", "찾으시는 강의가 존재하지 않습니다.");
      model.addAttribute("url", "/blooming/lecture/list");
      return "alert";
    }

    model.addAttribute("lessons", lessons);
    model.addAttribute("head", lessons.get(0));
    model.addAttribute("headURL", lessons.get(0).getLessonUrl().split("\\.")[0]);
    return "/lecture/course";
  }

}
