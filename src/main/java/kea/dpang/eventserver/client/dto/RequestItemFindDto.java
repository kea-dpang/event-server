package kea.dpang.eventserver.client.dto;

import lombok.Getter;

@Getter
public class RequestItemFindDto {
    private Long id;
    private String thumbnailImage;
    private String name;
    private int price;
    private int quantity;
    private int discountRate;
    private int discountPrice;
}
