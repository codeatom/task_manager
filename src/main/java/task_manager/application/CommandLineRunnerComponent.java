package task_manager.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.*;
import task_manager.application.model.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.springframework.boot.CommandLineRunner;

@Profile("!test")
@Component
public class CommandLineRunnerComponent implements CommandLineRunner{

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

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Address address_1 = new Address("myStreet", "1234", "Karlskrona");
        Address address_2 = new Address("another street", "12345", "Karlskrona");
        Address address_3 = new Address("Ronneby", "that street", "12345");
        addressDAO.save(address_1);
        addressDAO.save(address_2);
        addressDAO.save(address_3);

        ContactInfo contactInfo_1 = new ContactInfo("eAddr@email.com", "12345678");
        ContactInfo contactInfo_2 = new ContactInfo("otherAddr@email.com", "002345678");
        ContactInfo contactInfo_3 = new ContactInfo("thisAddr@email.com", "002345678");
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

        Language language_1 = new Language("English", "English Language", "Germanic");
        Language language_2 = new Language("French", "French Language", "Romance");
        Language language_3 = new Language("German", "French Language", "Germanic");
        languageDAO.save(language_1);
        languageDAO.save(language_2);
        languageDAO.save(language_3);

        Skill skill_1 = new Skill("Java", "Java development");
        Skill skill_2 = new Skill("C#", ".NET development");
        Skill skill_3 = new Skill("Javascript", "Scripting");
        skillDAO.save(skill_1);
        skillDAO.save(skill_2);
        skillDAO.save(skill_3);

        AppUser Chris = new AppUser("username_1", "Chris", "Chris", LocalDate.parse("2022-09-06"), "pw_1");
        AppUser Lucky = new AppUser("username_2", "Lucky", "Lucky", LocalDate.parse("2022-09-06"), "pw_22");
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
        Lucky.addSkill(skill_2);
        Lucky.addLanguage(language_2);
        Lucky.addLanguage(language_1);
        appUserDAO.update(Lucky);

        TaskItem taskItem_1 = new TaskItem("write main code", "writing the application code", LocalDateTime.parse("2022-09-04T10:11:30"));
        TaskItem taskItem_2 = new TaskItem("Write mainTest code", "writing the application test code", LocalDateTime.parse("2022-10-04T10:11:30"));
        TaskItem taskItem_3 = new TaskItem("Write .NET code", "writing an application code using .Net", LocalDateTime.parse("2022-12-04T10:11:30"));
        taskItemDAO.save(taskItem_1);
        taskItemDAO.save(taskItem_2);
        taskItemDAO.save(taskItem_3);

        taskItem_1.toggleDone();

        AssignedTask assignedTask_1 = new AssignedTask(false);
        AssignedTask assignedTask_2 = new AssignedTask(false);
        AssignedTask assignedTask_3 = new AssignedTask(false);
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
}
