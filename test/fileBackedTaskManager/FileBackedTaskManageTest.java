package fileBackedTaskManager;

import org.junit.jupiter.api.Test;
import taskmanager.FileBackedTaskManager;
import task.*;
import taskmanager.*;

import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

public class FileBackedTaskManageTest {

    @Test
    public void saveTasksInFile() {
        File file;
        TaskManager fileTaskManager = Managers.getDefault();
        Task task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW);
        fileTaskManager.createTask(task1);
        Epic epic1 = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        fileTaskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Выбор марки автомобиля", "Необходимо выбрать марку автомобиля", epic1.getId(), Status.NEW);
        fileTaskManager.createSubtask(subtask1);

        file = new File("C:\\Users\\Aleksandr\\IdeaProjects\\java-kanban\\task.csv");
        FileBackedTaskManager taskManagerFromFile = FileBackedTaskManager.loadFromFile(file);
        assertEquals(fileTaskManager.getTask(task1.getId()), taskManagerFromFile.getTask(task1.getId()));
        assertEquals(fileTaskManager.getEpic(epic1.getId()), taskManagerFromFile.getEpic(epic1.getId()));
        assertEquals(fileTaskManager.getSubtask(subtask1.getId()), taskManagerFromFile.getSubtask(subtask1.getId()));
    }

    @Test
    public void saveHistoryTaskManageInFile() {
        File file;
        TaskManager fileTaskManager = Managers.getDefault();
        Task task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW);
        fileTaskManager.createTask(task1);
        fileTaskManager.getTask(task1.getId());

        file = new File("C:\\Users\\Aleksandr\\IdeaProjects\\java-kanban\\task.csv");
        FileBackedTaskManager taskManagerFromFile = FileBackedTaskManager.loadFromFile(file);
        assertEquals(fileTaskManager.getHistory(), taskManagerFromFile.getHistory());

    }
}
