package task_manager.application.data;

import task_manager.application.model.TaskItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TaskItemDAO {

    TaskItem save(TaskItem taskItem);

    Optional<TaskItem> findById(String id);

    List<TaskItem> findAll();

    List<TaskItem> findByDeadLineBetween(LocalDateTime start, LocalDateTime end);

    List<TaskItem> findByDeadLineBefore(LocalDateTime end);

    List<TaskItem> findByDeadLineAfter(LocalDateTime start);

    TaskItem update(TaskItem taskItem);

    boolean delete(TaskItem taskItem);
}
