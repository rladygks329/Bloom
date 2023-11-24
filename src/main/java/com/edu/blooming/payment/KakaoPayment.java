package com.edu.blooming.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class KakaoPayment implements PaymentMethod<KakaoReadyResponse> {

  @Value("${pay.kakao.cid}")
  // 가맹점 테스트 코드
  private String cid;

  @Value("${pay.kakao.admin.key}")
  private String admin_Key;

  @Override
  public ResponseEntity<KakaoReadyResponse> pay(int memberId, int price) throws Exception {

    MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
    parameters.add("cid", cid);
    parameters.add("partner_order_id", "가맹점 주문 번호");
    parameters.add("partner_user_id", "가맹점 회원 ID");
    parameters.add("item_name", "상품명");
    parameters.add("quantity", Integer.toString(1));
    parameters.add("total_amount", Integer.toString(price));
    parameters.add("vat_amount", "0"); // 부가세
    parameters.add("tax_free_amount", "0"); // 상품 비과세 금액
    parameters.add("approval_url", "http://localhost:8080/blooming/payment/success");
    parameters.add("cancel_url", "http://localhost:8080/blooming/payment/cancel");
    parameters.add("fail_url", "http://localhost:8080/blooming/payment/fail");

    HttpHeaders httpHeaders = new HttpHeaders();
    String auth = "KakaoAK " + admin_Key;
    httpHeaders.set("Authorization", auth);
    httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

    // 파라미터, 헤더
    HttpEntity<MultiValueMap<String, String>> requestEntity =
        new HttpEntity<>(parameters, httpHeaders);

    // 외부에 보낼 url
    RestTemplate restTemplate = new RestTemplate();
    KakaoReadyResponse result = restTemplate.postForObject(
        "https://kapi.kakao.com/v1/payment/ready", requestEntity, KakaoReadyResponse.class);
    return new ResponseEntity<KakaoReadyResponse>(result, HttpStatus.OK);
  }

}
