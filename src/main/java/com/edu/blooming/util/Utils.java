package com.edu.blooming.util;

public class Utils {
  public static int parseInt(String s, int defaultValue) {
    try {
      return Integer.parseInt(s);
    } catch (Exception e) {
      return defaultValue;
    }
  }
}
