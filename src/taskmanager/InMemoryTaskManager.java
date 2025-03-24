package taskmanager;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.*;

import task.*;

public class InMemoryTaskManager implements TaskManager {
    protected final HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
    protected final HashMap<Integer, Epic> epics = new HashMap<Integer, Epic>();
    protected final HashMap<Integer, Subtask> subtasks = new HashMap<Integer, Subtask>();
    protected int id = 0;
    protected final HistoryManager historyManager = Managers.getDefaultHistory();
    protected TreeSet<Task> prioritizedTask = new TreeSet<>(Comparator.comparing(Task::getStartTime).thenComparing(Task::getName));

    //создание задач
    @Override
    public int createTask(Task task) {
        if (task.getStartTime() == null) {
            task.setId(counterOfId());
            tasks.put(id, task);
            return id;
        } else {
            if (!isIntersectionBetweenTasks(task)) {
                task.setId(counterOfId());
                tasks.put(id, task);
                prioritizedTask.add(task);
            }
        }
        return id;
    }

    @Override
    public int createEpic(Epic epic) {
        epic.setId(counterOfId());
        epics.put(id, epic);
        setTimeForEpic(epic);
        return id;
    }

    @Override
    public int createSubtask(Subtask subtask) {
        if (subtask.getStartTime() == null) {
            if (epics.containsKey(subtask.getEpicId())) {
                subtask.setId(counterOfId());
                subtasks.put(id, subtask);
                setTimeForEpic(epics.get(subtask.getEpicId()));
                epics.get(subtask.getEpicId()).setSubtasksOfEpic(subtask.getId());
                return id;
            }
            return 0;
        } else {
            if (!isIntersectionBetweenTasks(subtask)) {
                if (epics.containsKey(subtask.getEpicId())) {
                    subtask.setId(counterOfId());
                    subtasks.put(id, subtask);
                    epics.get(subtask.getEpicId()).setSubtasksOfEpic(subtask.getId());
                    prioritizedTask.add(subtask);
                    setTimeForEpic(epics.get(subtask.getEpicId()));
                }
            }
            return id;
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
        prioritizedTask.removeIf(task -> task.getType().equals(TypeTask.TASK));
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
        prioritizedTask.removeIf(task -> task.getType().equals(TypeTask.EPIC));
    }

    //Удалить все подзадачи
    @Override
    public void deleteAllSubtasks() {
        subtasks.clear();
        prioritizedTask.removeIf(task -> task.getType().equals(TypeTask.SUBTASK));
        for (Epic epic : epics.values()) {
            epic.getSubtasksOfEpic().clear();
            epic.setStatus(Status.NEW);
            setTimeForEpic(epic);
        }
    }

    //Удаление задач по айди
    @Override
    public void deleteTask(int id) {
        if (prioritizedTask.contains(tasks.get(id))) {
            prioritizedTask.remove(tasks.get(id));
        }
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
        setTimeForEpic(epics.get(subtasks.get(id).getEpicId()));
        if (prioritizedTask.contains(subtasks.get(id))) {
            prioritizedTask.remove(subtasks.get(id));
        }
        subtasks.remove(id);
        historyManager.remove(id);
        for (Epic epic : epics.values()) {
            for (int i = 0; i < epic.getSubtasksOfEpic().size(); i++) {
                if (epic.getSubtasksOfEpic().get(i) == id) {
                    epic.getSubtasksOfEpic().remove(i);
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
            setTimeForEpic(epics.get(id));
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
        if (!isIntersectionBetweenTasks(task)) {
            tasks.get(task.getId()).setName(task.getName());
            tasks.get(task.getId()).setDescription(task.getDescription());
            tasks.get(task.getId()).setStatus(task.getStatus());
            tasks.get(task.getId()).setStartTime(task.getStartTime());
            tasks.get(task.getId()).setDuration(task.getDuration());
            if (task.getStartTime() != null) {
                if (prioritizedTask.contains(task)) {
                    prioritizedTask.remove(task);
                    prioritizedTask.add(task);
                }
            }
        }
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
        if (!isIntersectionBetweenTasks(subtask)) {
            subtasks.get(subtask.getId()).setName(subtask.getName());
            subtasks.get(subtask.getId()).setDescription(subtask.getDescription());
            subtasks.get(subtask.getId()).setStatus(subtask.getStatus());
            subtasks.get(subtask.getId()).setDuration(subtask.getDuration());
            subtasks.get(subtask.getId()).setStartTime(subtask.getStartTime());
            updateStatusOfEpic(subtask);
            setTimeForEpic(epics.get(subtask.getEpicId()));
            if (subtask.getStartTime() != null) {
                if (prioritizedTask.contains(subtask)) {
                    prioritizedTask.remove(subtask);
                    prioritizedTask.add(subtask);
                }
            }
        }
    }

    //Получить подзадачи определенного эпика
    @Override
    public void getSubtasksOfEpic(int epicId) {
        ArrayList<Integer> subtasksInEpic = epics.get(epicId).getSubtasksOfEpic();
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
        for (int subtaskId : epics.get(subtask.getEpicId()).getSubtasksOfEpic()) {
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

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTask;
    }

    //Счетчик id
    private int counterOfId() {
        return id += 1;
    }

    private boolean isIntersectionBetweenTasks(Task task) {
        if (!prioritizedTask.isEmpty()) {
            for (Task taskInList : getPrioritizedTasks()) {
                if (!task.getName().equals(taskInList.getName()) && task.getStartTime().isBefore(taskInList.getEndTime()) && task.getEndTime().isAfter(taskInList.getStartTime())) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setTimeForEpic(Epic epic) {
        LocalDateTime startTime = null;
        LocalDateTime endTime = null;
        Duration duration = null;
        for (int i = 0; i < epic.getSubtasksOfEpic().size(); i++) {
            if (startTime == null) {
                startTime = subtasks.get(epic.getSubtasksOfEpic().get(i)).getStartTime();
            } else {
                if (subtasks.get(epic.getSubtasksOfEpic().get(i)).getStartTime().isBefore(startTime)) {
                    startTime = subtasks.get(epic.getSubtasksOfEpic().get(i)).getStartTime();
                }
            }
            if (endTime == null) {
                endTime = subtasks.get(epic.getSubtasksOfEpic().get(i)).getEndTime();
            } else {
                if (subtasks.get(epic.getSubtasksOfEpic().get(i)).getEndTime().isAfter(endTime)) ;
                endTime = subtasks.get(epic.getSubtasksOfEpic().get(i)).getEndTime();
            }
            duration = Duration.between(startTime, endTime);
        }
        epic.setStartTime(startTime);
        epic.setDuration(duration);
        epic.setEndTime(endTime);
    }
}
