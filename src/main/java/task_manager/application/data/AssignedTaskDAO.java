package task_manager.application.data;

import task_manager.application.model.AssignedTask;

import java.util.List;
import java.util.Optional;

public interface AssignedTaskDAO {

    AssignedTask save(AssignedTask assignedTask);

    Optional<AssignedTask> findById(Integer id);

    List<AssignedTask> findAll();

    AssignedTask update(AssignedTask assignedTask);

    boolean delete(AssignedTask assignedTask);
}
