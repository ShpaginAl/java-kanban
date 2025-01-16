package task;

import taskManager.*;


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
                "где хочется проживать", 4, Status.NEW);
        inMemoryTaskManager.createSubtask(subtask2);
        Subtask subtask3 = new Subtask("Выбор доп. опций", "Необходимо выбрать доп. опции", 3,
                Status.NEW);
        inMemoryTaskManager.createSubtask(subtask3);
        Subtask subtask4 = new Subtask("Выбор ремонта", "Необходимо выбрать подрядчиков",
                4, Status.NEW);
        inMemoryTaskManager.createSubtask(subtask4);

        //Проверка получения всех типов задач
        System.out.println("Проверка получения всех типов задач");
        System.out.println("Список всех задач:");
        System.out.println(inMemoryTaskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(inMemoryTaskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(inMemoryTaskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка получения задач по айди
        System.out.println("Проверка получения задачи по айди");
        System.out.println("Получение информации по задаче " + task2.getId() + ":");
        System.out.println(inMemoryTaskManager.getTask(task2.getId()));
        System.out.println("------------------------");
        System.out.println("Получение истории просмотров");
        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println("------------------------");
        System.out.println("Получение информации по эпику " + epic2.getId() + ":");
        System.out.println(inMemoryTaskManager.getEpic(epic2.getId()));
        System.out.println("------------------------");
        System.out.println("Получение истории просмотров");
        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println("------------------------");
        System.out.println("Получение информации по подзадаче " + subtask.getId() + ":");
        System.out.println(inMemoryTaskManager.getSubtask(subtask.getId()));
        System.out.println("Получение истории просмотров");
        System.out.println(inMemoryTaskManager.getHistory());
        System.out.println("------------------------");
        System.out.println("------------------------");
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка обновления данных в задаче
        System.out.println("Обновление задачи");
        task2 = new Task("Подарить цветы", "После обеда купить цветы любимой", Status.NEW, task2.getId());
        inMemoryTaskManager.updateTask(task2);
        System.out.println("Задача обновлена");
        System.out.println("Список задач с новыми обновлениями:");
        System.out.println(inMemoryTaskManager.printTasks());
        System.out.println("------------------------");

        //Проверка обновления данных в эпике
        System.out.println("Обновление эпика");
        epic1 = new Epic("Покупка машины", "План по покупке нового автомобиля", Status.NEW, 3);
        inMemoryTaskManager.updateEpic(epic1);
        System.out.println("Эпик обновлен");
        System.out.println("Список эпиков с новыми обновлениями:");
        System.out.println(inMemoryTaskManager.printEpics());
        System.out.println("------------------------");

        //Проверка обновления данных в подзадаче
        System.out.println("Обновление подзадачи");
        subtask1 = new Subtask("Выбор салона", "Выбрать салон, в котором буду покупать" +
                " машину и менеджера", 3, Status.NEW, subtask1.getId());
        inMemoryTaskManager.updateSubtask(subtask1);
        System.out.println("подзадача обновлена");
        System.out.println("Список подзадач с новыми обновлениями:");
        System.out.println(inMemoryTaskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка обновления статуса задачи
        System.out.println("Проверка обновления статуса задачи");
        task2 = new Task("Подарить цветы", "После обеда купить цветы любимой", Status.IN_PROGRESS, task2.getId());
        inMemoryTaskManager.updateTask(task2);
        System.out.println("Список всех задач:");
        System.out.println(inMemoryTaskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(inMemoryTaskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(inMemoryTaskManager.printSubtasks());
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
        inMemoryTaskManager.updateSubtask(subtask1);
        inMemoryTaskManager.updateSubtask(subtask);
        inMemoryTaskManager.updateSubtask(subtask3);
        System.out.println("Список всех задач:");
        System.out.println(inMemoryTaskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(inMemoryTaskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(inMemoryTaskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //проверка удаления задачи
        System.out.println("проверка удаления задачи");
        inMemoryTaskManager.deleteTask(1);
        System.out.println("Список всех задач:");
        System.out.println(inMemoryTaskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(inMemoryTaskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(inMemoryTaskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //проверка удаления подзадачи
        System.out.println("проверка удаления подзадачи");
        inMemoryTaskManager.deleteSubtask(6);
        System.out.println("Список всех задач:");
        System.out.println(inMemoryTaskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(inMemoryTaskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(inMemoryTaskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //проверка удаления эпика
        System.out.println("проверка удаления эпика'");
        System.out.println("Удалаяем эпик");
        inMemoryTaskManager.deleteEpic(3);
        System.out.println("Список всех задач:");
        System.out.println(inMemoryTaskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(inMemoryTaskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(inMemoryTaskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Получение подзадач определенного эпика
        inMemoryTaskManager.getSubtasksOfEpic(4);
        System.out.println("------------------------");
        System.out.println("------------------------");

        //Проверка удаления всех подзадач
        inMemoryTaskManager.deleteAllSubtasks();
        System.out.println("Список всех задач:");
        System.out.println(inMemoryTaskManager.printTasks());
        System.out.println("------------------------");
        System.out.println("Список всех эпиков:");
        System.out.println(inMemoryTaskManager.printEpics());
        System.out.println("------------------------");
        System.out.println("Список всех подзадач:");
        System.out.println(inMemoryTaskManager.printSubtasks());
        System.out.println("------------------------");
        System.out.println("------------------------");

    }
}
