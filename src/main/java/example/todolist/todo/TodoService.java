package example.todolist.todo;

import example.todolist.common.PageResponse;
import example.todolist.todo.domain.Todo;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoResponse;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
import example.todolist.user.UserRepository;
import example.todolist.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@Service
public class TodoService {
    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public List<TodoResponse> findRecent(Long userId, Pageable pageable) {
         return todoRepository.findByUserId(userId, pageable)
                 .stream()
                 .map(TodoResponse::new)
                 .collect(Collectors.toList());
    }

    public PageResponse<List<TodoResponse>> findAll(Long userId, Pageable pageable) {
        Page<Todo> todos = todoRepository.findAllByUserId(userId, pageable);
        List<TodoResponse> responses = todos.stream()
                .map(TodoResponse::new)
                .collect(Collectors.toList());

        return new PageResponse.Builder<List<TodoResponse>>()
                .totalPages(todos.getTotalPages())
                .totalElements(todos.getTotalElements())
                .paginationSize(todos.getSize())
                .data(responses)
                .build();
    }

    @Transactional
    public void createTodo(Long userId, TodoRequest request) {
        User user = findOwner(userId);
        todoRepository.save(request.toEntity(user));
    }

    @Transactional
    public void updateStatus(Long id, Long userId, TodoUpdateStatusRequest request) {
        Todo todo = findById(id, userId);
        todo.updateStatus(request.getStatus());
    }

    private Todo findById(Long id, Long userId) {
        return todoRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("Todo 목록이 존재하지 않습니다."));
    }

    private User findOwner(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User가 존재하지 않습니다."));
    }
}
