package example.todolist.common;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseMessage<T> {
    private int statusCode = HttpStatus.OK.value();
    private String message = "";
    protected T data = (T) "";

    public static ResponseMessage<String> ok() {
        return new ResponseMessage<>("");
    }

    public static <T> ResponseMessage<T> ok(T data) {
        return new ResponseMessage<>(data);
    }

    public static <T> ResponseMessage<T> noContent() {
        return new ResponseMessage<>(HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase(), null);
    }

    public static ResponseMessage<String> badRequest(String message) {
        return new ResponseMessage<>(HttpStatus.BAD_REQUEST.value(), message, "");
    }

    public static ResponseMessage<String> unauthorized(String message) {
        return new ResponseMessage<>(HttpStatus.UNAUTHORIZED.value(), message, "");
    }

    public static ResponseMessage<String> forbidden(String message) {
        return new ResponseMessage<>(HttpStatus.FORBIDDEN.value(), message, "");
    }

    public static ResponseMessage<String> notFound(String message) {
        return new ResponseMessage<>(HttpStatus.NOT_FOUND.value(), message, "");
    }

    protected ResponseMessage(T data) {
        this.data = data;
    }

    private ResponseMessage(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        if (data != null) {
            this.data = data;
        }
    }
}
