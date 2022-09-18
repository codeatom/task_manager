package task_manager.application.data.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.*;
import task_manager.application.model.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AppUserDAOImplTest {

    @Autowired
    AddressDAO addressDAO;
    @Autowired
    AppUserDAO appUserDAO;
    @Autowired
    ContactInfoDAO contactInfoDAO;
    @Autowired
    LanguageDAO languageDAO;
    @Autowired
    SkillDAO skillDAO;

    Address address_1;
    Address address_2;
    Address address_3;
    ContactInfo contactInfo_1;
    ContactInfo contactInfo_2;
    ContactInfo contactInfo_3;
    Language language_1;
    Language language_2;
    Language language_3;
    Skill skill_1;
    Skill skill_2;
    Skill skill_3;
    AppUser Chris;
    AppUser Lucky;

    @BeforeEach
    void beforeEach(){
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

        language_1 = new Language("English", "English Language", "Germanic");
        language_2 = new Language("French", "French Language", "Romance");
        language_3 = new Language("German", "French Language", "Germanic");
        languageDAO.save(language_1);
        languageDAO.save(language_2);
        languageDAO.save(language_3);

        skill_1 = new Skill("Java", "Java development");
        skill_2 = new Skill("C#", ".NET development");
        skill_3 = new Skill("Javascript", "Scripting");
        skillDAO.save(skill_1);
        skillDAO.save(skill_2);
        skillDAO.save(skill_3);

        Chris = new AppUser("username_1", "Chris", "Chris", LocalDate.parse("2022-09-06"), "pw_1");
        Lucky = new AppUser("username_2", "Lucky", "Lucky", LocalDate.parse("2022-09-06"), "pw_22");
        appUserDAO.save(Chris);
        appUserDAO.save(Lucky);

        Chris.setContactInfo(contactInfo_1);
        Chris.addSkill(skill_1);
        Chris.addSkill(skill_2);
        Chris.addLanguage(language_1);
        Chris.addLanguage(language_2);
        Chris.addLanguage(language_3);
        appUserDAO.update(Chris);

        Lucky.setContactInfo(contactInfo_2);
        Lucky.addSkill(skill_3);
        Lucky.addLanguage(language_1);
        Lucky.addLanguage(language_3);
        appUserDAO.update(Lucky);
    }

    @Test
    void save() {

        //Assert
        assertNotNull(Chris);
        assertNotNull(Lucky);
        assertEquals(1, Chris.getUserId());
        assertEquals(2, Lucky.getUserId());
    }

    @Test
    void findById() {

        //Act
        AppUser actual_1 = appUserDAO.findById(1).isPresent() ? appUserDAO.findById(1).get() : null;
        AppUser actual_2 = appUserDAO.findById(2).isPresent() ? appUserDAO.findById(2).get() : null;

        //Assert
        assertNotNull(actual_1);
        assertNotNull(actual_2);
        assertEquals(1, actual_1.getUserId());
        assertEquals(2, actual_2.getUserId());
    }

    @Test
    void findAll() {

        //Act
        List<AppUser> appUserList = appUserDAO.findAll();

        //Assert
        assertTrue(appUserList.size() > 0);
        assertEquals(Chris, appUserList.get(0));
        assertEquals(Lucky, appUserList.get(1));
    }

    @Test
    @Transactional
    void update() {

        //Arrange
        Chris.setFirstName("Christopher");
        Chris.setPassword("new_pass_word");

        //Act
        appUserDAO.update(Chris);
        AppUser expected =  appUserDAO.findById(Chris.getUserId()).isPresent() ? appUserDAO.findById(Chris.getUserId()).get() : null;

        //Assert
        assertEquals(1, Chris.getUserId());
        assertNotNull(expected);
        assertEquals(expected.getFirstName(), Chris.getFirstName());
        assertEquals(expected.getPassword(), Chris.getPassword());
    }

    @Test
    void delete() {

        //Act
        int listSizeBeforeDelete = appUserDAO.findAll().size();

        boolean isDeleted = appUserDAO.delete(Chris);
        boolean isDeleted2 = appUserDAO.delete(Lucky);

        int listSizeAfterDelete = appUserDAO.findAll().size();

        //Assert
        assertTrue(isDeleted);
        assertTrue(isDeleted2);
        assertEquals(2, listSizeBeforeDelete );
        assertEquals(0, listSizeAfterDelete );
    }
}