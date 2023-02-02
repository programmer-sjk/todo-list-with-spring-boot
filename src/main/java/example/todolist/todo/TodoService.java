package example.todolist.todo;

import example.todolist.todo.domain.Todo;
import example.todolist.todo.dto.TodoRequest;
import example.todolist.todo.dto.TodoUpdateStatusRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Transactional
    public void insertTodo(TodoRequest request) {
        todoRepository.save(request.toEntity());
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
}
