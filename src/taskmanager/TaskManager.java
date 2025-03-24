package taskmanager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import task.*;

public interface TaskManager {
    //создание задач
    int createTask(Task task);

    int createEpic(Epic epic);

    int createSubtask(Subtask subtask);

    //Получение всех задач
    HashMap<Integer, Task> printTasks();

    HashMap<Integer, Epic> printEpics();

    HashMap<Integer, Subtask> printSubtasks();

    //Удалить все задачи
    void deleteAllTasks();

    //Удалить все эпики
    void deleteAllEpics();

    //Удалить все подзадачи
    void deleteAllSubtasks();

    //Удаление задач по айди
    void deleteTask(int id);

    //удаление эпика по айди
    void deleteEpic(int id);

    //удаление подзадачи по айди
    void deleteSubtask(int id);

    // Получение задачи по айди
    Task getTask(int id);

    // Получение эпика по айди
    Epic getEpic(int id);

    // Получение подзадачи по айди
    Subtask getSubtask(int id);

    // Обновление задачи
    void updateTask(Task task);

    // обновление эпика
    void updateEpic(Epic epic);

    // обновление подзадачи
    void updateSubtask(Subtask subtask);

    //Получить подзадачи определенного эпика
    void getSubtasksOfEpic(int epicId);

    ArrayList<Task> getHistory();

    TreeSet<Task> getPrioritizedTasks();

}