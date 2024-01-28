package kea.dpang.eventserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventStatusScheduler {

    private final EventServiceImpl eventService;

    @Scheduled(cron = "0 0 0 * * *") //매일 자정에 실행
    public void updateEventStatus() {
        eventService.updateEventStatus();
    }
}
