package example.todolist.common;

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
