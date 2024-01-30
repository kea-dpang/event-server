package kea.dpang.eventserver.client;

import kea.dpang.eventserver.base.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "seller-service")
// eureka를 사용하므로 url은 생략
public interface SellerSerivceClient {
    @GetMapping("/api/seller/findName")
    ResponseEntity<SuccessResponse<String>> getSellerName(@RequestParam Long id);
}
