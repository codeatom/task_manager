package task_manager.application.data;

import task_manager.application.model.ContactInfo;

import java.util.List;
import java.util.Optional;

public interface ContactInfoDAO {

    ContactInfo save(ContactInfo contactInfo);

    Optional<ContactInfo> findById(Integer id);

    List<ContactInfo> findAll();

    ContactInfo update(ContactInfo contactInfo);

    boolean delete(ContactInfo contactInfo);
}

