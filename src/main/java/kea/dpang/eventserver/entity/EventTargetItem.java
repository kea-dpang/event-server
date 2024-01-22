package kea.dpang.eventserver.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class EventTargetItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ItemEventEntity event_id;
    private Long item_id;

    public void update(Long item_id){
        this.item_id = item_id;
    }

    @Builder
    public EventTargetItem(ItemEventEntity event_id, Long item_id) {
        this.event_id = event_id;
        this.item_id = item_id;
    }
}