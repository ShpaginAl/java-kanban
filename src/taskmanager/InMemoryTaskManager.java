package taskmanager;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import task.*;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
    protected final HashMap<Integer, Epic> epics = new HashMap<Integer, Epic>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<Integer, Subtask>();
    protected int id = 0;
    protected final HistoryManager historyManager = Managers.getDefaultHistory();

    //создание задач
    @Override
    public void createTask(Task task) {
        task.setId(counterOfId());
        tasks.put(id, task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId(counterOfId());
        epics.put(id, epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        subtask.setId(counterOfId());
        if (epics.containsKey(subtask.getEpicId())) {
            subtasks.put(id, subtask);
            epics.get(subtask.getEpicId()).setSubtasks(subtask.getId());
        }
    }

    //Получение всех задач
    @Override
    public HashMap<Integer, Task> printTasks() {
        return tasks;
    }

    @Override
    public HashMap<Integer, Epic> printEpics() {
        return epics;
    }

    @Override
    public HashMap<Integer, Subtask> printSubtasks() {
        return subtasks;
    }

    //Удалить все задачи
    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    //Удалить все эпики
    @Override
    public void deleteAllEpics() {
        epics.clear();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() != 0) {
                subtasks.remove(subtask.getId());
            }
        }
    }

    //Удалить все подзадачи
    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubtasks().clear();
            epic.setStatus(Status.NEW);
        }
    }

    //Удаление задач по айди
    @Override
    public void deleteTask(int id) {
        tasks.remove(id);
        historyManager.remove(id);
    }

    //удаление эпика по айди
    @Override
    public void deleteEpic(int id) {
        epics.remove(id);
        historyManager.remove(id);
        List<Integer> subtaskIds = new ArrayList<>();
        for (Subtask subtask : subtasks.values()) {
            if (subtask.getEpicId() == id) {
                subtaskIds.add(subtask.getId());
            }
        }
        for (int index : subtaskIds) {
            subtasks.remove(index);
            historyManager.remove(index);
        }
    }

    //удаление подзадачи по айди
    @Override
    public void deleteSubtask(int id) {
        Subtask deletedSubtask = subtasks.get(id);
        subtasks.remove(id);
        historyManager.remove(id);
        for (Epic epic : epics.values()) {
            for (int i = 0; i < epic.getSubtasks().size(); i++) {
                if (epic.getSubtasks().get(i) == id) {
                    epic.getSubtasks().remove(i);
                }
            }
        }
        updateStatusOfEpic(deletedSubtask);
    }

    // Получение задачи по айди
    @Override
    public Task getTask(int id) {
        if (tasks.containsKey(id)) {
            historyManager.addValueInHistory(tasks.get(id));
            return tasks.get(id);
        } else {
            return null;
        }
    }

    // Получение эпика по айди
    @Override
    public Epic getEpic(int id) {
        if (epics.containsKey(id)) {
            historyManager.addValueInHistory(epics.get(id));
            return epics.get(id);
        } else {
            return null;
        }
    }

    // Получение подзадачи по айди
    @Override
    public Subtask getSubtask(int id) {
        if (subtasks.containsKey(id)) {
            historyManager.addValueInHistory(subtasks.get(id));
            return subtasks.get(id);
        } else {
            return null;
        }
    }

    // Обновление задачи
    @Override
    public void updateTask(Task task) {
        tasks.get(task.getId()).setName(task.getName());
        tasks.get(task.getId()).setDescription(task.getDescription());
        tasks.get(task.getId()).setStatus(task.getStatus());
    }

    // обновление эпика
    @Override
    public void updateEpic(Epic epic) {
        epics.get(epic.getId()).setName(epic.getName());
        epics.get(epic.getId()).setDescription(epic.getDescription());
    }

    // обновление подзадачи
    @Override
    public void updateSubtask(Subtask subtask) {
        subtasks.get(subtask.getId()).setName(subtask.getName());
        subtasks.get(subtask.getId()).setDescription(subtask.getDescription());
        subtasks.get(subtask.getId()).setStatus(subtask.getStatus());
        updateStatusOfEpic(subtask);
    }

    //Получить подзадачи определенного эпика
    @Override
    public void getSubtasksOfEpic(int epicId) {
        ArrayList<Integer> subtasksInEpic = epics.get(epicId).getSubtasks();
        if (!subtasksInEpic.isEmpty()) {
            for (int subtask : subtasksInEpic) {
                System.out.println(subtasks.get(subtask));
            }
        }
    }

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }

    // обновление статуса эпика
    private void updateStatusOfEpic(Subtask subtask) {
        boolean isDone = true;
        boolean isNew = true;
        for (int subtaskId : epics.get(subtask.getEpicId()).getSubtasks()) {
            if (subtasks.get(subtaskId).getStatus() == Status.IN_PROGRESS) {
                isNew = false;
                isDone = false;
            } else if (subtasks.get(subtaskId).getStatus() == Status.NEW) {
                isDone = false;
            } else if (subtasks.get(subtaskId).getStatus() == Status.DONE) {
                isNew = false;
            }
        }
        if (!isDone && !isNew) {
            epics.get(subtask.getEpicId()).setStatus(Status.IN_PROGRESS);
        } else if (isDone) {
            epics.get(subtask.getEpicId()).setStatus(Status.DONE);
        } else if (isNew) {
            epics.get(subtask.getEpicId()).setStatus(Status.NEW);
        }
    }

    //Счетчик id
    private int counterOfId() {
        return id += 1;
    }
}
