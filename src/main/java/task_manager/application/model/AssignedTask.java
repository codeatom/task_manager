package task_manager.application.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "AssignedTask")
public class AssignedTask {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "assigned")
    private boolean assigned;

    @OneToOne
    @JoinColumn(name = "taskItem_id")
    private TaskItem taskItem;

    @OneToOne
    @JoinColumn(name = "assignee_id")
    private AppUser assignee;

    public AssignedTask() {
    }

    public AssignedTask(boolean assigned) {
        this.assigned = assigned;
    }

    public AssignedTask(boolean assigned, TaskItem taskItem, AppUser assignee) {
        this.assigned = assigned;
        this.taskItem = taskItem;
        this.assignee = assignee;
    }

    public Integer getId() {
        return id;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public TaskItem getTodoItem() {
        return taskItem;
    }

    public void setTodoItem(TaskItem taskItem) {
        this.taskItem = taskItem;
    }

    public AppUser getAssignee() {
        return assignee;
    }

    public void setAssignee(AppUser assignee) {
        this.assignee = assignee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AssignedTask that = (AssignedTask) o;
        return assigned == that.assigned && Objects.equals(id, that.id) && Objects.equals(taskItem, that.taskItem) && Objects.equals(assignee, that.assignee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, assigned, taskItem, assignee);
    }

    @Override
    public String toString() {
        return "AssignedTask{" +
                "id=" + id +
                ", assigned=" + assigned +
                ", taskItem=" + taskItem +
                ", assignee=" + assignee +
                '}';
    }

}
