package example.todolist.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageResponse<T> extends ResponseMessage<T> {
    private final int totalPages;
    private final Long totalElements;
    private final int paginationSize;

    public PageResponse(int totalPages, Long totalElements, int paginationSize, T data) {
        super(data);
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.paginationSize = paginationSize;
    }

    @JsonProperty(value = "isPaged")
    public boolean isPaged() {
        return true;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public int getPaginationSize() {
        return paginationSize;
    }
}
