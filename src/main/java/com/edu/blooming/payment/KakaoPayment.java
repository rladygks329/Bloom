package com.edu.blooming.payment;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class KakaoPayment implements PaymentMethod {

  // 가맹점 테스트 코드
  @Value("${pay.kakao.cid}")
  private String cid;

  @Value("${pay.kakao.admin.key}")
  private String admin_Key;

  private static ConcurrentHashMap<Integer, Map<String, Object>> readyPayments =
      new ConcurrentHashMap<Integer, Map<String, Object>>();

  private Logger logger = LoggerFactory.getLogger(KakaoPayment.class);

  /*
   * 결제 준비
   */
  @Override
  public Map<String, Object> readyForPay(String baseURL, int memberId, String name, int price) {
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();

    String orderNum = "aaaaaaaa";
    String userId = Integer.toString(memberId);

    parameters.add("cid", cid);
    parameters.add("partner_order_id", orderNum);
    parameters.add("partner_user_id", userId);
    parameters.add("item_name", name);
    parameters.add("quantity", Integer.toString(1));
    parameters.add("total_amount", Integer.toString(price));
    parameters.add("vat_amount", "0"); // 부가세
    parameters.add("tax_free_amount", "0"); // 상품 비과세 금액
    parameters.add("approval_url", baseURL + "/blooming/purchase/kakao/success");
    parameters.add("cancel_url", baseURL + "/blooming/purchase/cancel");
    parameters.add("fail_url", baseURL + "/blooming/purchase/fail");

    // 파라미터, 헤더
    HttpEntity<MultiValueMap<String, String>> requestEntity =
        new HttpEntity<>(parameters, this.getHeaders());

    // 외부에 보낼 url
    RestTemplate restTemplate = new RestTemplate();
    Map<String, Object> result = restTemplate
        .postForObject("https://kapi.kakao.com/v1/payment/ready", requestEntity, Map.class);

    Map<String, Object> userInfo = new HashMap<String, Object>();
    userInfo.put("tid", result.get("tid"));
    userInfo.put("orderNum", orderNum);
    readyPayments.put(memberId, userInfo);

    return result;
  }

  /**
   * 결제 완료 승인
   */
  public Map<String, Object> approvePay(int memberId, String token) {
    Map<String, Object> userInfo = readyPayments.get(memberId);
    String tid = (String) userInfo.get("tid");
    String orderNum = (String) userInfo.get("orderNum");
    readyPayments.remove(memberId);

    // 카카오 요청
    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.add("cid", cid);
    parameters.add("tid", tid);
    parameters.add("partner_order_id", orderNum);
    parameters.add("partner_user_id", Integer.toString(memberId));
    parameters.add("pg_token", token);

    // 파라미터, 헤더
    HttpEntity<MultiValueMap<String, String>> requestEntity =
        new HttpEntity<>(parameters, this.getHeaders());

    // 외부에 보낼 url
    RestTemplate restTemplate = new RestTemplate();

    Map<String, Object> result = restTemplate
        .postForObject("https://kapi.kakao.com/v1/payment/approve", requestEntity, Map.class);

    return result;
  }

  /*
   * 카카오 요구 헤더값
   */
  private HttpHeaders getHeaders() {
    HttpHeaders httpHeaders = new HttpHeaders();
    String auth = "KakaoAK " + admin_Key;
    httpHeaders.set("Authorization", auth);
    httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
    return httpHeaders;
  }

}
