package inMemoryHistoryManager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Task;
import taskManager.InMemoryTaskManager;
import taskManager.Managers;
import taskManager.TaskManager;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    @Test
    public void taskIsAddedInHistoryManagerWhenItIsWatched() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.getTask(task1.getId());
        inMemoryTaskManager.getHistory();
        assertEquals(task1,inMemoryTaskManager.getHistory().getFirst());
    }
    @Test
    public void taskThatWasViewed11thIsSavedInManagerHistoryAs10th() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task1);
        Task task2 = new Task("Утренная пробежка", "В 8:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task2);
        Epic epic3 = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        inMemoryTaskManager.createEpic(epic3);
        Task task4 = new Task("Утренная пробежка", "В 9:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task4);
        Task task5 = new Task("Утренная пробежка", "В 10:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task5);
        Task task6 = new Task("Утренная пробежка", "В 11:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task6);
        Task task7 = new Task("Утренная пробежка", "В 12:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task7);
        Task task8 = new Task("Утренная пробежка", "В 13:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task8);
        Task task9 = new Task("Утренная пробежка", "В 14:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task9);
        Task task10 = new Task("Утренная пробежка", "В 15:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task10);
        Task task11 = new Task("Утренная пробежка", "В 16:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task11);
        inMemoryTaskManager.getTask(task1.getId());
        inMemoryTaskManager.getTask(task2.getId());
        inMemoryTaskManager.getEpic(epic3.getId());
        inMemoryTaskManager.getTask(task4.getId());
        inMemoryTaskManager.getTask(task5.getId());
        inMemoryTaskManager.getTask(task6.getId());
        inMemoryTaskManager.getTask(task7.getId());
        inMemoryTaskManager.getTask(task8.getId());
        inMemoryTaskManager.getTask(task9.getId());
        inMemoryTaskManager.getTask(task10.getId());
        inMemoryTaskManager.getTask(task11.getId());
        assertEquals(task2, inMemoryTaskManager.getHistory().getFirst(), "Глубина истории - 10 задач, при добавении 11ой должно происходить удалении первого элемента со сдвигом на один");
    }
}