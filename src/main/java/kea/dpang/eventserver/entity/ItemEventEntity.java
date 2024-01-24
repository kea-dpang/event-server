package kea.dpang.eventserver.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Entity
@DiscriminatorValue("I")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class ItemEventEntity extends EventEntity{
    @OneToMany(mappedBy = "event_id")
    private List<EventTargetItem> item_id;

    public void updateItemEvent(String event_name, String image_path, String event_status, double discount_rate, LocalDate start_date, LocalDate end_date) {
        updateEvent(event_name,image_path,event_status,discount_rate,start_date,end_date);
    }
}
