package example.todolist.todo;

import example.todolist.todo.domain.Todo;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoResponse;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
import example.todolist.user.UserRepository;
import example.todolist.user.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

    public TodoResponse find(Long id) {
        return new TodoResponse(findById(id));
    }

    public List<TodoResponse> findAll() {
        return todoRepository.findAll()
                .stream()
                .map(TodoResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void insertTodo(Long userId, TodoRequest request) {
        User user = findOwner(userId);
        todoRepository.save(request.toEntity(user));
    }

    @Transactional
    public void updateStatus(Long id, TodoUpdateStatusRequest request) {
        Todo todo = findById(id);
        todo.updateStatus(request.getStatus());
    }

    private Todo findById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Todo 목록이 존재하지 않습니다."));
    }

    private User findOwner(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User가 존재하지 않습니다."));
    }
}
