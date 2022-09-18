package task_manager.application.data.Impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.LanguageDAO;
import task_manager.application.model.Language;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Repository
public class LanguageDAOImpl implements LanguageDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Language save(Language language) {
        if(language == null ){
            throw new IllegalArgumentException ("language is null");
        }
        entityManager.persist(language);

        return language;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Language> findById(Integer id) {
        if(id == null){
            throw new IllegalArgumentException ("id is null");
        }

        Optional<Language> language;

        language = Optional.of(entityManager.find(Language.class, id));

        return language;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Language> findAll() {
        String jpql = "SELECT l FROM Language l";

        TypedQuery<Language> typedQuery = entityManager.createQuery(jpql, Language.class);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public Language update(Language language) {
        if(language == null ){
            throw new IllegalArgumentException ("language is null");
        }
        entityManager.merge(language);

        return language;
    }

    @Override
    @Transactional
    public boolean delete(Language language) {
        if(language == null ){
            throw new IllegalArgumentException ("language is null");
        }

        if(findById(language.getId()).isPresent()){
            entityManager.remove(entityManager.contains(language) ? language : entityManager.merge(language));
            return true;
        }

        return false;
    }
}
