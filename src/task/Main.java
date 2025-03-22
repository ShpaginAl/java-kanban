package task;

import taskmanager.*;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;


public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager fileTaskManager = Managers.getDefault();

        Task task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW, Duration.ofMinutes(90), LocalDateTime.of(2024, 6, 30, 20, 30));
        fileTaskManager.createTask(task1);
        Epic epic1 = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        fileTaskManager.createEpic(epic1);
        Subtask subtask1 = new Subtask("Выбор марки автомобиля", "Необходимо выбрать марку автомобиля", epic1.getId(), Status.NEW, Duration.ofMinutes(100), LocalDateTime.of(2022, 3, 30, 20, 30));
        fileTaskManager.createSubtask(subtask1);
        Subtask subtask2 = new Subtask("Выбор марки мотоцикла", "Необходимо выбрать марку мотоцикла", epic1.getId(), Status.NEW, Duration.ofMinutes(100), LocalDateTime.of(2024, 7, 30, 20, 50));
        fileTaskManager.createSubtask(subtask2);
        System.out.println(subtask1.getEndTime());
        System.out.println(subtask2.getEndTime());
        System.out.println(subtask2.duration);
        System.out.println(epic1.getEndTime());
        System.out.println(epic1.duration);
        System.out.println(epic1.getStartTime());


        System.out.println("Выводим все задачи из созданного менеджера");
        System.out.println(fileTaskManager.printEpics());
        System.out.println(fileTaskManager.printTasks());
        System.out.println(fileTaskManager.printSubtasks());

        System.out.println("Запрашиваем задачу:");
        System.out.println(fileTaskManager.getTask(task1.getId()));
        System.out.println("Запрашиваем подзадачу:");
        System.out.println(fileTaskManager.getSubtask(subtask1.getId()));
        System.out.println("Запрашиваем эпик:");
        System.out.println(fileTaskManager.getEpic(epic1.getId()));
        System.out.println("Выводим историю запросов из созданного менеджера");
        System.out.println(fileTaskManager.getHistory());
        System.out.println("==========================");


        File file2 = new File("C:\\Users\\Aleksandr1\\IdeaProjects\\java_kannan_new\\task.csv");
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
        System.out.println(taskManagerFromFile.printEpics());

        System.out.println(taskManagerFromFile.getPrioritizedTasks());
        System.out.println(fileTaskManager.getPrioritizedTasks());


        Task task4 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW, Duration.ofMinutes(100), LocalDateTime.of(2015, 4, 30, 20, 30));
        Task task5 = new Task("Утренная пробежка", "В 8:00 необходимо сделать утреннюю пробежку", Status.NEW);
        Epic epic4 = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        Epic epic5 = new Epic("Покупка квартиры", "План по покупке новой квартиры", Status.NEW);
        Subtask subtask4 = new Subtask("Выбор марки автомобиля", "Необходимо выбрать марку автомобиля", epic1.getId(), Status.NEW, Duration.ofMinutes(100), LocalDateTime.of(2021, 4, 30, 20, 30));
        Subtask subtask5 = new Subtask("Выбор марки автомобиля", "Необходимо выбрать марку мотоцикла", epic1.getId(), Status.NEW, Duration.ofMinutes(100), LocalDateTime.of(2020, 4, 30, 20, 30));
        fileTaskManager.createTask(task4);
        fileTaskManager.createSubtask(subtask4);
        fileTaskManager.createSubtask(subtask5);
    }
}