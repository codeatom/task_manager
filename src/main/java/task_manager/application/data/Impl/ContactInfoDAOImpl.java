package task_manager.application.data.Impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.ContactInfoDAO;
import task_manager.application.model.ContactInfo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;


@Repository
public class ContactInfoDAOImpl implements ContactInfoDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public ContactInfo save(ContactInfo contactInfo) {
        if(contactInfo == null ){
            throw new IllegalArgumentException ("contactInfo is null");
        }
        entityManager.persist(contactInfo);

        return contactInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ContactInfo> findById(Integer id) {
        if(id == null){
            throw new IllegalArgumentException ("id is null");
        }

        Optional<ContactInfo> contactInfo;

        contactInfo = Optional.of(entityManager.find(ContactInfo.class, id));

        return contactInfo;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ContactInfo> findAll() {
        String jpql = "SELECT c FROM ContactInfo c";

        TypedQuery<ContactInfo> typedQuery = entityManager.createQuery(jpql, ContactInfo.class);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public ContactInfo update(ContactInfo contactInfo) {
        if(contactInfo == null ){
            throw new IllegalArgumentException ("contactInfo is null");
        }
        entityManager.merge(contactInfo);

        return contactInfo;
    }

    @Override
    @Transactional
    public boolean delete(ContactInfo contactInfo) {
        if(contactInfo == null ){
            throw new IllegalArgumentException ("contactInfo is null");
        }

        if(findById(contactInfo.getId()).isPresent()){
            entityManager.remove(entityManager.contains(contactInfo) ? contactInfo : entityManager.merge(contactInfo));
            return true;
        }

        return false;
    }
}
