package kea.dpang.eventserver.exception;

public class FeignException extends RuntimeException{
    public FeignException (String message, Throwable e) {super(message+e);}

}
