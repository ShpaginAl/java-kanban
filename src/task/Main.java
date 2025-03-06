package task;

import taskmanager.*;

import javax.xml.transform.Source;
import java.io.File;


public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager fileTaskManager = Managers.getDefault();

        Task task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW);
        fileTaskManager.createTask(task1);
        Epic epic1 = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        fileTaskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Выбор марки автомобиля", "Необходимо выбрать марку автомобиля", epic1.getId(), Status.NEW);
        fileTaskManager.createSubtask(subtask1);

        System.out.println("Выводим все задачи из созданного менеджера");
        System.out.println(fileTaskManager.printEpics());
        System.out.println(fileTaskManager.printTasks());
        System.out.println(fileTaskManager.printSubtasks());

//        System.out.println("Запрашиваем задачу:");
//        System.out.println(fileTaskManager.getTask(task1.getId()));
//        System.out.println("Запрашиваем подзадачу:");
//        System.out.println(fileTaskManager.getSubtask(subtask1.getId()));
        System.out.println("Запрашиваем эпик:");
        System.out.println(fileTaskManager.getEpic(epic1.getId()));
//        System.out.println("Выводим историю запросов из созданного менеджера");
//        System.out.println(fileTaskManager.getHistory());
//        System.out.println("==========================");



        File file2 = new File("C:\\Users\\Aleksandr\\IdeaProjects\\java-kanban\\task.csv");
        FileBackedTaskManager taskManagerFromFile = FileBackedTaskManager.loadFromFile(file2);
        System.out.println("Выводим все задачи из нового менеджера, созданного через файл");
        System.out.println(taskManagerFromFile.printTasks());
        System.out.println(taskManagerFromFile.printEpics());
        System.out.println(taskManagerFromFile.printSubtasks());

        System.out.println("Выводим историю запросов из нового менеджера, созданного через файл");
        System.out.println(taskManagerFromFile.getHistory());

        Epic epic2 = new Epic("Покупка машины1", "План по покупке новой машины", Status.NEW);
        taskManagerFromFile.createEpic(epic2);
        Epic epic3 = new Epic("Покупка машины2", "План по покупке новой машины", Status.NEW);
        taskManagerFromFile.createEpic(epic3);
        taskManagerFromFile.deleteEpic(epic3.getId());
        System.out.println(taskManagerFromFile.printEpics());



    }
}