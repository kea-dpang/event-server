package kea.dpang.eventserver.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "type")
// default name = DTYPE
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String event_name;
    private String image_path;
    private String event_status;
    private double discount_rate;
    @CreationTimestamp
    private LocalDateTime registration_date;
    private LocalDate start_date;
    private LocalDate end_date;

    public void updateEvent(String event_name, String image_path, String event_status, double discount_rate, LocalDate start_date, LocalDate end_date) {
        this.event_name = event_name;
        this.image_path = image_path;
        this.event_status = event_status;
        this.discount_rate = discount_rate;
        this.start_date = start_date;
        this.end_date = end_date;
    }
}
