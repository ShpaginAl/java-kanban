package subtask;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Subtask;
import task.Task;
import taskManager.InMemoryTaskManager;

import static org.junit.jupiter.api.Assertions.*;

class SubtaskTest {
    @Test
    public void equalsIfForTwoSubtaskWithOneId() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("Покупка квартиры", "Необходимо сегодня купить новую квартиру", Status.NEW);
        inMemoryTaskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Покупка квартиры1", "Необходимо сегодня купить новую квартиру11", epic1.getId(), Status.NEW);
        inMemoryTaskManager.createSubtask(subtask1);
        Subtask subtask2 = new Subtask("Покупка квартиры2", "Необходимо сегодня купить вторую новую квартиру22", epic1.getId(), Status.NEW, subtask1.getId());
        assertEquals(subtask2, subtask1, "Задачи должны быть равны друг другу");
    }
}