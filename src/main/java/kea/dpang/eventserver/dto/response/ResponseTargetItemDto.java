package kea.dpang.eventserver.dto.response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseTargetItemDto {

    /**
     * 상품 이벤트 대상 상품 ID
     */
    private Long itemId;

    /**
     * 상품 이벤트 대상 상품 이름
     */
    private String itemName;

}
