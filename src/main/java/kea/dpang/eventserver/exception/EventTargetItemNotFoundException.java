package kea.dpang.eventserver.exception;

public class EventTargetItemNotFoundException extends RuntimeException{
    public EventTargetItemNotFoundException (Long id) {super("해당 ID를 가진 이벤트 상품이 존재하지 않습니다: "+id);}
}