package task_manager.application.data.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.SkillDAO;
import task_manager.application.model.Skill;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SkillDAOImplTest {

    @Autowired
    SkillDAO skillDAO;

    Skill skill_1;
    Skill skill_2;
    Skill skill_3;

    @BeforeEach
    void beforeEach() {
        skill_1 = new Skill("Java", "Java development");
        skill_2 = new Skill("C#", ".NET development");
        skill_3 = new Skill("Javascript", "Scripting");
        skillDAO.save(skill_1);
        skillDAO.save(skill_2);
        skillDAO.save(skill_3);
    }

    @Test
    void save() {

        //Assert
        assertNotNull(skill_1);
        assertNotNull(skill_1);
        assertNotNull(skill_3);
        assertEquals(1, skill_1.getId());
        assertEquals(2, skill_2.getId());
        assertEquals(3, skill_3.getId());
    }

    @Test
    void findById() {

        //Act
        Skill actual_1 = skillDAO.findById(1).isPresent() ? skillDAO.findById(1).get() : null;
        Skill actual_2 = skillDAO.findById(2).isPresent() ? skillDAO.findById(2).get() : null;

        //Assert
        assertNotNull(actual_1);
        assertNotNull(actual_2);
        assertEquals(1, actual_1.getId());
        assertEquals(2, actual_2.getId());
    }

    @Test
    void findAll() {

        //Act
        List<Skill> skillList = skillDAO.findAll();

        //Assert
        assertTrue(skillList.size() > 0);
        assertEquals(skill_1, skillList.get(0));
        assertEquals(skill_2, skillList.get(1));
    }

    @Test
    @Transactional
    void update() {

        //Arrange
        skill_1.setSkillName("Scala");
        skill_1.setDescription("Scala Programming");

        //Act
        skillDAO.update(skill_1);
        Skill expected =  skillDAO.findById(skill_1.getId()).isPresent() ? skillDAO.findById(skill_1.getId()).get() : null;

        //Assert
        assertEquals(1, skill_1.getId());
        assertNotNull(expected);
        assertEquals(expected.getSkillName(), skill_1.getSkillName());
        assertEquals(expected.getDescription(), skill_1.getDescription());
    }

    @Test
    void delete() {

        //Act
        int listSizeBeforeDelete = skillDAO.findAll().size();

        boolean isDeleted = skillDAO.delete(skill_1);

        int listSizeAfterDelete = skillDAO.findAll().size();

        //Assert
        assertTrue(isDeleted);
        assertEquals(3, listSizeBeforeDelete );
        assertEquals(2, listSizeAfterDelete );
    }
}