package task_manager.application.data.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.AddressDAO;
import task_manager.application.model.Address;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AddressDAOImplTest {

    @Autowired
    AddressDAO addressDAO;

    Address address_1;
    Address address_2;
    Address address_3;

    @BeforeEach
    void beforeEach(){
        address_1 = new Address("Karlskrona", "myStreet", "123");
        address_2 = new Address("Karlshamn", "another street", "1234");
        address_3 = new Address("Ronneby", "that street", "12345");
        addressDAO.save(address_1);
        addressDAO.save(address_2);
        addressDAO.save(address_3);
    }

    @Test
    void save() {

        //Assert
        assertNotNull(address_1);
        assertNotNull(address_2);
        assertNotNull(address_3);
        assertEquals(1, address_1.getId());
        assertEquals(2, address_2.getId());
        assertEquals(3, address_3.getId());
    }

    @Test
    void findById() {

        //Act
        Address actual_1 = addressDAO.findById(1).isPresent() ? addressDAO.findById(1).get() : null;
        Address actual_3 = addressDAO.findById(3).isPresent() ? addressDAO.findById(3).get() : null;

        //Assert
        assertNotNull(actual_1);
        assertNotNull(actual_3);
        assertEquals(1, actual_1.getId());
        assertEquals(3, actual_3.getId());
    }

    @Test
    void findAll() {

        //Act
        List<Address> addressList = addressDAO.findAll();

        //Assert
        assertTrue(addressList.size() > 0);
        assertEquals(address_1, addressList.get(0));
        assertEquals(address_2, addressList.get(1));
    }

    @Test
    @Transactional
    void update() {

        //Arrange
        address_1.setCity("Gothenborg");
        address_2.setStreet("Ronnebygatan");

        //Act
        addressDAO.update(address_1);
        Address expected =  addressDAO.findById(address_1.getId()).isPresent() ? addressDAO.findById(address_1.getId()).get() : null;

        //Assert
        assertEquals(1, address_1.getId());
        assertNotNull(expected);
        assertEquals(expected.getCity(), address_1.getCity());
        assertEquals(expected.getStreet(), address_1.getStreet());
    }

    @Test
    void delete() {

        //Act
        int listSizeBeforeDelete = addressDAO.findAll().size();

        boolean isDeleted = addressDAO.delete(address_1);
        boolean isDeleted2 = addressDAO.delete(address_2);

        int listSizeAfterDelete = addressDAO.findAll().size();

        //Assert
        assertTrue(isDeleted);
        assertTrue(isDeleted2);
        assertEquals(3, listSizeBeforeDelete );
        assertEquals(1, listSizeAfterDelete );
    }
}