package example.todolist.common;

import org.springframework.http.HttpStatus;

public class ResponseMessage<T> {
    private int statusCode = HttpStatus.OK.value();
    private String message = "";
    protected T data;

    public static ResponseMessage<String> ok() {
        return new ResponseMessage<>("");
    }

    public static <T> ResponseMessage<T> ok(T data) {
        return new ResponseMessage<>(data);
    }

    private ResponseMessage(T data) {
        this.data = data;
    }

    private ResponseMessage() {}

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
