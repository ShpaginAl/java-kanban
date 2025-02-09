package task;

import taskmanager.*;


public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");

        TaskManager inMemoryTaskManager = Managers.getDefault();

        Task task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW);
        inMemoryTaskManager.createTask(task1);
        Task task2 = new Task("Подарить цветы", "После работы купить цветы любимой", Status.NEW);
        inMemoryTaskManager.createTask(task2);
        Epic epic1 = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        inMemoryTaskManager.createEpic(epic1);
        Epic epic2 = new Epic("Покупка дома", "План по покупке дома", Status.NEW);
        inMemoryTaskManager.createEpic(epic2);
        Subtask subtask = new Subtask("Выбор марки автомобиля", "Необходимо выбрать марку автомобиля," +
                "который хочется приобрести", 3, Status.NEW);
        inMemoryTaskManager.createSubtask(subtask);
        Subtask subtask1 = new Subtask("Выбор салона", "Выбрать салон, в котором буду покупать" +
                " машину", 3, Status.NEW);
        inMemoryTaskManager.createSubtask(subtask1);
        Subtask subtask2 = new Subtask("Выбор дома", "Выбрать район," +
                "где хочется проживать", 3, Status.NEW);
        inMemoryTaskManager.createSubtask(subtask2);

        inMemoryTaskManager.getTask(task1.getId());
        System.out.println(inMemoryTaskManager.getEpic(epic1.getId()));
        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println(inMemoryTaskManager.getTask(task1.getId()));
        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println(inMemoryTaskManager.getTask(task2.getId()));
        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println(inMemoryTaskManager.getSubtask(subtask.getId()));
        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println(inMemoryTaskManager.getEpic(epic1.getId()));
        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println(inMemoryTaskManager.getSubtask(subtask1.getId()));
        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println(inMemoryTaskManager.getSubtask(subtask2.getId()));
        System.out.println(inMemoryTaskManager.getHistory());

        inMemoryTaskManager.deleteTask(task1.getId());
        System.out.println(inMemoryTaskManager.getHistory());
        inMemoryTaskManager.deleteEpic(epic1.getId());
        System.out.println(inMemoryTaskManager.getHistory());
    }
}