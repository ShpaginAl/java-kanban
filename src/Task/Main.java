package Task;

import TaskManager.TaskManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");

        Task task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW);
        TaskManager.createTask(task1);
        Task task2 = new Task("Подарить цветы", "После работы купить цветы любимой", Status.NEW);
        TaskManager.createTask(task2);
        Epic epic1 = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        TaskManager.createEpic(epic1);
        Epic epic2 = new Epic("Покупка дома", "План по покупке дома", Status.NEW);
        TaskManager.createEpic(epic2);
        Subtask subtask = new Subtask("Выбор марки автомобиля", "Необходимо выбрать марку автомобиля," +
                "который хочется приобрести", 3, Status.NEW);
        TaskManager.createSubtask(subtask);
        Subtask subtask1 = new Subtask("Выбор салона", "Выбрать салон, в котором буду покупать" +
                " машину", 3, Status.NEW);
        TaskManager.createSubtask(subtask1);
        Subtask subtask2 = new Subtask("Выбор дома", "Выбрать район," +
                "где хочется проживать", 4, Status.NEW);
        TaskManager.createSubtask(subtask2);
        Subtask subtask3 = new Subtask("Выбор доп. опций", "Необходимо выбрать доп. опции", 3,
                Status.NEW);
        TaskManager.createSubtask(subtask3);
        Subtask subtask4 = new Subtask("Выбор ремонта", "Необходимо выбрать подрядчиков",
                4, Status.NEW);
        TaskManager.createSubtask(subtask4);

        //Проверка получения всех таблиц
        System.out.println("Проверка получения всех таблиц");
        System.out.println("Таблица задач");
        System.out.println(TaskManager.tasks);
        System.out.println("------------------------");
        System.out.println("Таблица эпиков");
        System.out.println(TaskManager.epics);
        System.out.println("------------------------");
        System.out.println("Таблица подзадач");
        System.out.println(TaskManager.subtasks);
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка получения всех типов задач
        System.out.println("Проверка получения всех типов задач");
        System.out.println("Список всех задач:");
        TaskManager.printTasks();
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        TaskManager.printEpics();
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        TaskManager.printSubtasks();
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка получения задач по айди
        System.out.println("Проверка получения задачи по айди");
        System.out.println("Получение информации по задаче " + task2.getId() + ":");
        TaskManager.getTask(task2.getId());
        System.out.println("------------------------");
        System.out.println("Получение информации по эпику " + epic2.getId() + ":");
        TaskManager.getEpic(epic2.getId());
        System.out.println("------------------------");
        System.out.println("Получение информации по подзадаче " + subtask.getId() + ":");
        TaskManager.getSubtask(subtask.getId());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка обновления данных в задаче
        System.out.println("Обновление задачи");
        task2 = new Task("Подарить цветы", "После обеда купить цветы любимой", Status.NEW, task2.getId());
        System.out.println(task2);
        TaskManager.updateTask(task2);
        TaskManager.printTasks();
        System.out.println("------------------------");
        System.out.println("Обновление эпика");
        subtask1 = new Subtask("Выбор салона", "Выбрать салон, в котором буду покупать" +
                " машину и менеджера", 3, Status.NEW, subtask1.getId());
        TaskManager.updateSubtask(subtask1);
        TaskManager.printSubtasks();
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка обновления статуса задачи
        System.out.println("Проверка обновления статуса задачи");
        task2 = new Task("Подарить цветы", "После обеда купить цветы любимой", Status.IN_PROGRESS, task2.getId());
        TaskManager.updateTask(task2);
        System.out.println("Список всех задач:");
        TaskManager.printTasks();
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        TaskManager.printEpics();
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        TaskManager.printSubtasks();
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка обновления статуса подзадачи
        System.out.println("Проверка обновления статуса подзадачи");
        subtask1 = new Subtask("Выбор салона", "Выбрать салон, в котором буду покупать" +
                " машину и менеджера", 3, Status.DONE, subtask1.getId());
        subtask = new Subtask("Выбор марки автомобиля", "Необходимо выбрать марку автомобиля," +
                "который хочется приобрести", 3, Status.DONE, subtask.getId());
        subtask3 = new Subtask("Выбор салона", "Выбрать салон, в котором буду покупать" +
                " машину и менеджера", 3, Status.NEW, subtask3.getId());
        TaskManager.updateSubtask(subtask1);
        TaskManager.updateSubtask(subtask);
        TaskManager.updateSubtask(subtask3);
        System.out.println("Список всех задач:");
        TaskManager.printTasks();
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        TaskManager.printEpics();
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        TaskManager.printSubtasks();
        System.out.println("------------------------");
        System.out.println("------------------------");

        //проверка удаления задачи
        System.out.println("проверка удаления задачи");
        TaskManager.deleteTask(1);
        System.out.println("Список всех задач:");
        TaskManager.printTasks();
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        TaskManager.printEpics();
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        TaskManager.printSubtasks();
        System.out.println("------------------------");

        //проверка удаления подзадачи
        System.out.println("проверка удаления подзадачи");
        TaskManager.deleteSubtask(6);
        System.out.println("Список всех задач:");
        TaskManager.printTasks();
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        TaskManager.printEpics();
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        TaskManager.printSubtasks();

        //проверка удаления эпика
        System.out.println("проверка удаления эпика'");
        System.out.println("Удалаяем эпик");
        TaskManager.deleteEpic(3);
        System.out.println("Список всех задач:");
        TaskManager.printTasks();
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        TaskManager.printEpics();
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        TaskManager.printSubtasks();
    }
}
