package task_manager.application.data.Impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.AssignedTaskDAO;
import task_manager.application.model.AssignedTask;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AssignedTaskDAOImpl implements AssignedTaskDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public AssignedTask save(AssignedTask assignedTask) {
        if(assignedTask == null ){
            throw new IllegalArgumentException ("assignedTask is null");
        }
        entityManager.persist(assignedTask);

        return assignedTask;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AssignedTask> findById(Integer id) {
        if(id == null){
            throw new IllegalArgumentException ("id is null");
        }

        Optional<AssignedTask> assignedTask;

        assignedTask = Optional.of(entityManager.find(AssignedTask.class, id));

        return assignedTask;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssignedTask> findAll() {
        String jpql = "SELECT t FROM AssignedTask t";

        TypedQuery<AssignedTask> typedQuery = entityManager.createQuery(jpql, AssignedTask.class);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public AssignedTask update(AssignedTask assignedTask) {
        if(assignedTask == null ){
            throw new IllegalArgumentException ("assignedTask is null");
        }
        entityManager.merge(assignedTask);

        return assignedTask;
    }

    @Override
    @Transactional
    public boolean delete(AssignedTask assignedTask) {
        if(assignedTask == null ){
            throw new IllegalArgumentException ("assignedTask is null");
        }

        if(findById(assignedTask.getId()).isPresent()){
            entityManager.remove(entityManager.contains(assignedTask) ? assignedTask : entityManager.merge(assignedTask));
            return true;
        }

        return false;
    }
}
