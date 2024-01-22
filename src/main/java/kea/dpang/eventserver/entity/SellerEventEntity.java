package kea.dpang.eventserver.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@DiscriminatorValue("S")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class SellerEventEntity extends EventEntity {
    private Long seller_id;

    public void updateSellerEvent(String event_name, String image_path, String event_status, double discount_rate, LocalDate start_date, LocalDate end_date, Long seller_id){
        updateEvent(event_name,image_path,event_status,discount_rate,start_date,end_date);
        this.seller_id = seller_id;
    }
}
