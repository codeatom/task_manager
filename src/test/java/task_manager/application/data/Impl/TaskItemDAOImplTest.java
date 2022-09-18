package task_manager.application.data.Impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.transaction.annotation.Transactional;
import task_manager.application.data.TaskItemDAO;
import task_manager.application.model.TaskItem;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class TaskItemDAOImplTest {

    @Autowired
    private TaskItemDAO taskItemDAO;

    TaskItem taskItem_1;
    TaskItem taskItem_2;
    TaskItem taskItem_3;

    @BeforeEach
    void beforeEach(){
        taskItem_1 = new TaskItem("write main code", "writing the application code", LocalDateTime.parse("2022-09-04T10:11:30"));
        taskItem_2 = new TaskItem("Write mainTest code", "writing the application test code", LocalDateTime.parse("2022-10-04T10:11:30"));
        taskItem_3 = new TaskItem("Write .NET code", "writing an application code using .Net", LocalDateTime.parse("2022-12-04T10:11:30"));
        taskItemDAO.save(taskItem_1);
        taskItemDAO.save(taskItem_2);
        taskItemDAO.save(taskItem_3);
    }

    @Test
    void save() {

        //Assert
        assertNotNull(taskItem_1);
        assertNotNull(taskItem_2);
        assertNotNull(taskItem_3);
        assertNotNull(taskItem_1.getTodoId());
        assertNotNull(taskItem_2.getTodoId());
        assertNotNull(taskItem_3.getTodoId());
    }

    @Test
    void findById() {

        //Act
        TaskItem actual_1 = taskItemDAO.findById(taskItem_1.getTodoId()).isPresent() ? taskItemDAO.findById(taskItem_1.getTodoId()).get() : null;
        TaskItem actual_2 = taskItemDAO.findById(taskItem_2.getTodoId()).isPresent() ? taskItemDAO.findById(taskItem_2.getTodoId()).get() : null;

        //Assert
        assertNotNull(actual_1);
        assertNotNull(actual_2);
        assertNotNull(actual_1.getTodoId());
        assertNotNull(actual_2.getTodoId());
        assertNotEquals(actual_1.getTodoId(), actual_2.getTodoId());
    }

    @Test
    void findAll() {

        //Act
        List<TaskItem> todoItemList = taskItemDAO.findAll();

        //Assert
        assertTrue(todoItemList.size() > 0);
    }

    @Test
    @Transactional
    void update() {

        //Arrange
        taskItem_1.setTitle("write javascript code");
        taskItem_1.setDescription("write javascript test code");

        //Act
        taskItemDAO.update(taskItem_1);
        TaskItem expected =  taskItemDAO.findById(taskItem_1.getTodoId()).isPresent() ? taskItemDAO.findById(taskItem_1.getTodoId()).get() : null;

        //Assert
        assertNotNull(taskItem_1.getTodoId());
        assertNotNull(expected);
        assertEquals(expected.getTitle(), taskItem_1.getTitle());
        assertEquals(expected.getDescription(), taskItem_1.getDescription());
    }

    @Test
    void findByDeadLineBetween() {

        //Arrange
        LocalDateTime start = LocalDateTime.parse("2022-09-04T10:11:30");
        LocalDateTime end = LocalDateTime.parse("2022-09-07T10:11:30");

        //Act
        List<TaskItem> todoItemList = taskItemDAO.findByDeadLineBetween(start, end);

        //Assert
        assertEquals(1, todoItemList.size());
    }

    @Test
    void findByDeadLineBefore() {

        //Arrange
        LocalDateTime end = LocalDateTime.parse("2022-11-04T10:11:30");

        //Act
        List<TaskItem> todoItemList = taskItemDAO.findByDeadLineBefore(end);

        //Assert
        assertEquals(2, todoItemList.size());
    }

    @Test
    void findByDeadLineAfter() {

        //Arrange
        LocalDateTime end = LocalDateTime.parse("2022-09-05T10:11:30");

        //Act
        List<TaskItem> todoItemList = taskItemDAO.findByDeadLineBefore(end);

        //Assert
        assertEquals(1, todoItemList.size());
    }

    @Test
    void delete() {

        //Act
        int listSizeBeforeDelete = taskItemDAO.findAll().size();

        boolean isDeleted = taskItemDAO.delete(taskItem_1);

        int listSizeAfterDelete = taskItemDAO.findAll().size();

        //Assert
        assertTrue(isDeleted);
        assertEquals(3, listSizeBeforeDelete );
        assertEquals(2, listSizeAfterDelete );
    }
}