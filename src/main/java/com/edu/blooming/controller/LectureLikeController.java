package com.edu.blooming.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.edu.blooming.service.LectureService;

@Controller
@RequestMapping("/lecture/like/{lectureId}")
public class LectureLikeController {

  @Autowired
  private LectureService lectureService;

  /// @formatter:off
  @PostMapping
  public ResponseEntity<Integer> likeLecture(
      HttpServletRequest request,
      @PathVariable("lectureId") int lectureId
  ) {
    int memberId = (int) request.getAttribute("memberId");
    lectureService.likeLecture(memberId, lectureId);
    int result = lectureService.read(lectureId).getLectureLikeCount();
    return new ResponseEntity<Integer>(result, HttpStatus.OK);
  }

  @DeleteMapping
  public ResponseEntity<Integer> dislikeLecture(
      HttpServletRequest request,
      @PathVariable("lectureId") int lectureId
  ) {
    int memberId = (int) request.getAttribute("memberId");
    lectureService.dislikeLecture(memberId,lectureId);
    int result = lectureService.read(lectureId).getLectureLikeCount();
    return new ResponseEntity<Integer>(result, HttpStatus.OK);
  }
}
