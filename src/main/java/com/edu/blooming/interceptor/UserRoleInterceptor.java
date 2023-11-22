package com.edu.blooming.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.edu.blooming.domain.MemberVO;

public class UserRoleInterceptor extends HandlerInterceptorAdapter {
  private static final Logger logger = LoggerFactory.getLogger(UserRoleInterceptor.class);

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    HttpSession session = request.getSession();

    MemberVO user = (MemberVO) session.getAttribute("loginVo");
    if (user == null) {
      response.sendRedirect("/blooming/member/login");
      return false;
    }

    String memberLevel = user.getMemberLevel();
    if (!memberLevel.equals("instructor")) {
      response.sendRedirect("/blooming/main");
      return false;
    }

    return super.preHandle(request, response, handler);
  }

}
