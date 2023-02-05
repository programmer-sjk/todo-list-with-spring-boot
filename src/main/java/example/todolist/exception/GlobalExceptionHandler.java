package example.todolist.exception;

import example.todolist.common.ResponseMessage;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseMessage<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseMessage.badRequest(e.getMessage());
    }
}
