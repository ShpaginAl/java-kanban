package Task;

import TaskManager.TaskManager;

public class Main {
    public static void main(String[] args) {
        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();

        Task task1 = new Task("Утренная пробежка", "В 7:00 необходимо сделать утреннюю пробежку", Status.NEW);
        taskManager.createTask(task1);
        Task task2 = new Task("Подарить цветы", "После работы купить цветы любимой", Status.NEW);
        taskManager.createTask(task2);
        Epic epic1 = new Epic("Покупка машины", "План по покупке новой машины", Status.NEW);
        taskManager.createEpic(epic1);
        Epic epic2 = new Epic("Покупка дома", "План по покупке дома", Status.NEW);
        taskManager.createEpic(epic2);
        Subtask subtask = new Subtask("Выбор марки автомобиля", "Необходимо выбрать марку автомобиля," +
                "который хочется приобрести", 3, Status.NEW);
        taskManager.createSubtask(subtask);
        Subtask subtask1 = new Subtask("Выбор салона", "Выбрать салон, в котором буду покупать" +
                " машину", 3, Status.NEW);
        taskManager.createSubtask(subtask1);
        Subtask subtask2 = new Subtask("Выбор дома", "Выбрать район," +
                "где хочется проживать", 4, Status.NEW);
        taskManager.createSubtask(subtask2);
        Subtask subtask3 = new Subtask("Выбор доп. опций", "Необходимо выбрать доп. опции", 3,
                Status.NEW);
        taskManager.createSubtask(subtask3);
        Subtask subtask4 = new Subtask("Выбор ремонта", "Необходимо выбрать подрядчиков",
                4, Status.NEW);
        taskManager.createSubtask(subtask4);

        //Проверка получения всех типов задач
        System.out.println("Проверка получения всех типов задач");
        System.out.println("Список всех задач:");
        System.out.println(taskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(taskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(taskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка получения задач по айди
        System.out.println("Проверка получения задачи по айди");
        System.out.println("Получение информации по задаче " + task2.getId() + ":");
        System.out.println(taskManager.getTask(task2.getId()));
        System.out.println("------------------------");
        System.out.println("Получение информации по эпику " + epic2.getId() + ":");
        System.out.println(taskManager.getEpic(epic2.getId()));
        System.out.println("------------------------");
        System.out.println("Получение информации по подзадаче " + subtask.getId() + ":");
        System.out.println(taskManager.getSubtask(subtask.getId()));
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка обновления данных в задаче
        System.out.println("Обновление задачи");
        task2 = new Task("Подарить цветы", "После обеда купить цветы любимой", Status.NEW, task2.getId());
        taskManager.updateTask(task2);
        System.out.println("Задача обновлена");
        System.out.println("Список задач с новыми обновлениями:");
        System.out.println(taskManager.printTasks());
        System.out.println("------------------------");

        //Проверка обновления данных в эпике
        System.out.println("Обновление эпика");
        epic1 = new Epic("Покупка машины", "План по покупке нового автомобиля", Status.NEW, 3);
        taskManager.updateEpic(epic1);
        System.out.println("Эпик обновлен");
        System.out.println("Список эпиков с новыми обновлениями:");
        System.out.println(taskManager.printEpics());
        System.out.println("------------------------");

        //Проверка обновления данных в подзадаче
        System.out.println("Обновление подзадачи");
        subtask1 = new Subtask("Выбор салона", "Выбрать салон, в котором буду покупать" +
                " машину и менеджера", 3, Status.NEW, subtask1.getId());
        taskManager.updateSubtask(subtask1);
        System.out.println("подзадача обновлена");
        System.out.println("Список подзадач с новыми обновлениями:");
        System.out.println(taskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка обновления статуса задачи
        System.out.println("Проверка обновления статуса задачи");
        task2 = new Task("Подарить цветы", "После обеда купить цветы любимой", Status.IN_PROGRESS, task2.getId());
        taskManager.updateTask(task2);
        System.out.println("Список всех задач:");
        System.out.println(taskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(taskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(taskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка обновления статуса подзадачи
        System.out.println("Проверка обновления статуса подзадачи");
        subtask1 = new Subtask("Выбор салона", "Выбрать салон, в котором буду покупать" +
                " машину и менеджера", 3, Status.IN_PROGRESS, subtask1.getId());
        subtask = new Subtask("Выбор марки автомобиля", "Необходимо выбрать марку автомобиля," +
                "который хочется приобрести", 3, Status.DONE, subtask.getId());
        subtask3 = new Subtask("Выбор доп. опций", "Необходимо выбрать доп. опции", 3,
                Status.DONE, subtask3.getId());
        taskManager.updateSubtask(subtask1);
        taskManager.updateSubtask(subtask);
        taskManager.updateSubtask(subtask3);
        System.out.println("Список всех задач:");
        System.out.println(taskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(taskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(taskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //проверка удаления задачи
        System.out.println("проверка удаления задачи");
        taskManager.deleteTask(1);
        System.out.println("Список всех задач:");
        System.out.println(taskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(taskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(taskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //проверка удаления подзадачи
        System.out.println("проверка удаления подзадачи");
        taskManager.deleteSubtask(6);
        System.out.println("Список всех задач:");
        System.out.println(taskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(taskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(taskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //проверка удаления эпика
        System.out.println("проверка удаления эпика'");
        System.out.println("Удалаяем эпик");
        taskManager.deleteEpic(3);
        System.out.println("Список всех задач:");
        System.out.println(taskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(taskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(taskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Получение подзадач определенного эпика
        taskManager.getSubtasksOfEpic(4);
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка удаления всех подзадач
        taskManager.deleteAllSubtasks();
        System.out.println("Список всех задач:");
        System.out.println(taskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(taskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(taskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

    }
}
