package epic;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import taskmanager.InMemoryTaskManager;

class EpicTest {
    @Test
    public void equalsIfForTwoEpicsWithOneId() {
        InMemoryTaskManager inMemoryTaskManager = new InMemoryTaskManager();
        Epic epic1 = new Epic("Покупка квартиры1", "Необходимо сегодня купить новую квартиру1", Status.NEW);
        inMemoryTaskManager.createEpic(epic1);
        Epic epic2 = new Epic("Покупка квартиры2", "Необходимо сегодня купить вторую новую квартиру2", Status.NEW, epic1.getId());
        assertEquals(epic1, epic2, "Задачи должны быть равны друг другу");
    }
}