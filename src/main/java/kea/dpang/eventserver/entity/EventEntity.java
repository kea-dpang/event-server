package kea.dpang.eventserver.entity;

import jakarta.persistence.*;
import kea.dpang.eventserver.dto.EventDto;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 이벤트 테이블의 Entity
 * 상품 테이블과 판매처 테이블의 부모 테이블
 * @author Tomas
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Table(name = "event")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type")
// default name = DTYPE
public class EventEntity {
    /**
     * 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 이벤트 이름
     */
    @Column(name = "event_name")
    private String eventName;

    /**
     * 이벤트 배너 이미지 경로
     */
    @Column(name = "image_path")
    private String imagePath;

    /**
     * 이벤트 진행 상태
     * 대기, 진행, 종료
     */
    @Column(name="event_status")
    @Enumerated(EnumType.STRING)
    private Status eventStatus;

    /**
     * 이벤트 할인율
     */
    @Column(name = "discount_rate")
    private double discountRate;

    /**
     * 이벤트 등록 시간
     */
    @Column(name="registration_date")
    @CreationTimestamp
    private LocalDateTime registrationDate;

    /**
     * 이벤트 시작 날짜
     */
    @Column(name="start_date")
    private LocalDate startDate;

    /**
     * 이벤트 종료 날짜
     */
    @Column(name="end_date")
    private LocalDate endDate;

    /**
     * 이벤트 DTO를 build하는 메서드
     * @return build된 EventDto
     */
    public EventDto toEventDto(){
        // 이벤트 인스턴스가 ItemEventEntity에 상속하면 type을 item으로 정의, 그 외의 경우(SellerEventEntity에 상속)에는 seller로 정의
        String type = (this instanceof ItemEventEntity) ? Types.ITEM : Types.SELLER;
        return EventDto.builder()
                .id(this.id)
                .eventName(this.eventName)
                .eventStatus(this.eventStatus)
                .startDate(this.startDate)
                .endDate(this.endDate)
                .registrationDate(this.registrationDate)
                .imagePath(this.imagePath)
                .type(type)
                .build();
    }

    /**
     * 이벤트 수정 메서드
     * ItemEvent나 SellerEvent를 수정할 때 사용되는 메서드이다.
     * @param event_name 이벤트 이름
     * @param image_path 이벤트 이미지 경로
     * @param discount_rate 이벤트 할인율
     * @param start_date 이벤트 시작 날짜
     * @param end_date 이벤트 종료 날짜
     */
    public void updateEvent(String event_name, String image_path, double discount_rate, LocalDate start_date, LocalDate end_date) {
        this.eventName = event_name;
        this.imagePath = image_path;
        this.discountRate = discount_rate;
        this.startDate = start_date;
        this.endDate = end_date;
    }

    /**
     * 이벤트 상태를 변경하는 메서드입니다.
     * @param event_status 변경할 이벤트 상태
     */
    public void updateStatus(Status event_status){
        this.eventStatus = event_status;
    }

}