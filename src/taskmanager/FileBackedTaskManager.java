package taskmanager;

import task.Epic;
import task.Subtask;
import task.Task;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileBackedTaskManager extends InMemoryTaskManager {
    private final File file;


    public FileBackedTaskManager(File file) {
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        final FileBackedTaskManager taskManagerFromFile = new FileBackedTaskManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String currentLine = br.readLine();
            String nextLine = br.readLine();
            while (nextLine != null) {
                Task task = CSVTaskFormat.taskFromString(currentLine);
                switch (task.getType()) {
                    case TASK:
                        if (task.getStartTime() == null) {
                            taskManagerFromFile.tasks.put(task.getId(), task);
                            taskManagerFromFile.id++;
                        } else {
                            if (!taskManagerFromFile.isIntersectionBetweenTasks(task)) {
                                taskManagerFromFile.tasks.put(task.getId(), task);
                                taskManagerFromFile.prioritizedTask.add(task);
                                taskManagerFromFile.id++;
                            }
                        }
                        break;
                    case EPIC:
                        if (task.getStartTime() == null) {
                            taskManagerFromFile.epics.put(task.getId(), (Epic) task);
                            taskManagerFromFile.id++;
                        } else {
                            if (!taskManagerFromFile.isIntersectionBetweenTasks(task)) {
                                taskManagerFromFile.epics.put(task.getId(), (Epic) task);
                                taskManagerFromFile.prioritizedTask.add(task);
                                taskManagerFromFile.id++;
                            }
                        }
                        break;
                    case SUBTASK:
                        if (task.getStartTime() == null) {
                            taskManagerFromFile.subtasks.put(task.getId(), (Subtask) task);
                            taskManagerFromFile.id++;
                        } else {
                            if (!taskManagerFromFile.isIntersectionBetweenTasks(task)) {
                                taskManagerFromFile.subtasks.put(task.getId(), (Subtask) task);
                                taskManagerFromFile.prioritizedTask.add(task);
                                taskManagerFromFile.id++;
                            }
                        }
                        break;
                }
                currentLine = nextLine;
                nextLine = br.readLine();
            }
            if (!currentLine.equals("No value")) {
                List<Integer> listOfHistory = CSVTaskFormat.historyFromString(currentLine);
                for (int taskInHistory : listOfHistory) {
                    if (taskManagerFromFile.tasks.containsKey(taskInHistory)) {
                        Task task = taskManagerFromFile.tasks.get(taskInHistory);
                        taskManagerFromFile.historyManager.addValueInHistory(task);
                    } else if (taskManagerFromFile.epics.containsKey(taskInHistory)) {
                        Epic epic = taskManagerFromFile.epics.get(taskInHistory);
                        taskManagerFromFile.historyManager.addValueInHistory(epic);
                    } else if (taskManagerFromFile.subtasks.containsKey(taskInHistory)) {
                        Subtask subtask = taskManagerFromFile.subtasks.get(taskInHistory);
                        taskManagerFromFile.historyManager.addValueInHistory(subtask);
                    }
                }
            }
        } catch (IOException exp) {
            throw new ManagerSaveException("Ошибка считывания файла");
        }
        return taskManagerFromFile;
    }

    @Override
    public int createTask(Task task) {
        super.createTask(task);
        save();
        return task.getId();
    }

    @Override
    public int createEpic(Epic epic) {
        super.createEpic(epic);
        save();
        return epic.getId();
    }

    @Override
    public int createSubtask(Subtask subtask) {
        super.createSubtask(subtask);
        save();
        return subtask.getId();
    }

    @Override
    public void deleteAllTasks() {
        super.deleteAllTasks();
        save();
    }

    @Override
    public void deleteAllEpics() {
        super.deleteAllEpics();
        save();
    }

    @Override
    public void deleteAllSubtasks() {
        super.deleteAllSubtasks();
        save();
    }

    @Override
    public void deleteTask(int id) {
        super.deleteTask(id);
        save();
    }

    @Override
    public void deleteEpic(int id) {
        super.deleteEpic(id);
        save();
    }

    @Override
    public void deleteSubtask(int id) {
        super.deleteSubtask(id);
        save();
    }

    @Override
    public Task getTask(int id) {
        if (tasks.containsKey(id)) {
            historyManager.addValueInHistory(tasks.get(id));
            save();
            return tasks.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Epic getEpic(int id) {
        if (epics.containsKey(id)) {
            historyManager.addValueInHistory(epics.get(id));
            save();
            return epics.get(id);
        } else {
            return null;
        }
    }

    @Override
    public Subtask getSubtask(int id) {
        if (subtasks.containsKey(id)) {
            historyManager.addValueInHistory(subtasks.get(id));
            save();
            return subtasks.get(id);
        } else {
            return null;
        }
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    public int createTaskFromFile(FileBackedTaskManager fileBackedTaskManager, Task task) {
        if (task.getStartTime() == null) {
            tasks.put(task.getId(), task);
            fileBackedTaskManager.id++;
            return id;
        } else {
            if (!isIntersectionBetweenTasks(task)) {
                tasks.put(task.getId(), task);
                prioritizedTask.add(task);
                fileBackedTaskManager.id++;
                return id;
            } else {
                return 0;
            }
        }
    }


    private void save() {
        Path pathOfFile = file.toPath();
        if (!Files.exists(pathOfFile)) {
            try {
                System.out.println(pathOfFile);
                Files.createFile(pathOfFile);
            } catch (IOException exc) {
                throw new ManagerSaveException("Не удалось создать файл");
            }
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("id,type,name,status,description,epic,startTime,duration\n");
            for (Task task : tasks.values()) {
                writer.write(CSVTaskFormat.toString(task) + "\n");
            }
            for (Epic epic : epics.values()) {
                writer.write(CSVTaskFormat.toString(epic) + "\n");
            }
            for (Subtask subtask : subtasks.values()) {
                writer.write(CSVTaskFormat.toString(subtask) + "\n");
            }
            if (historyManager.getHistory() != null) {
                writer.write(CSVTaskFormat.toString(historyManager));
            } else {
                writer.write("No value");
            }
        } catch (IOException exc) {
            throw new ManagerSaveException("Ошибка сохранения в файл");
        }
    }
}
