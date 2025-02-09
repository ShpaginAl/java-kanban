package inMemoryHistoryManager;

import org.junit.jupiter.api.Test;
import task.Epic;
import task.Status;
import task.Task;
import taskmanager.Managers;
import taskmanager.TaskManager;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    @Test
    public void taskIsAddedInHistoryManagerWhenItIsWatched() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        Task task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW);
        Task task2 = new Task("Утренная пробежка", "В 8:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        inMemoryTaskManager.getTask(task1.getId());
        inMemoryTaskManager.getTask(task2.getId());
        assertEquals(task2, inMemoryTaskManager.getHistory().get(1));
    }

    @Test
    public void theSameTaskIsDeletedFromHistoryManager() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        ArrayList<Task> listOfAddedTask = new ArrayList<>();
        Task task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.getTask(task1.getId());
        listOfAddedTask.add(task1);
        inMemoryTaskManager.getTask(task1.getId());
        assertArrayEquals(listOfAddedTask, inMemoryTaskManager.getHistory());
    }

    @Test
    public void deleteFromHistoryWhenTaskIsDeletedFromTaskManager() {
        TaskManager inMemoryTaskManager = Managers.getDefault();
        ArrayList<Task> listOfAddedTask = new ArrayList<>();
        Task task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW);
        Task task2 = new Task("Утренная пробежка", "В 8:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task1);
        inMemoryTaskManager.createTask(task2);
        listOfAddedTask.add(task2);
        inMemoryTaskManager.getTask(task2.getId());
        inMemoryTaskManager.getTask(task1.getId());
        inMemoryTaskManager.deleteTask(task1.getId());
        assertArrayEquals(listOfAddedTask, inMemoryTaskManager.getHistory());
    }

    private void assertArrayEquals(ArrayList<Task> listOfAddedTask, ArrayList<Task> history) {
    }
}