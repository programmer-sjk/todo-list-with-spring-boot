package example.todolist.todo;

import example.todolist.todo.domain.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    Optional<Todo> findByIdAndUserId(Long id, Long userId);
    List<Todo> findByUserId(Long userId, Pageable pageable);
    Page<Todo> findAllByUserId(Long userId, Pageable pageable);
}
