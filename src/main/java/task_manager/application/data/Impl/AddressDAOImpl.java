package task_manager.application.data.Impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.AddressDAO;
import task_manager.application.model.Address;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AddressDAOImpl implements AddressDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Address save(Address address) {
        if(address == null ){
            throw new IllegalArgumentException ("address is null");
        }
        entityManager.persist(address);

        return address;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Address> findById(Integer id) {
        if(id == null){
            throw new IllegalArgumentException ("id is null");
        }

        Optional<Address> address;

        address = Optional.of(entityManager.find(Address.class, id));

        return address;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> findAll() {
        String jpql = "SELECT a FROM Address a";

        TypedQuery<Address> typedQuery = entityManager.createQuery(jpql, Address.class);

        return typedQuery.getResultList();
    }

    @Override
    @Transactional
    public Address update(Address address) {
        if(address == null ){
            throw new IllegalArgumentException ("address is null");
        }
        entityManager.merge(address);

        return address;
    }

    @Override
    @Transactional
    public boolean delete(Address address) {
        if(address == null ){
            throw new IllegalArgumentException ("address is null");
        }

        if(findById(address.getId()).isPresent()){
            entityManager.remove(entityManager.contains(address) ? address : entityManager.merge(address));
            return true;
        }

        return false;
    }

}

