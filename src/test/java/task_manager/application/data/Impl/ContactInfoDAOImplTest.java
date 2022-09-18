package task_manager.application.data.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.AddressDAO;
import task_manager.application.data.ContactInfoDAO;
import task_manager.application.model.Address;
import task_manager.application.model.ContactInfo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ContactInfoDAOImplTest {

    @Autowired
    AddressDAO addressDAO;
    @Autowired
    ContactInfoDAO contactInfoDAO;

    Address address_1;
    Address address_2;
    Address address_3;
    ContactInfo contactInfo_1;
    ContactInfo contactInfo_2;
    ContactInfo contactInfo_3;

    @BeforeEach
    void beforeEach() {
        address_1 = new Address("Karlskrona", "myStreet", "123");
        address_2 = new Address("Karlshamn", "another street", "1234");
        address_3 = new Address("Ronneby", "that street", "12345");
        addressDAO.save(address_1);
        addressDAO.save(address_2);
        addressDAO.save(address_3);

        contactInfo_1 = new ContactInfo("eAddr@email.com", "12345678");
        contactInfo_2 = new ContactInfo("otherAddr@email.com", "002345600");
        contactInfo_3 = new ContactInfo("thisAddr@email.com", "002345678");
        contactInfoDAO.save(contactInfo_1);
        contactInfoDAO.save(contactInfo_2);
        contactInfoDAO.save(contactInfo_3);

        contactInfo_1.addAddress(address_1);
        contactInfo_1.addAddress(address_2);
        contactInfo_2.addAddress(address_3);
        contactInfo_3.addAddress(address_3);
        contactInfoDAO.update(contactInfo_1);
        contactInfoDAO.update(contactInfo_2);
        contactInfoDAO.update(contactInfo_3);
    }

    @Test
    void save() {

        //Assert
        assertNotNull(contactInfo_1);
        assertNotNull(contactInfo_2);
        assertNotNull(contactInfo_3);
        assertEquals(1, contactInfo_1.getId());
        assertEquals(2, contactInfo_2.getId());
        assertEquals(3, contactInfo_3.getId());
    }

    @Test
    void findById() {

        //Act
        ContactInfo actual_1 = contactInfoDAO.findById(1).isPresent() ? contactInfoDAO.findById(1).get() : null;
        ContactInfo actual_2 = contactInfoDAO.findById(2).isPresent() ? contactInfoDAO.findById(2).get() : null;

        //Assert
        assertNotNull(actual_1);
        assertNotNull(actual_2);
        assertEquals(1, actual_1.getId());
        assertEquals(2, actual_2.getId());
    }

    @Test
    void findAll() {

        //Act
        List<ContactInfo> contactInfoList = contactInfoDAO.findAll();

        //Assert
        assertTrue(contactInfoList.size() > 0);
        assertEquals(contactInfo_1, contactInfoList.get(0));
        assertEquals(contactInfo_2, contactInfoList.get(1));
    }

    @Test
    @Transactional
    void update() {

        //Arrange
        contactInfo_1.setPhone("1111");
        contactInfo_1.setEmail("aa@aa.com");

        //Act
        contactInfoDAO.update(contactInfo_1);
        ContactInfo expected =  contactInfoDAO.findById(contactInfo_1.getId()).isPresent() ? contactInfoDAO.findById(contactInfo_1.getId()).get() : null;

        //Assert
        assertEquals(1, contactInfo_1.getId());
        assertNotNull(expected);
        assertEquals(expected.getPhone(), contactInfo_1.getPhone());
        assertEquals(expected.getEmail(), contactInfo_1.getEmail());
    }

    @Test
    void delete() {

        //Act
        int listSizeBeforeDelete = contactInfoDAO.findAll().size();

        boolean isDeleted = contactInfoDAO.delete(contactInfo_1);
        boolean isDeleted2 = contactInfoDAO.delete(contactInfo_2);

        int listSizeAfterDelete = contactInfoDAO.findAll().size();

        //Assert
        assertTrue(isDeleted);
        assertTrue(isDeleted2);
        assertEquals(3, listSizeBeforeDelete );
        assertEquals(1, listSizeAfterDelete );
    }
}