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
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AssignedTaskDAOImplTest {

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
    @Autowired
    TaskItemDAO taskItemDAO;
    @Autowired
    AssignedTaskDAO assignedTaskDAO;

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
    TaskItem taskItem_1;
    TaskItem taskItem_2;
    TaskItem taskItem_3;
    AssignedTask assignedTask_1;
    AssignedTask assignedTask_2;
    AssignedTask assignedTask_3;

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

        taskItem_1 = new TaskItem("write main code", "writing the application code", LocalDateTime.parse("2022-09-04T10:11:30"));
        taskItem_2 = new TaskItem("Write mainTest code", "writing the application test code", LocalDateTime.parse("2022-10-04T10:11:30"));
        taskItem_3 = new TaskItem("Write .NET code", "writing an application code using .Net", LocalDateTime.parse("2022-12-04T10:11:30"));
        taskItemDAO.save(taskItem_1);
        taskItemDAO.save(taskItem_2);
        taskItemDAO.save(taskItem_3);

        assignedTask_1 = new AssignedTask(false);
        assignedTask_2 = new AssignedTask(false);
        assignedTask_3 = new AssignedTask(false);
        assignedTaskDAO.save(assignedTask_1);
        assignedTaskDAO.save(assignedTask_2);
        assignedTaskDAO.save(assignedTask_3);

        assignedTask_1.setTodoItem(taskItem_1);
        assignedTask_1.setAssignee(Chris);
        assignedTask_1.setAssigned(true);
        assignedTaskDAO.update(assignedTask_1);

        assignedTask_2.setTodoItem(taskItem_2);
        assignedTask_2.setAssignee(Lucky);
        assignedTask_2.setAssigned(true);
        assignedTaskDAO.update(assignedTask_2);

        assignedTask_3.setTodoItem(taskItem_3);
        assignedTask_3.setAssignee(Lucky);
        assignedTaskDAO.update(assignedTask_3);
    }

    @Test
    void save() {

        //Assert
        assertNotNull(assignedTask_1);
        assertNotNull(assignedTask_2);
        assertNotNull(assignedTask_3);
        assertEquals(1, assignedTask_1.getId());
        assertEquals(2, assignedTask_2.getId());
        assertEquals(3, assignedTask_3.getId());
    }

    @Test
    void findById() {

        //Act
        AssignedTask actual_1 = assignedTaskDAO.findById(1).isPresent() ? assignedTaskDAO.findById(1).get() : null;
        AssignedTask actual_2 = assignedTaskDAO.findById(2).isPresent() ? assignedTaskDAO.findById(2).get() : null;

        //Assert
        assertNotNull(actual_1);
        assertNotNull(actual_2);
        assertEquals(1, actual_1.getId());
        assertEquals(2, actual_2.getId());
    }

    @Test
    void findAll() {

        //Act
        List<AssignedTask> todoItemTaskList = assignedTaskDAO.findAll();

        //Assert
        assertTrue(todoItemTaskList.size() > 0);
        assertEquals(assignedTask_1, todoItemTaskList.get(0));
        assertEquals(assignedTask_2, todoItemTaskList.get(1));
        assertEquals(assignedTask_3, todoItemTaskList.get(2));
    }

    @Test
    @Transactional
    void update() {

        //Arrange
        assignedTask_1.setAssignee(Lucky);
        assignedTask_1.setAssigned(false);

        //Act
        assignedTaskDAO.update(assignedTask_1);
        AssignedTask expected =  assignedTaskDAO.findById(assignedTask_1.getId()).isPresent() ? assignedTaskDAO.findById(assignedTask_1.getId()).get() : null;

        //Assert
        assertEquals(1, assignedTask_1.getId());
        assertNotNull(expected);
        assertEquals(expected.getAssignee(), assignedTask_1.getAssignee());
        assertEquals(expected.isAssigned(), assignedTask_1.isAssigned());
    }

    @Test
    void delete() {

        //Act
        int listSizeBeforeDelete = assignedTaskDAO.findAll().size();

        boolean isDeleted = assignedTaskDAO.delete(assignedTask_1);
        boolean isDeleted2 = assignedTaskDAO.delete(assignedTask_2);

        int listSizeAfterDelete = assignedTaskDAO.findAll().size();

        //Assert
        assertTrue(isDeleted);
        assertTrue(isDeleted2);
        assertEquals(3, listSizeBeforeDelete );
        assertEquals(1, listSizeAfterDelete );
    }
}