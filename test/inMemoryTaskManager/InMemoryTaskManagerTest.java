package inMemoryTaskManager;

import taskmanager.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import task.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private static TaskManager taskManager;
    private static Task task1;
    private static Task task2;
    private static Epic epic1;
    private static Epic epic2;
    private static Subtask subtask1;
    private static Subtask subtask2;
    private static ArrayList<Task> tasksList;
    private static ArrayList<Epic> epicslist;
    private static ArrayList<Subtask> subtasksList;

    @BeforeAll
    public static void beforeAll() {
        taskManager = Managers.getDefault();
        task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW);
        task2 = new Task("Утренная пробежка", "В 8:00 необходимо сделать утреннюю пробежку", Status.NEW);
        epic1 = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        epic2 = new Epic("Покупка квартиры", "План по покупке новой квартиры", Status.NEW);
        subtask1 = new Subtask("Выбор салона", "Выбрать салон, в котором буду покупать" +
                " машину", 1, Status.NEW);
        subtask2 = new Subtask("Выбор салона", "Выбрать салон, в котором буду покупать" +
                " машину", 1, Status.NEW);
        tasksList = new ArrayList<>(); 
        epicslist = new ArrayList<>();
        subtasksList = new ArrayList<>();
        
        tasksList.add(task1);
        tasksList.add(task2);
        epicslist.add(epic1);
        epicslist.add(epic2);
        subtasksList.add(subtask1);
        subtasksList.add(subtask2);
        
    }
    @Test
    public void addNewTaskInTaskManager() {
        taskManager.createTask(task1);
        assertEquals(task1, taskManager.getTask(task1.getId()), "Должна возвращаться добавленная задача");
    }

    @Test
    public void addNewEpicInTaskManager() {
        taskManager.createEpic(epic1);
        assertEquals(epic1, taskManager.getEpic(epic1.getId()), "Должен возвращаться добавленный эпик");
    }

    @Test
    public void addNewSubtaskInTaskManager() {
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        assertEquals(subtask1, taskManager.getSubtask(subtask1.getId()), "Должна возвращаться добавленная подзадача");
    }

    @Test
    public void equalsOfCreatedTaskToTheSameTaskWhichAddedInManager() {
        taskManager.createTask(task1);
        assertEquals(task1, taskManager.getTask(task1.getId()), "задача должна быть неизменной (по всем полям) при добавлении задачи в менеджер");
    }
    
    @Test
    public void returnListOfTasks() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskManager.printTasks().values()) {
            tasks.add(task);
        }
        assertArrayEquals(tasksList, tasks);
    }

    @Test
    public void returnListOfEpics() {
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        ArrayList<Epic> epics = new ArrayList<>();
        for (Epic epic : taskManager.printEpics().values()) {
            epics.add(epic);
        }
        assertArrayEquals(epicslist, epics);
    }

    @Test
    public void returnListOfSubtasks() {
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Subtask subtask : taskManager.printSubtasks().values()) {
            subtasks.add(subtask);
        }
        assertArrayEquals(subtasksList, subtasks);
    }

    @Test
    public void deleteAllTasks() {
        taskManager.createTask(task1);
        taskManager.createTask(task2);
        taskManager.deleteAllTasks();
        assertTrue(taskManager.printTasks().size() == 0, "ВСЕ задачи должны удаляться");
    }

    @Test
    public void deleteAllEpics() {
        taskManager.createEpic(epic1);
        taskManager.createEpic(epic2);
        taskManager.deleteAllEpics();
        assertTrue(taskManager.printEpics().size() == 0, "ВСЕ эпики должны удаляться");
    }

    @Test
    public void deleteAllSubtasks() {
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.createSubtask(subtask2);
        taskManager.deleteAllSubtasks();
        assertTrue(taskManager.printSubtasks().size() == 0, "ВСЕ подзадачи должны удаляться");
    }

    @Test
    public void CheckUpdateOfTask() {
        taskManager.createTask(task1);
        task1 = new Task("Дневная пробежка", "В 10:00 необходимо сделать утреннюю пробежку", Status.NEW, task1.getId());
        taskManager.updateTask(task1);
        assertEquals(task1, taskManager.getTask(task1.getId()), "Задача должна обновляться в менеджере");
    }

    @Test
    public void CheckUpdateOfEpic() {
        taskManager.createEpic(epic1);
        epic1 = new Epic("Покупка телефона", "План по покупке нового телефона", epic1.getStatus(), epic1.getId());
        taskManager.updateEpic(epic1);
        assertEquals(epic1, taskManager.getEpic(epic1.getId()), "Эпик должен обновляться в менеджере");
    }

    @Test
    public void CheckUpdateOfSubtask() {
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        subtask1 = new Subtask("Выбор нескольких салонов", "Выбрать салон, в котором буду покупать" +
                " авто", 1, Status.NEW, subtask1.getId());
        taskManager.updateSubtask(subtask1);
        assertEquals(subtask1, taskManager.getSubtask(subtask1.getId()), "Подзадача должна обновляться в менеджере");
    }

    @Test
    public void statusOfEpicIsChangedWhenChangeStatusOfRelatedSubtask() {
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        subtask1 = new Subtask("Выбор нескольких салонов", "Выбрать салон, в котором буду покупать" +
                " авто", 1, Status.IN_PROGRESS, subtask1.getId());
        assertNotEquals(subtask1.getStatus(), Status.NEW, "Статус эпика должен обновляться при обновлении статуса связанных подзадач");
    }

    @Test
    public void deleteTaskFromManagerById() {
        taskManager.createTask(task1);
        taskManager.deleteTask(task1.getId());
        assertNull(taskManager.getTask(task1.getId()), "Задача должна удаляться по айди");
    }

    @Test
    public void deleteSubtaskFromManagerById() {
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask1);
        taskManager.deleteSubtask(subtask1.getId());
        assertNull(taskManager.getSubtask(subtask1.getId()), "Подзадача должна удаляться по айди");
    }

    @Test
    public void deleteEpicFromManagerById() {
        taskManager.createEpic(epic1);
        taskManager.deleteEpic(epic1.getId());
        assertNull(taskManager.getEpic(epic1.getId()), "Эпик должен удаляться по айди");
    }

   @Test
   public void deleteSubtaskFromRelatedEpicWhenDeleteSubtask() {
       taskManager.createEpic(epic1);
       taskManager.createSubtask(subtask1);
       taskManager.createSubtask(subtask2);
       taskManager.deleteAllSubtasks();
       assertEquals(0, epic1.getSubtasks().size(), "Подзадача должна удаляться из списка связанных задач эпика при ее удалении");
    }

    @Test
    public void deleteRelatedSubtaskWhenDeleteEpic() {
        taskManager.createEpic(epic1);
        taskManager.createSubtask(subtask2);
        taskManager.createSubtask(subtask1);
        taskManager.deleteEpic(epic1.getId());
        assertEquals(0, taskManager.printSubtasks().size(), "Подзадачи должны удаляться при удалении связанного эпика");
    }

    private <T extends Task> void assertArrayEquals(ArrayList<T> tasksList, ArrayList<T> tasks) {
    }


}