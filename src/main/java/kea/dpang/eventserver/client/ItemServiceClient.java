package kea.dpang.eventserver.client;

import kea.dpang.eventserver.base.BaseResponse;
import kea.dpang.eventserver.base.SuccessResponse;
import kea.dpang.eventserver.client.dto.RequestItemFindDto;
import kea.dpang.eventserver.client.dto.RequestItemServiceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name="item-server")
// eureka를 사용하므로 url은 생략
public interface ItemServiceClient {
    // 상품의 이름을 받아오기 위한 API
    @GetMapping("/api/items/{itemId}")
    ResponseEntity<SuccessResponse<RequestItemFindDto>> getItemName(@PathVariable Long itemId);

    // 상품을 이벤트에 등록/수정하기 위한 API
    @PutMapping("/api/items/eventDiscount")
    ResponseEntity<BaseResponse> registerItemsToEvent(@RequestBody RequestItemServiceDto requestDto);

    // 이벤트가 삭제될 경우 해당 이벤트의 대상이 되었던 상품들의 할인율 제거
    @DeleteMapping("/api/items/eventDiscount")
    ResponseEntity<BaseResponse> deleteEvent(@RequestParam Long eventId);

}
