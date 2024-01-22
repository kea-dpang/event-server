package kea.dpang.eventserver.dto.response;

import lombok.Builder;
import lombok.experimental.SuperBuilder;

@SuperBuilder
public class SellerEventGetDto extends ItemEventGetDto {
    //이벤트 사진
    private String image_path;
    //판매처 ID
    private Long seller_id;
}
