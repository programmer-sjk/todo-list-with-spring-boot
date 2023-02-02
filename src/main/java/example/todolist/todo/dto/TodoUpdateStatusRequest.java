package example.todolist.todo.dto;

import jakarta.validation.constraints.NotBlank;

public class TodoUpdateStatusRequest {
    @NotBlank
    private String status;

    protected TodoUpdateStatusRequest() {}

    public TodoUpdateStatusRequest(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
