package kea.dpang.eventserver.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Transient;
import kea.dpang.eventserver.dto.SellerEventDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * 판매처 이벤트 테이블의 Entity
 * EventEntity와 상속 관계 매핑이 되어 있다.
 */
@Entity
@DiscriminatorValue(Types.SELLER)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class SellerEventEntity extends EventEntity {

    /**
     * 판매처 ID 외래키
     * 판매처 이벤트 대상 판매처
     */
    private Long sellerId;

    /**
     * 판매처 이벤트 DTO를 build 하는 메서드
     * @return build된 판매처 이벤트 DTO
     */
    public SellerEventDto toSellerEventDto(){
        return SellerEventDto.builder()
                .discountRate(this.getDiscountRate())
                .id(this.getId())
                .eventName(this.getEventName())
                .eventStatus(this.getEventStatus())
                .startDate(this.getStartDate())
                .endDate(this.getEndDate())
                .imagePath(this.getImagePath())
                .sellerId(this.getSellerId())
                .build();
    }

    /**
     * 판매처 이벤트 수정 메서드
     * @param sellerEvent 판매처 이벤트 수정 정보가 담긴 DTO
     */
    public void updateSellerEvent(SellerEventDto sellerEvent){
        updateEvent(
                sellerEvent.getEventName(),
                sellerEvent.getImagePath(),
                sellerEvent.getDiscountRate(),
                sellerEvent.getStartDate(),
                sellerEvent.getEndDate()
        );
        this.sellerId = sellerEvent.getSellerId();
    }
}
