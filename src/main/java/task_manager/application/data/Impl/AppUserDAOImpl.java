package task_manager.application.data.Impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.AppUserDAO;
import task_manager.application.model.AppUser;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AppUserDAOImpl implements AppUserDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public AppUser save(AppUser appUser){
        if(appUser == null ){
            throw new IllegalArgumentException ("appUser is null");
        }
        entityManager.persist(appUser);

        return appUser;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AppUser> findById(Integer id) {
        if(id == null){
            throw new IllegalArgumentException ("id is null");
        }

        Optional<AppUser> appUser;

        appUser = Optional.of(entityManager.find(AppUser.class, id));

        return appUser;
    }

    @Override
    @Transactional(readOnly = true)
    public List<AppUser> findAll(){
        String jpql = "SELECT a FROM AppUser a";

        TypedQuery<AppUser> typedQuery = entityManager.createQuery(jpql, AppUser.class);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public AppUser update(AppUser appUser) {
        if(appUser == null ){
            throw new IllegalArgumentException ("appUser is null");
        }
        entityManager.merge(appUser);

        return appUser;
    }

    @Override
    @Transactional
    public boolean delete(AppUser appUser) {
        if(appUser == null ){
            throw new IllegalArgumentException ("appUser is null");
        }

        if(findById(appUser.getUserId()).isPresent()){
            AppUser a = findById(appUser.getUserId()).get();
            entityManager.remove(a);
            return true;
        }

        return false;
    }

}
