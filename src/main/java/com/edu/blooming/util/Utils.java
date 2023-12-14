package com.edu.blooming.util;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class Utils {
  public static int parseInt(String s, int defaultValue) {
    try {
      return Integer.parseInt(s);
    } catch (Exception e) {
      return defaultValue;
    }
  }

  /**
   * This method to get base URL of the application
   * 
   * @param request the HttpServletRequest object
   * @return the base URL
   */
  public static String getBaseUrl(HttpServletRequest request) {
    String baseUrl =
        ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();

    return baseUrl;
  }
}
