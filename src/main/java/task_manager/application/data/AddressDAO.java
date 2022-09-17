package task_manager.application.data;

import task_manager.application.model.Address;

import java.util.List;
import java.util.Optional;

public interface AddressDAO {

    Address save(Address address);

    Optional<Address> findById(Integer id);

    List<Address> findAll();

    Address update(Address address);

    boolean delete(Address address);

}
