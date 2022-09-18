package task_manager.application.data.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.LanguageDAO;
import task_manager.application.model.Language;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class LanguageDAOImplTest {

    @Autowired
    LanguageDAO languageDAO;

    Language language_1;
    Language language_2;
    Language language_3;

    @BeforeEach
    void beforeEach(){
        language_1 = new Language("English", "English Language", "Germanic");
        language_2 = new Language("French", "French Language", "Romance");
        language_3 = new Language("German", "French Language", "Germanic");
        languageDAO.save(language_1);
        languageDAO.save(language_2);
        languageDAO.save(language_3);
    }

    @Test
    void save() {

        //Assert
        assertNotNull(language_1);
        assertNotNull(language_2);
        assertNotNull(language_3);
        assertEquals(1, language_1.getId());
        assertEquals(2, language_2.getId());
        assertEquals(3, language_3.getId());
    }

    @Test
    void findById() {

        //Act
        Language actual_1 = languageDAO.findById(1).isPresent() ? languageDAO.findById(1).get() : null;
        Language actual_2 = languageDAO.findById(2).isPresent() ? languageDAO.findById(2).get() : null;

        //Assert
        assertNotNull(actual_1);
        assertNotNull(actual_2);
        assertEquals(1, actual_1.getId());
        assertEquals(2, actual_2.getId());
    }

    @Test
    void findAll() {

        //Act
        List<Language> languageList = languageDAO.findAll();

        //Assert
        assertTrue(languageList.size() > 0);
        assertEquals(language_1, languageList.get(0));
        assertEquals(language_2, languageList.get(1));
        assertEquals(language_3, languageList.get(2));
    }

    @Test
    @Transactional
    void update() {

        //Arrange
        language_1.setLanguageName("Spanish");
        language_1.setLanguageName("Romance");

        //Act
        languageDAO.update(language_1);
        Language expected =  languageDAO.findById(language_1.getId()).isPresent() ? languageDAO.findById(language_1.getId()).get() : null;

        //Assert
        assertEquals(1, language_1.getId());
        assertNotNull(expected);
        assertEquals(expected.getLanguageName(), language_1.getLanguageName());
        assertEquals(expected.getLanguageBranch(), language_1.getLanguageBranch());
    }

    @Test
    void delete() {

        //Act
        int listSizeBeforeDelete = languageDAO.findAll().size();

        boolean isDeleted = languageDAO.delete(language_1);

        int listSizeAfterDelete = languageDAO.findAll().size();

        //Assert
        assertTrue(isDeleted);
        assertEquals(3, listSizeBeforeDelete );
        assertEquals(2, listSizeAfterDelete );
    }
}