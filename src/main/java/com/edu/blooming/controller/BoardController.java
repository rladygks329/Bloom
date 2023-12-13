package com.edu.blooming.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.domain.MemberVO;
import com.edu.blooming.service.BoardService;
import com.edu.blooming.util.MediaUtil;
import com.edu.blooming.util.PageCriteria;
import com.edu.blooming.util.PageMaker;
import com.google.gson.JsonObject;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
  private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

  @Value("${uploadPath.img}")
  private String uploadPath;

  @Autowired
  private BoardService boardService;

  @GetMapping("/list")
  public void list(Model model, Integer page, Integer numsPerPage, String option, String keyword) {
    logger.info("list() 호출");
    logger.info("page = " + page + ", numsPerPage = " + numsPerPage);

    // Paging 처리
    PageCriteria criteria = new PageCriteria();
    if (page != null) {
      criteria.setPage(page);
    }

    if (numsPerPage != null) {
      criteria.setNumsPerPage(numsPerPage);
    }

    List<BoardVO> list = boardService.read(criteria, option, keyword);

    PageMaker pageMaker = new PageMaker();
    pageMaker.setTotalCount(boardService.getTotalCounts(option, keyword));
    pageMaker.setCriteria(criteria);
    pageMaker.setPageData();

    model.addAttribute("page", page);
    model.addAttribute("list", list);
    model.addAttribute("option", option);
    model.addAttribute("keyword", keyword);
    model.addAttribute("pageMaker", pageMaker);
  } // end list()

  @GetMapping("/detail")
  public String detail(Model model, @RequestParam Integer boardId,
      @RequestParam(defaultValue = "1") Integer page, @RequestParam(required = false) String option,
      @RequestParam(required = false) String keyword, HttpServletRequest request,
      HttpServletResponse response, HttpSession session) {
    // 게시글 조회 코드
    BoardVO vo = boardService.read(boardId);

    if (vo == null) {
      model.addAttribute("msg", "찾으시는 게시글이 존재하지 않습니다.");
      model.addAttribute("url", "list");
      return "alert";
    }
    model.addAttribute("option", option);
    model.addAttribute("keyword", keyword);
    model.addAttribute("isLike", false);

    if ((MemberVO) session.getAttribute("loginVo") != null) {
      int memberId = ((MemberVO) session.getAttribute("loginVo")).getMemberId();
      boolean isLike = boardService.checkIsLike(memberId, boardId);
      model.addAttribute("isLike", isLike);
    }

    model.addAttribute("vo", vo);
    model.addAttribute("page", page);
    logger.info("page = " + page);
    logger.info("option = " + option);
    logger.info("keyword = " + keyword);

    // 쿠키 이름과 현재 게시글 ID 및 페이지를 조합하여 쿠키 이름 생성
    String cookieName = "viewed_" + boardId + "_page" + page;
    Cookie[] cookies = request.getCookies();
    boolean isViewed = false; // 해당 게시글을 조회한 여부

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(cookieName)) {
          isViewed = true;
          break;
        }
      }
    }

    if (!isViewed) {
      // 쿠키가 없는 경우: 조회수 증가 및 쿠키 설정
      boardService.updateViewCount(boardId);
      Cookie viewedCookie = new Cookie(cookieName, "1");
      viewedCookie.setMaxAge(3600);
      viewedCookie.setPath("/"); // 모든 경로에서 쿠키 사용
      response.addCookie(viewedCookie);
    }
    return "board/detail"; // JSP 페이지 경로만 반환
  }

  // @GetMapping("/like/{boardId}/{memberId}")
  // @ResponseBody
  // public boolean getLikeStatus(@PathVariable("boardId") int boardId,
  // @PathVariable("memberId") int memberId) {
  // boolean isLike = boardService.checkIsLike(memberId, boardId);
  // return isLike;
  // } // detail에서 같이 넘겨주기

  @GetMapping("/register")
  public void registerGET() {
    logger.info("registerGET()");
  }

  @PostMapping("/register")
  public String registerPOST(BoardVO vo, RedirectAttributes reAttr) {
    // RedirectAttributes - 리다이렉트 시 데이터를 전달하기 위한 인터페이스
    logger.info("registerPOST() 호출 vo : " + vo.toString());
    int result = boardService.create(vo);
    if (result == 1) {
      reAttr.addFlashAttribute("insert_result", "success");
      return "redirect:/board/list";
    }
    return "redirect:/board/register";
  }

  @GetMapping("/update")
  public String updateGET(Model model, Integer boardId, Integer page, HttpSession session) {
    logger.info("updateGET() 호출 : boardId = " + boardId);
    BoardVO vo = boardService.read(boardId);
    int memberId = vo.getMemberId();
    int sesMemberId = ((MemberVO) session.getAttribute("loginVo")).getMemberId();
    if (memberId != sesMemberId) {
      return "redirect:/main";
    }
    model.addAttribute("vo", vo);
    model.addAttribute("page", page);
    return "/board/update";
  }

  @PostMapping("/update")
  public String updatePOST(BoardVO vo, Integer page) {
    logger.info("updatePOST()호출: vo = " + vo.toString());
    int result = boardService.update(vo);
    logger.info("page : " + page + "result : " + result);
    if (result == 1) {
      return "redirect:/board/detail?boardId=" + vo.getBoardId() + "&page=" + page;
    }
    return "redirect:/board/update?boardId=" + vo.getBoardId();
  }

  @PostMapping("/like/{boardId}/{memberId}")
  public ResponseEntity<Integer> likeBoard(@PathVariable("boardId") int boardId,
      @PathVariable("memberId") int memberId) {
    boardService.likeBoard(boardId, memberId);
    int result = boardService.read(boardId).getBoardLikeCount();
    return new ResponseEntity<Integer>(result, HttpStatus.OK);
  }

  @DeleteMapping("/like/{boardId}/{memberId}")
  public ResponseEntity<Integer> dislikeBoard(@PathVariable("boardId") int boardId,
      @PathVariable("memberId") int memberId) {
    boardService.dislikeBoard(boardId, memberId);
    int result = boardService.read(boardId).getBoardLikeCount();
    return new ResponseEntity<Integer>(result, HttpStatus.OK);
  }

  @GetMapping("/deleteOrUpdate")
  public void deleteOrUpdateGET() {
    logger.info("deleteOrUpdateGET()");
  }

  @PostMapping("/deleteOrUpdate")
  public String deleteOrUpdatePOST(BoardVO vo) {
    logger.info("deleteOrUpdatePOST()호출: vo = " + vo.toString());
    int result = boardService.delete(vo);
    if (result == 1) {
      return "redirect:/board/list";
    }
    return "redirect:/board/detail?boardId=" + vo.getBoardId();
  }

  @ResponseBody
  @PostMapping("/upload")
  public ResponseEntity<Map<String, Object>> summer_image(
      @RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
    JsonObject jsonObject = new JsonObject();

    String fileRoot = uploadPath;
    // String fileRoot = "C:\\Study\\BloomingBucket\\img\\";
    logger.info("fileRoot = " + fileRoot);
    String originalFileName = multipartFile.getOriginalFilename(); // 오리지날 파일명
    logger.info("originalFileName = " + originalFileName);
    String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 파일 확장자
    logger.info("extension = " + extension);
    String savedFileName = UUID.randomUUID() + extension; // 저장될 파일 명
    logger.info("savedFileName = " + savedFileName);

    File targetFile = new File(fileRoot + savedFileName);
    Map<String, Object> result = new HashMap<String, Object>();
    try {
      InputStream fileStream = multipartFile.getInputStream();
      FileUtils.copyInputStreamToFile(fileStream, targetFile); // 파일 저장
      result.put("url", fileRoot + savedFileName);
      result.put("responseCode", "success");
      // contextroot + resources + 저장할 내부 폴더명
    } catch (IOException e) {
      FileUtils.deleteQuietly(targetFile); // 저장된 파일 삭제
      result.put("responseCode", "error");
      e.printStackTrace();
    }
    logger.info("result = " + result.toString());
    return new ResponseEntity<Map<String, Object>>(result, HttpStatus.OK);
  }

  @GetMapping("/display")
  public ResponseEntity<byte[]> display(String fileName) {
    logger.info("display() 호출");

    ResponseEntity<byte[]> entity = null;
    InputStream in = null;

    String filePath = uploadPath + fileName;

    try {
      in = new FileInputStream(filePath);

      // 파일 확장자
      String extension = filePath.substring(filePath.lastIndexOf(".") + 1);
      logger.info(extension);

      // 응답 헤더(response header)에 Content-Type 설정
      HttpHeaders httpHeaders = new HttpHeaders();
      httpHeaders.setContentType(MediaUtil.getMediaType(extension));
      // 데이터 전송
      entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), // 파일에서 읽은 데이터
          httpHeaders, // 응답 헤더
          HttpStatus.OK);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return entity;
  }



}


