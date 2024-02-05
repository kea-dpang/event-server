package kea.dpang.eventserver.client;

import kea.dpang.eventserver.base.BaseResponse;
import kea.dpang.eventserver.base.SuccessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name="item-server")
// eureka를 사용하므로 url은 생략
public interface ItemServiceClient {
    @GetMapping("/api/item/findName")
    ResponseEntity<SuccessResponse<String>> getItemName(@RequestParam Long id);

    @PostMapping("")
    ResponseEntity<BaseResponse> postItemList(@RequestBody List<Long> itemIds);
}
