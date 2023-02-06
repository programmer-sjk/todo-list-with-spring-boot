package example.todolist.common;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PageResponse<T> extends ResponseMessage<T> {
    private int totalPages;
    private Long totalElements;
    private int paginationSize;

    private PageResponse() {}

    private PageResponse(Builder<T> builder) {
        super(builder.data);
        this.totalPages = builder.totalPages;
        this.totalElements = builder.totalElements;
        this.paginationSize = builder.paginationSize;
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

    public static class Builder<T> {
        private int totalPages;
        private Long totalElements;
        private int paginationSize;
        private T data;

        public Builder<T> totalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder<T> totalElements(Long totalElements) {
            this.totalElements = totalElements;
            return this;
        }

        public Builder<T> paginationSize(int paginationSize) {
            this.paginationSize = paginationSize;
            return this;
        }

        public Builder <T> data(T data) {
            this.data = data;
            return this;
        }

        public PageResponse<T> build() {
            return new PageResponse<>(this);
        }
    }
}
