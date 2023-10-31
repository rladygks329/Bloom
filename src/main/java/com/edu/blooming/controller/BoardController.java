package com.edu.blooming.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public void list(Model model, Integer page, Integer numsPerPage) {
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

    List<BoardVO> list = boardService.read(criteria);
    model.addAttribute("list", list);

    PageMaker pageMaker = new PageMaker();
    pageMaker.setCriteria(criteria);
    pageMaker.setTotalCount(boardService.getTotalCounts());
    pageMaker.setPageData();
    model.addAttribute("pageMaker", pageMaker);
  } // end list()

  @GetMapping("/detail")
  public void detail(Model model, Integer boardId, Integer page) {
    logger.info("detail() 호출 : boardId = " + boardId);
    List<BoardVO> list = boardService.read(boardId);
    model.addAttribute("list", list);
    model.addAttribute("page", page);
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
    BoardVO vo = boardService.readForUpdate(boardId);
    model.addAttribute("vo", vo);
    model.addAttribute("page", page);
  }

  @PostMapping("/update")
  public String updatePOST(BoardVO vo, Integer page) {
    logger.info("updatePOST()호출: vo = " + vo.toString());
    int result = boardService.update(vo);
    logger.info("page : " + page + "result : " + result);
    if (result == 1) {
      return "redirect:/board/list?page=" + page;
    } else {
      return "redirect:/board/update?boardId=" + vo.getBoardId();
    }
  }



}


