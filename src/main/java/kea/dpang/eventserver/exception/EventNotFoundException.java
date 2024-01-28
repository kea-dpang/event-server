package kea.dpang.eventserver.exception;

public class EventNotFoundException extends RuntimeException{
    public EventNotFoundException(Long id) {super("해당 ID를 가진 이벤트가 존재하지 않습니다: "+id);}
}
