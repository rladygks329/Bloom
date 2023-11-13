package com.edu.blooming.controller;

import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.edu.blooming.domain.BoardVO;
import com.edu.blooming.service.BoardService;
import com.edu.blooming.util.PageCriteria;
import com.edu.blooming.util.PageMaker;

@Controller
@RequestMapping(value = "/board")
public class BoardController {
  private static final Logger logger = LoggerFactory.getLogger(BoardController.class);

  @Autowired
  private BoardService boardService;

  @GetMapping("/list")
  public void list(Model model, Integer page, Integer numsPerPage, String option, String keyword) {
    logger.info("list() 호출");
    logger.info("page = " + page + ", numsPerPage = " + numsPerPage);
    List<BoardVO> list = null;
    // Paging 처리
    PageCriteria criteria = new PageCriteria();
    if (page != null) {
      criteria.setPage(page);
    }

    if (numsPerPage != null) {
      criteria.setNumsPerPage(numsPerPage);
    }

    PageMaker pageMaker = new PageMaker();
    if (option != null) {
      if (option.equals("searchNickname")) {
        logger.info("searchNickname");
        list = boardService.readByNickname(criteria, keyword);
        pageMaker.setTotalCount(boardService.getTotalCountsByNickname(keyword));
      } else if (option.equals("searchTitleOrContent")) {
        logger.info("searchTitleOrContent");
        list = boardService.readByTitleOrContent(criteria, keyword);
        pageMaker.setTotalCount(boardService.getTotalCountsByTitleOrContent(keyword));
      }
    } else {
      logger.info("totalCount = " + pageMaker.getTotalCount());
      list = boardService.read(criteria);
      pageMaker.setTotalCount(boardService.getTotalCounts());
    }
    model.addAttribute("list", list);
    model.addAttribute("option", option);
    model.addAttribute("keyword", keyword);

    pageMaker.setCriteria(criteria);
    pageMaker.setPageData();
    model.addAttribute("pageMaker", pageMaker);
  } // end list()

  @GetMapping("/detail")
  public String detail(Model model, @RequestParam Integer boardId, @RequestParam Integer page,
      HttpServletRequest request, HttpServletResponse response) {
    // 게시글 조회 코드
    BoardVO vo = boardService.read(boardId);
    model.addAttribute("vo", vo);
    model.addAttribute("page", page);

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

  @GetMapping("/like/{boardId}/{memberId}")
  @ResponseBody
  public boolean getLikeStatus(@PathVariable("boardId") int boardId,
      @PathVariable("memberId") int memberId) {
    boolean isLike = boardService.checkIsLike(memberId, boardId);
    return isLike;
  }

  @GetMapping("/register")
  public void registerGET() {
    logger.info("registerGET()");
  }

  @PostMapping("/register")
  public String registerPOST(BoardVO vo, RedirectAttributes reAttr) {
    // RedirectAttributes - 리다이렉트 시 데이터를 전달하기 위한 인터페이스
    logger.info("registerPOST() 호출");
    logger.info(vo.toString());
    int result = boardService.create(vo);
    logger.info(result + "행 삽입");
    if (result == 1) {
      reAttr.addFlashAttribute("insert_result", "success");
      return "redirect:/board/list";
    } else {
      return "redirect:/board/register";
    }
  } // end registerPOST()

  @GetMapping("/update")
  public void updateGET(Model model, Integer boardId, Integer page) {
    logger.info("updateGET() 호출 : boardId = " + boardId);
    BoardVO vo = boardService.read(boardId);
    model.addAttribute("vo", vo);
    model.addAttribute("page", page);
  }

  @PostMapping("/update")
  public String updatePOST(BoardVO vo, Integer page) {
    logger.info("updatePOST()호출: vo = " + vo.toString());
    int result = boardService.update(vo);
    logger.info("page : " + page + "result : " + result);
    if (result == 1) {
      return "redirect:/board/detail?boardId=" + vo.getBoardId() + "&page=" + page;
    } else {
      return "redirect:/board/update?boardId=" + vo.getBoardId();
    }
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

  @PostMapping("/deletOrUpdate")
  public String deleteOrUpdatePOST(BoardVO vo) {
    logger.info("deleteOrUpdatePOST()호출: vo = " + vo.toString());
    int result = boardService.deleteOrUpdate(vo);
    if (result == 1) {
      return "redirect:/board/list";
    } else {
      return "redirect:/board/detail?boardId=" + vo.getBoardId();
    }
  }


}


