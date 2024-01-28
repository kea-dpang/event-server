package kea.dpang.eventserver.entity;

/**
 * 이벤트 진행 상태를 나타내는 enum 클래스
 */
public enum Status {
    WAITING, // 대기 중인 이벤트
    PROCEEDING, // 진행 중인 이벤트
    END // 종료된 이벤트
}
