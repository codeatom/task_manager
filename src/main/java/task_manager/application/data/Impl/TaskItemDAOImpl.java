package task_manager.application.data.Impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.TaskItemDAO;
import task_manager.application.model.TaskItem;
import task_manager.application.util.Validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
public class TaskItemDAOImpl implements TaskItemDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public TaskItem save(TaskItem taskItem) {
        if(taskItem == null ){
            throw new IllegalArgumentException ("taskItem is null");
        }
        entityManager.persist(taskItem);

        return taskItem;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaskItem> findById(String id) {
        if(Validation.isNullOrEmpty(id)){
            throw new IllegalArgumentException ("id is null");
        }

        Optional<TaskItem> taskItem;

        taskItem = Optional.of(entityManager.find(TaskItem.class, id));

        return taskItem;
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskItem> findAll() {
        String jpql = "SELECT t FROM TaskItem t";

        TypedQuery<TaskItem> typedQuery = entityManager.createQuery(jpql, TaskItem.class);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskItem> findByDeadLineBetween(LocalDateTime start, LocalDateTime end){
        if(start == null || end == null ){
            throw new IllegalArgumentException ("start date or end date is null");
        }

        String jpql = "SELECT t FROM TaskItem t WHERE t.deadline BETWEEN :start AND :end";
        TypedQuery<TaskItem> typedQuery = entityManager.createQuery(jpql, TaskItem.class)
                .setParameter("start", start)
                .setParameter("end", end);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskItem> findByDeadLineBefore(LocalDateTime end){
        if(end == null ){
            throw new IllegalArgumentException ("end date is null");
        }

        String jpql = "SELECT t FROM TaskItem t WHERE t.deadline < :end";
        TypedQuery<TaskItem> typedQuery = entityManager.createQuery(jpql, TaskItem.class)
                .setParameter("end", end);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskItem> findByDeadLineAfter(LocalDateTime start){
        if(start == null){
            throw new IllegalArgumentException ("start date is null");
        }

        String jpql = "SELECT t FROM TaskItem t WHERE t.deadline > :start";
        TypedQuery<TaskItem> typedQuery = entityManager.createQuery(jpql, TaskItem.class)
                .setParameter("start", start);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public TaskItem update(TaskItem taskItem) {
        if(taskItem == null ){
            throw new IllegalArgumentException ("taskItem is null");
        }
        entityManager.merge(taskItem);

        return taskItem;
    }

    @Override
    @Transactional
    public boolean delete(TaskItem taskItem) {
        if(taskItem == null ){
            throw new IllegalArgumentException ("taskItem is null");
        }

        if(findById(taskItem.getTodoId()).isPresent()){
            entityManager.remove(entityManager.contains(taskItem) ? taskItem : entityManager.merge(taskItem));
            return true;
        }

        return false;
    }
}
