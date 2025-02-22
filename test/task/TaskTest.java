package task;

import taskmanager.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    public void equalsIfForTwoTaskWithOneId() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Task task1 = new Task("Покупка квартиры", "Необходимо сегодня купить новую квартиру", Status.NEW);
        System.out.println(task1);
        inMemoryTaskManager.createTask(task1);
        Task task2 = new Task("Покупка квартиры", "Необходимо сегодня купить вторую новую квартиру", Status.NEW, task1.getId());
        assertEquals(task2, task1, "Задачи должны быть равны друг другу");
    }
}