package com.edu.blooming.interceptor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.edu.blooming.domain.MemberVO;

public class AuthInterceptor extends HandlerInterceptorAdapter {
  private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    logger.info("===== preHandle 호출");

    HttpSession session = request.getSession();
    if (session.getAttribute("vo") == null) {
      logger.info("로그아웃 상태");
      String targetURL = saveDestination(request);
      response.sendRedirect("/blooming/member/login?targetURL=" + targetURL);
      return false;
    }

    MemberVO vo = (MemberVO) session.getAttribute("vo");
    request.setAttribute("memberId", vo.getMemberId());
    request.setAttribute("memberLevel", vo.getMemberLevel());
    return true;
  }

  private String saveDestination(HttpServletRequest request) {
    logger.info("saveDestination() 호출");

    String uri = request.getRequestURI();
    logger.info("요청 URI: " + uri);

    String contextRoot = request.getContextPath();
    uri = uri.replace(contextRoot, "");

    String queryString = request.getQueryString();
    logger.info("query string : " + queryString);

    String targetURL = uri;
    if (queryString != null) {
      targetURL += "?" + queryString;
    }

    logger.info("targetURL: " + targetURL);

    try {
      targetURL = URLEncoder.encode(targetURL, "UTF-8");
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }

    return targetURL;
  }

}
