package TaskManager;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import Task.*;

public class TaskManager {
    public static HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
    public static HashMap<Integer, Epic> epics = new HashMap<Integer, Epic>();
    public static HashMap<Integer, Subtask> subtasks = new HashMap<Integer, Subtask>();
    static int id = 1;

    //создание задач
    public static void createTask(Task task) {
        task.setId(id);
        System.out.println("taskId: " + id);
        tasks.put(id, task);
        id += 1;
        System.out.println("Новая задача создана!");
    }

    public static void createEpic(Epic epic) {
        epic.setId(id);
        System.out.println("epicId: " + id);
        epics.put(id, epic);
        id += 1;
        System.out.println("Новый эпик создан!");
    }

    public static void createSubtask(Subtask subtask) {
        subtask.setId(id);
        if (epics.containsKey(subtask.getEpicId())) {
            subtasks.put(id, subtask);
            epics.get(subtask.getEpicId()).setSubtasks(subtask.getId());
            id += 1;
            System.out.println("Подзадача создана!");
        } else {
            System.out.println("Эпика, к которому вы хотите привязать подзадачу, нет. Привяжите подзадачу к другому" +
                    "эпику");
        }
    }

    //Получение всех задач
    public static void printTasks() {
        for (Task task : tasks.values()) {
            System.out.println(task);
        }
    }

    public static void printEpics() {
        for (Epic epic : epics.values()) {
            System.out.println(epic);
        }
    }

    public static void printSubtasks() {
        for (Subtask subtask : subtasks.values()) {
            System.out.println(subtask);
        }
    }

    //Удалить все задачи
    public static void deleteAllTasks() {
        tasks.clear();
    }

    //Удалить все эпики
    public static void deleteAllEpics() {
        epics.clear();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() != 0) {
                subtasks.remove(subtask.getId());
            }
        }
    }

    //Удалить все подзадачи
    public static void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
        }
    }

    //Удаление задач по айди
    public static void deleteTask(int id) {
        if (tasks.containsKey(id)) {
            tasks.remove(id);
            System.out.println("Задача удалена");
        } else {
            System.out.println("Задачи с таким id нет");
        }
    }

    //удаление эпика по айди
    public static void deleteEpic(int id) {
        if (epics.containsKey(id)) {
            epics.remove(id);
            System.out.println("Эпик удален");
            List<Integer> subtaskIds = new ArrayList<>();
            for (Subtask subtask : subtasks.values()) {
                if (subtask.getEpicId() == id) {
                    subtaskIds.add(subtask.getId());
                }
            }
            for (int index : subtaskIds) {
                subtasks.remove(index);
            }
        } else {
            System.out.println("Эпика с таким id нет");
        }
    }

    //удаление подзадачи по айди
    public static void deleteSubtask(int id) {
        if (subtasks.containsKey(id)) {
            subtasks.remove(id);
            System.out.println("Подзадача удалена");
            for (Epic epic : epics.values()) {
                for (int i = 0; i < epic.getSubtasks().size(); i++) {
                    if (epic.getSubtasks().get(i) == id) {
                        epic.getSubtasks().remove(i);
                    }
                }
            }
        } else {
            System.out.println("Подзадачи с таким id нет");
        }
    }

    // Получение задачи по айди
    public static void getTask(int id) {
        if (tasks.containsKey(id)) {
            System.out.println(tasks.get(id));
        } else {
            System.out.println("Такой задачи пока нет. Введите существующую задачу");
        }
    }

    // Получение эпика по айди
    public static void getEpic(int id) {
        if (epics.containsKey(id)) {
            System.out.println(epics.get(id));
        } else {
            System.out.println("Такого эпика пока нет. Введите существующий эпик");
        }
    }

    // Получение подзадачи по айди
    public static void getSubtask(int id) {
        if(subtasks.containsKey(id)) {
            System.out.println(subtasks.get(id));
        } else {
            System.out.println("Такого эпика пока нет. Введите существующий эпик");
        }
    }

    // Обновление задачи
    public static void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.get(task.getId()).setName(task.getName());
            tasks.get(task.getId()).setDescription(task.getDescription());
            tasks.get(task.getId()).setStatus(task.getStatus());
            System.out.println("Задача обновлена в списке");
        } else {
            System.out.println("Такой задачи нет. Попробуйте поменять другую задачу");
        }
    }

    // обновление эпика
    public static void updateEpic(Epic epic) {
        if (epics.containsKey(epic.getId())) {
            epics.get(epic.getId()).setName(epic.getName());
            epics.get(epic.getId()).setDescription(epic.getDescription());
        } else {
            System.out.println("Такого эпика нет. Попробуйте поменять другой эпик");
        }
    }

    // обновление подзадачи
    public static void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.get(subtask.getId()).setName(subtask.getName());
            subtasks.get(subtask.getId()).setDescription(subtask.getDescription());
            subtasks.get(subtask.getId()).setStatus(subtask.getStatus());
            updateStatusOfEpic(subtask);
        } else {
            System.out.println("Такой подзадачи нет. Попробуйте поменять на другую");
        }
    }

    // обновление статуса эпика
    public static void updateStatusOfEpic (Subtask subtask) {
        boolean isDone = true;
        boolean isNew = true;
        for (int subtaskId : epics.get(subtask.getEpicId()).getSubtasks()) {
            if (subtasks.get(subtaskId).getStatus() == Status.IN_PROGRESS) {
                isNew = false;
                isDone = false;
            }
            else if (subtasks.get(subtaskId).getStatus() == Status.NEW) {
                isDone = false;
            } else if (subtasks.get(subtaskId).getStatus() == Status.DONE) {
                isNew = false;
            }
        }
        if (!isDone && !isNew) epics.get(subtask.getEpicId()).setStatus(Status.IN_PROGRESS);
        else if (isDone) epics.get(subtask.getEpicId()).setStatus(Status.DONE);
        else if (isNew) epics.get(subtask.getEpicId()).setStatus(Status.NEW);

        // обновление статуса эпика (один из вариантов)
//        for (Subtask subtask1 : subtasks.values()) {
//            if (subtask1.getEpicId() == subtask.getEpicId()) {
//                if (subtask1.getStatus().equals(Status.NEW)) {
//                    statusNew += 1;
//                    if (statusNew == epics.get(subtask.getEpicId()).getSubtasks().size()) {
//                        epics.get(subtask.getEpicId()).setStatus(Status.NEW);
//                    } else {
//                        epics.get(subtask.getEpicId()).setStatus(Status.IN_PROGRESS);
//                    }
//                } else if (subtask1.getStatus().equals(Status.DONE)) {
//                    statusDone += 1;
//                    if (statusDone == epics.get(subtask.getEpicId()).getSubtasks().size()) {
//                        epics.get(subtask.getEpicId()).setStatus(Status.DONE);
//                    } else {
//                        epics.get(subtask.getEpicId()).setStatus(Status.IN_PROGRESS);
//                    }
//                } else {
//                    epics.get(subtask.getEpicId()).setStatus(Status.IN_PROGRESS);
//                    break;
//                }
//            }
//        }
    }

    //Получить подзадачи определенного эпика
    public static void getSubtasksOfEpic(int epicId) {
        if (epics.containsKey(epicId)) {
            ArrayList<Integer> subtasksInEpic = epics.get(epicId).getSubtasks();
            if (!subtasksInEpic.isEmpty()) {
                for (int subtask : subtasksInEpic) {
                    System.out.println(subtasks.get(subtask));
                }
            } else {
                System.out.println("Пока нет подзадач у этого эпика");
            }
        } else {
            System.out.println("Такого эпика нет. Выберите другой эпик");
        }
    }
}