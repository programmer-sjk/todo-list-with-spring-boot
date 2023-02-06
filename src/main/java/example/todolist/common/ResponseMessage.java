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

    public static ResponseMessage<String> noContent() {
        return new ResponseMessage<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), "");
    }

    public static ResponseMessage<String> badRequest(String message) {
        return new ResponseMessage<>(HttpStatus.BAD_REQUEST.value(), message, "");
    }

    protected ResponseMessage(T data) {
        this.data = data;
    }

    private ResponseMessage(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    protected ResponseMessage() {}

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
