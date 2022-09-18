package task_manager.application.data.Impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.SkillDAO;
import task_manager.application.model.Skill;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Repository
public class SkillDAOImpl implements SkillDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Skill save(Skill skill) {
        if(skill == null ){
            throw new IllegalArgumentException ("skill is null");
        }
        entityManager.persist(skill);

        return skill;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Skill> findById(Integer id) {
        if(id == null){
            throw new IllegalArgumentException ("id is null");
        }

        Optional<Skill> skill;

        skill = Optional.of(entityManager.find(Skill.class, id));

        return skill;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Skill> findAll() {
        String jpql = "SELECT s FROM Skill s";

        TypedQuery<Skill> typedQuery = entityManager.createQuery(jpql, Skill.class);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public Skill update(Skill skill) {
        if(skill == null ){
            throw new IllegalArgumentException ("skill is null");
        }
        entityManager.merge(skill);

        return skill;
    }

    @Override
    @Transactional
    public boolean delete(Skill skill) {
        if(skill == null ){
            throw new IllegalArgumentException ("appUser is null");
        }

        if(findById(skill.getId()).isPresent()){
            entityManager.remove(entityManager.contains(skill) ? skill : entityManager.merge(skill));
            return true;
        }

        return false;
    }
}

